package com.masjid.crm.dto.response;

import com.masjid.crm.model.FamilyStatus;
import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import com.masjid.crm.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardInsightsResponse {

    private HeaderStats headerStats;
    private Demographics demographics;
    private FamilyStatusBlock familyStatus;
    private FinancialBlock financial;
    private MembershipBlock membership;
    private ActivityBlock activity;
    private Trends trends;
    private PeriodBlock period;

    @Data
    @Builder
    public static class PeriodBlock {
        private String from;
        private String to;
    }

    @Data
    @Builder
    public static class HeaderStats {
        private long totalFamilies;
        private long totalMembers;
        private long aliveMembers;
        private long deceasedMembers;
        private long totalMarriages;
        private long totalDeaths;
        private double currentBalance;
    }

    @Data
    @Builder
    public static class Demographics {
        private Map<Gender, Long> gender;
        private Map<MartialStatus, Long> maritalStatus;
        private AgeBuckets ageBuckets;
        private long studentsCount;
        private DisabilityBlock disability;
        private long medicalIssueCount;
        private double averageAge;
        private Map<String, Long> bloodGroupBreakdown;
    }

    @Data
    @Builder
    public static class AgeBuckets {
        private long children;   // 0-12
        private long teens;      // 13-19
        private long adults;     // 20-59
        private long seniors;    // 60+
    }

    @Data
    @Builder
    public static class DisabilityBlock {
        private long total;
        private long male;
        private long female;
    }

    @Data
    @Builder
    public static class FamilyStatusBlock {
        private Map<FamilyStatus, Long> breakdown;
        private double totalHouseholdIncome;
        private double averageHouseholdIncome;
        private long familiesWithHouseNumber;
        private long familiesWithoutHouseNumber;
        private long totalDeclaredMembers;
        private long totalRegisteredMembers;
        private long pendingMemberRegistrations;
        private long familiesWithIncompleteRegistration;
    }

    @Data
    @Builder
    public static class FinancialBlock {
        private double totalIncome;
        private double totalExpense;
        private double balance;
        private double pendingIncome;
        private double pendingExpense;
        private long cancelledCount;
        private Map<FinancialCategory, Double> incomeByCategory;
        private Map<FinancialCategory, Double> expenseByCategory;
    }

    @Data
    @Builder
    public static class MembershipBlock {
        private Map<PaymentStatus, Long> statusCounts;
        private double totalCollected;
        private double outstandingDues;
        private double cancelledAmount;
    }

    @Data
    @Builder
    public static class ActivityBlock {
        private long newFamilies;
        private long newMembers;
        private long newMarriages;
        private long newDeaths;
        private long newTransactions;
    }

    @Data
    @Builder
    public static class Trends {
        private List<String> months;
        private List<Double> income;
        private List<Double> expense;
        private List<Long> marriages;
        private List<Long> deaths;
    }
}
