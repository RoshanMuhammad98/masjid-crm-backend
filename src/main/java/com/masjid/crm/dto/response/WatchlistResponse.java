package com.masjid.crm.dto.response;

import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class WatchlistResponse {

    private OverduePendingMembershipsSection overduePendingMemberships;
    private SimpleMemberSection disabilityMembers;
    private SimpleMemberSection medicalIssueMembers;
    private SimpleMemberSection widows;
    private IncompleteRegistrationSection incompleteRegistrations;

    @Data
    @Builder
    public static class IncompleteRegistrationSection {
        private long count;
        private List<IncompleteRegistrationItem> items;
    }

    @Data
    @Builder
    public static class IncompleteRegistrationItem {
        private Long familyDetailId;
        private String householdName;
        private String phoneNumber;
        private Long declaredMembersCount;
        private long registeredMembersCount;
        private long pendingCount;
    }

    @Data
    @Builder
    public static class OverduePendingMembershipsSection {
        private long count;
        private Integer thresholdDays;
        private List<OverdueMembershipItem> items;
    }

    @Data
    @Builder
    public static class OverdueMembershipItem {
        private Long membershipId;
        private Long familyDetailId;
        private String householdName;
        private MemberShipType memberShipType;
        private Double amount;
        private PaymentStatus paymentStatus;
        private Date createdDate;
        private long daysOverdue;
        private String notes;
    }

    @Data
    @Builder
    public static class SimpleMemberSection {
        private long count;
        private List<WatchlistMemberItem> items;
    }

    @Data
    @Builder
    public static class WatchlistMemberItem {
        private Long memberId;
        private String name;
        private Gender gender;
        private Long age;
        private Long familyDetailId;
        private String householdName;
        private String phoneNumber;
        private String note;
    }
}
