package com.masjid.crm.service;

import com.masjid.crm.Util.MembershipDetailFactory;
import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.dto.response.MembershipDetailListResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.FinancialAccount;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.FinancialSource;
import com.masjid.crm.model.MembershipMemberType;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.model.TransactionType;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.FinancialAccountRepository;
import com.masjid.crm.repository.MembershipRepository;
import com.masjid.crm.specification.MembershipDetailSpecification;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    @Autowired
    private FinancialAccountRepository financialAccountRepository;

    public MembershipDetail saveMembershipDetails(MembershipDetailRequest request) {
        MembershipDetail saveMembershipDetail = saveMembershipDetail(request);
        recordMembershipIncome(saveMembershipDetail);
        return saveMembershipDetail;
    }

    public MembershipDetail updatePaymentStatus(Long id, PaymentStatus newStatus) {
        if (newStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment status is required");
        }

        MembershipDetail membershipDetail = membershipRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Membership not found: " + id));

        PaymentStatus current = membershipDetail.getPaymentStatus();

        if (current == PaymentStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cancelled memberships cannot be modified");
        }
        if (current == PaymentStatus.RECEIVED && newStatus == PaymentStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A received payment cannot be moved back to pending");
        }
        if (current == newStatus) {
            return membershipDetail;
        }

        membershipDetail.setPaymentStatus(newStatus);
        MembershipDetail saved = membershipRepository.save(membershipDetail);
        recordMembershipIncome(saved);
        return saved;
    }

    private void recordMembershipIncome(MembershipDetail membershipDetail) {
        Long membershipId = membershipDetail.getId();
        if (membershipId == null) {
            return;
        }

        Optional<FinancialAccount> existing = financialAccountRepository
                .findBySourceTypeAndSourceId(FinancialSource.MEMBERSHIP, membershipId);

        FamilyDetail family = membershipDetail.getFamilyDetail();
        String payerName = null;
        if (membershipDetail.getMembershipMemberType() == MembershipMemberType.OTHER) {
            payerName = membershipDetail.getOtherPersonName();
        } else if (family != null) {
            payerName = family.getHouseholdName();
        }

        FinancialAccount account = existing.orElseGet(FinancialAccount::new);
        account.setTransactionType(TransactionType.INCOME);
        account.setCategory(FinancialCategory.MEMBERSHIP_FEE);
        account.setAmount(membershipDetail.getAmount());
        if (account.getTransactionDate() == null) {
            account.setTransactionDate(Date.valueOf(LocalDate.now()));
        }
        account.setReferenceNumber("MEMBERSHIP-" + membershipId);
        account.setPayerOrPayeeName(payerName);
        account.setDescription(buildDescription(membershipDetail));
        account.setSourceType(FinancialSource.MEMBERSHIP);
        account.setSourceId(membershipId);
        account.setPaymentStatus(membershipDetail.getPaymentStatus());
        account.setPaymentMethod(membershipDetail.getPaymentMethod());

        financialAccountRepository.save(account);
    }

    private String buildDescription(MembershipDetail membershipDetail) {
        StringBuilder sb = new StringBuilder("Membership fee");
        if (membershipDetail.getMemberShipType() != null) {
            sb.append(" - ").append(membershipDetail.getMemberShipType());
        }
        if (membershipDetail.getNotes() != null && !membershipDetail.getNotes().isEmpty()) {
            sb.append(" (").append(membershipDetail.getNotes()).append(")");
        }
        return sb.toString();
    }

    private MembershipDetail saveMembershipDetail(MembershipDetailRequest request) {

        if (request.getFamilyDetailId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "familyDetailId is required");
        }

        Optional<FamilyDetail> familyDetailOpt = familyDetailRepository.findById(request.getFamilyDetailId());

        if (!familyDetailOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Family not found: " + request.getFamilyDetailId());
        }

        MembershipDetail membershipDetail;

        if (request.getId() != null) {
            membershipDetail = membershipRepository.findById(request.getId())
                    .orElse(new MembershipDetail());
        } else {
            membershipDetail = new MembershipDetail();
        }

        // ✅ set values instead of builder
        membershipDetail.setMemberShipType(request.getMemberShipType());
        membershipDetail.setAmount(request.getAmount());
        membershipDetail.setPaymentStatus(request.getPaymentStatus());
        membershipDetail.setNotes(request.getNotes());
        membershipDetail.setFamilyDetail(familyDetailOpt.get());
        membershipDetail.setMembershipMemberType(request.getMembershipMemberType());
        membershipDetail.setPaymentMethod(request.getPaymentMethod());

        if (request.getMembershipMemberType() == MembershipMemberType.OTHER) {
            membershipDetail.setOtherPersonName(request.getOtherPersonName());
            membershipDetail.setOtherPersonPhoneNumber(request.getOtherPersonPhoneNumber());
        } else {
            membershipDetail.setOtherPersonName(null);
            membershipDetail.setOtherPersonPhoneNumber(null);
        }

        return membershipRepository.save(membershipDetail);
    }

    public MembershipDetailListResponse filteredMembershipDetails(MembershipDetailRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        int pageSize = request.getPageSize() == null || request.getPageSize() <= 0 ? 20 : request.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Specification<MembershipDetail> spec = MembershipDetailSpecification.filterMemberships(request);
        Page<MembershipDetail> membershipDetails = membershipRepository.findAll(spec, pageable);
        Long count = membershipDetails.getTotalElements();
        return MembershipDetailFactory.buildMembershipDetailsListResponse(membershipDetails, count);
    }

}
