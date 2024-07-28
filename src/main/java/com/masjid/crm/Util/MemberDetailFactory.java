package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.dto.response.MemberDetailListResponse;
import com.masjid.crm.dto.response.MemberDetailResponse;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDetailFactory {

    public static MemberDetail buildMemberDetail(MemberDetailRequest request) {
        return MemberDetail.builder()
                .name(request.getName())
                .martialStatus(request.getMartialStatus())
                .gender(request.getGender())
                .age(request.getAge())
                .educationQualification(request.getEducationQualification())
                .occupation(request.getOccupation())
                .phoneNumber(request.getPhoneNumber())
                .alternativeNumber(request.getAlternativeNumber())
                .bloodGroup(request.getBloodGroup())
                .build();
    }

    public static MemberDetailResponse buildMemberDetailResponse(MemberDetail memberDetail) {
        MemberDetailResponse response = new MemberDetailResponse();
        response.setId(memberDetail.getId());
        response.setName(memberDetail.getName());
        response.setMartialStatus(memberDetail.getMartialStatus());
        response.setGender(memberDetail.getGender());
        response.setAge(memberDetail.getAge());
        response.setEducationQualification(memberDetail.getEducationQualification());
        response.setOccupation(memberDetail.getOccupation());
        response.setPhoneNumber(memberDetail.getPhoneNumber());
        response.setAlternativeNumber(memberDetail.getAlternativeNumber());
        response.setBloodGroup(memberDetail.getBloodGroup());
        return response;
    }

    public static MemberDetailListResponse buildMemberDetailListResponse(Page<MemberDetail> memberDetails, Long count) {
        List<MemberDetailResponse> responses = memberDetails.getContent().stream()
                .map(MemberDetailFactory::buildMemberDetailResponse)
                .collect(Collectors.toList());
        return MemberDetailListResponse.builder().memberDetails(responses).count(count)
                .build();
    }

}
