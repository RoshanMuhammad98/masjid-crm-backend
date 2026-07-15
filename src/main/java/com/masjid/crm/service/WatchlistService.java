package com.masjid.crm.service;

import com.masjid.crm.dto.response.WatchlistResponse;
import com.masjid.crm.dto.response.WatchlistResponse.IncompleteRegistrationItem;
import com.masjid.crm.dto.response.WatchlistResponse.IncompleteRegistrationSection;
import com.masjid.crm.dto.response.WatchlistResponse.OverdueMembershipItem;
import com.masjid.crm.dto.response.WatchlistResponse.OverduePendingMembershipsSection;
import com.masjid.crm.dto.response.WatchlistResponse.SimpleMemberSection;
import com.masjid.crm.dto.response.WatchlistResponse.WatchlistMemberItem;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.MartialStatus;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.repository.DeathDetailRepository;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.MarriageRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
import com.masjid.crm.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    private static final int DEFAULT_OVERDUE_DAYS = 30;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    @Autowired
    private DeathDetailRepository deathDetailRepository;

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    public WatchlistResponse getWatchlist(Integer overdueDays) {
        int threshold = overdueDays == null || overdueDays <= 0 ? DEFAULT_OVERDUE_DAYS : overdueDays;
        return WatchlistResponse.builder()
                .overduePendingMemberships(buildOverdueMemberships(threshold))
                .disabilityMembers(buildDisabilitySection())
                .medicalIssueMembers(buildMedicalIssueSection())
                .widows(buildWidowsSection())
                .incompleteRegistrations(buildIncompleteRegistrations())
                .build();
    }

    private IncompleteRegistrationSection buildIncompleteRegistrations() {
        List<FamilyDetail> families = familyDetailRepository.findAll();
        List<MemberDetail> allMembers = memberDetailsRepository.findAll();

        java.util.Map<Long, Long> registeredByFamily = new java.util.HashMap<>();
        for (MemberDetail m : allMembers) {
            if (m.getFamilyDetail() != null) {
                registeredByFamily.merge(m.getFamilyDetail().getId(), 1L, Long::sum);
            }
        }

        List<IncompleteRegistrationItem> items = new ArrayList<>();
        for (FamilyDetail f : families) {
            if (f.getTotalMembersCount() == null) continue;
            long registered = registeredByFamily.getOrDefault(f.getId(), 0L);
            long declared = f.getTotalMembersCount();
            if (declared > registered) {
                items.add(IncompleteRegistrationItem.builder()
                        .familyDetailId(f.getId())
                        .householdName(f.getHouseholdName())
                        .phoneNumber(f.getPhoneNumber())
                        .declaredMembersCount(declared)
                        .registeredMembersCount(registered)
                        .pendingCount(declared - registered)
                        .build());
            }
        }
        items.sort((a, b) -> Long.compare(b.getPendingCount(), a.getPendingCount()));

        return IncompleteRegistrationSection.builder()
                .count(items.size())
                .items(items)
                .build();
    }

    private OverduePendingMembershipsSection buildOverdueMemberships(int thresholdDays) {
        Date cutoff = Date.from(Instant.now().minus(thresholdDays, ChronoUnit.DAYS));
        List<MembershipDetail> overdue = membershipRepository
                .findByPaymentStatusAndCreatedBefore(PaymentStatus.PENDING, cutoff);

        List<OverdueMembershipItem> items = overdue.stream()
                .map(m -> toOverdueItem(m, thresholdDays))
                .collect(Collectors.toList());

        return OverduePendingMembershipsSection.builder()
                .count(items.size())
                .thresholdDays(thresholdDays)
                .items(items)
                .build();
    }

    private OverdueMembershipItem toOverdueItem(MembershipDetail m, int thresholdDays) {
        FamilyDetail family = m.getFamilyDetail();
        long daysOverdue = m.getCreatedDate() == null
                ? thresholdDays
                : ChronoUnit.DAYS.between(m.getCreatedDate().toInstant(), Instant.now());
        return OverdueMembershipItem.builder()
                .membershipId(m.getId())
                .familyDetailId(family != null ? family.getId() : null)
                .householdName(family != null ? family.getHouseholdName() : null)
                .memberShipType(m.getMemberShipType())
                .amount(m.getAmount())
                .paymentStatus(m.getPaymentStatus())
                .createdDate(m.getCreatedDate())
                .daysOverdue(daysOverdue)
                .notes(m.getNotes())
                .build();
    }

    private SimpleMemberSection buildDisabilitySection() {
        List<MemberDetail> members = memberDetailsRepository.findByHasDisabilityTrue();
        Set<Long> deceasedIds = getDeceasedMemberIds();
        List<WatchlistMemberItem> items = members.stream()
                .filter(m -> !deceasedIds.contains(m.getId()))
                .map(m -> toItem(m, m.getDisabilityNotes()))
                .collect(Collectors.toList());
        return SimpleMemberSection.builder().count(items.size()).items(items).build();
    }

    private SimpleMemberSection buildMedicalIssueSection() {
        List<MemberDetail> members = memberDetailsRepository.findByHasMedicalIssueTrue();
        Set<Long> deceasedIds = getDeceasedMemberIds();
        List<WatchlistMemberItem> items = members.stream()
                .filter(m -> !deceasedIds.contains(m.getId()))
                .map(m -> toItem(m, m.getMedicalCondition()))
                .collect(Collectors.toList());
        return SimpleMemberSection.builder().count(items.size()).items(items).build();
    }

    private SimpleMemberSection buildWidowsSection() {
        List<DeathDetail> deaths = deathDetailRepository.findAll();
        if (deaths.isEmpty()) {
            return SimpleMemberSection.builder().count(0).items(Collections.emptyList()).build();
        }

        Set<Long> deceasedMemberIds = deaths.stream()
                .map(DeathDetail::getMemberDetail)
                .filter(m -> m != null)
                .map(MemberDetail::getId)
                .collect(Collectors.toSet());

        Set<Long> candidateFamilyIds = deaths.stream()
                .map(DeathDetail::getMemberDetail)
                .filter(m -> m != null && m.getFamilyDetail() != null)
                .map(m -> m.getFamilyDetail().getId())
                .collect(Collectors.toSet());

        if (candidateFamilyIds.isEmpty()) {
            return SimpleMemberSection.builder().count(0).items(Collections.emptyList()).build();
        }

        List<WatchlistMemberItem> items = new ArrayList<>();
        Set<Long> alreadyAdded = new HashSet<>();

        for (Long familyId : candidateFamilyIds) {
            List<MemberDetail> familyMembers = memberDetailsRepository.findByFamilyDetailId(familyId);
            for (MemberDetail member : familyMembers) {
                if (member.getMartialStatus() == MartialStatus.MARRIED
                        && !deceasedMemberIds.contains(member.getId())
                        && alreadyAdded.add(member.getId())) {
                    String note = resolveSpouseName(member);
                    items.add(toItem(member, note));
                }
            }
        }

        return SimpleMemberSection.builder().count(items.size()).items(items).build();
    }

    private String resolveSpouseName(MemberDetail member) {
        List<MarriageDetail> marriages = marriageRepository.findByMemberDetail_FamilyDetail_Id(
                member.getFamilyDetail() != null ? member.getFamilyDetail().getId() : null);
        return marriages.stream()
                .filter(md -> md.getMemberDetail() != null
                        && member.getId().equals(md.getMemberDetail().getId()))
                .map(MarriageDetail::getMarriageMemberName)
                .filter(n -> n != null && !n.isEmpty())
                .findFirst()
                .map(s -> "Possible widow/widower of " + s)
                .orElse("Possible widow/widower — verify with family");
    }

    private Set<Long> getDeceasedMemberIds() {
        return deathDetailRepository.findAll().stream()
                .map(DeathDetail::getMemberDetail)
                .filter(m -> m != null)
                .map(MemberDetail::getId)
                .collect(Collectors.toSet());
    }

    private WatchlistMemberItem toItem(MemberDetail m, String note) {
        FamilyDetail family = m.getFamilyDetail();
        return WatchlistMemberItem.builder()
                .memberId(m.getId())
                .name(m.getName())
                .gender(m.getGender())
                .age(m.getAge())
                .familyDetailId(family != null ? family.getId() : null)
                .householdName(family != null ? family.getHouseholdName() : null)
                .phoneNumber(m.getPhoneNumber())
                .note(note)
                .build();
    }
}
