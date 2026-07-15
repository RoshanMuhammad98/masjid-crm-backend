package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FamilyOverviewResponse {

    private FamilyDetailResponse family;

    private List<MemberDetailResponse> members;

    private List<MarriageDetailResponse> marriages;

    private List<DeathDetailResponse> deaths;

    private FamilyStats stats;

    @Data
    @Builder
    public static class FamilyStats {
        private long totalMembers;
        private long marriedCount;
        private long unmarriedCount;
        private long divorcedCount;
        private long deathCount;
        private long studentsCount;
        private long disabilityCount;
        private long disabilityMaleCount;
        private long disabilityFemaleCount;
        private long medicalIssueCount;
        private long maleCount;
        private long femaleCount;
        private Long declaredMembersCount;
        private long registeredMembersCount;
        private long pendingMemberRegistrations;
        private boolean registrationComplete;
    }
}
