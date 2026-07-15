package com.masjid.crm.service;

import com.masjid.crm.dto.response.DashboardInsightsResponse;
import com.masjid.crm.dto.response.DashboardInsightsResponse.ActivityBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.AgeBuckets;
import com.masjid.crm.dto.response.DashboardInsightsResponse.Demographics;
import com.masjid.crm.dto.response.DashboardInsightsResponse.DisabilityBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.FamilyStatusBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.FinancialBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.HeaderStats;
import com.masjid.crm.dto.response.DashboardInsightsResponse.MembershipBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.PeriodBlock;
import com.masjid.crm.dto.response.DashboardInsightsResponse.Trends;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.FinancialAccount;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.FamilyStatus;
import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.model.TransactionType;
import com.masjid.crm.repository.DeathDetailRepository;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.FinancialAccountRepository;
import com.masjid.crm.repository.MarriageRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
import com.masjid.crm.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DashboardInsightsService {

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private DeathDetailRepository deathDetailRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private FinancialAccountRepository financialAccountRepository;

    public DashboardInsightsResponse getInsights(LocalDate fromDate, LocalDate toDate,
                                                 FamilyStatus familyStatusFilter,
                                                 Gender genderFilter) {
        LocalDate now = LocalDate.now();
        LocalDate periodFrom = fromDate != null ? fromDate : now.withDayOfMonth(1);
        LocalDate periodTo = toDate != null ? toDate : now;

        List<FamilyDetail> allFamilies = familyDetailRepository.findAll();
        List<MemberDetail> allMembers = memberDetailsRepository.findAll();
        List<MarriageDetail> allMarriages = marriageRepository.findAll();
        List<DeathDetail> allDeaths = deathDetailRepository.findAll();
        List<MembershipDetail> allMemberships = membershipRepository.findAll();
        List<FinancialAccount> allTransactions = financialAccountRepository.findAll();

        List<FamilyDetail> families = familyStatusFilter == null
                ? allFamilies
                : filter(allFamilies, f -> f.getFamilyStatus() == familyStatusFilter);

        Set<Long> filteredFamilyIds = new HashSet<>();
        for (FamilyDetail f : families) filteredFamilyIds.add(f.getId());

        List<MemberDetail> members = filter(allMembers, m ->
                (familyStatusFilter == null || (m.getFamilyDetail() != null
                        && filteredFamilyIds.contains(m.getFamilyDetail().getId())))
                && (genderFilter == null || m.getGender() == genderFilter));

        Set<Long> deceasedMemberIds = new HashSet<>();
        for (DeathDetail d : allDeaths) {
            if (d.getMemberDetail() != null) deceasedMemberIds.add(d.getMemberDetail().getId());
        }

        double totalIncome = sumByType(allTransactions, TransactionType.INCOME, PaymentStatus.RECEIVED);
        double totalExpense = sumByType(allTransactions, TransactionType.EXPENSE, PaymentStatus.RECEIVED);

        return DashboardInsightsResponse.builder()
                .period(PeriodBlock.builder()
                        .from(periodFrom.toString())
                        .to(periodTo.toString())
                        .build())
                .headerStats(buildHeaderStats(families, members, deceasedMemberIds,
                        allMarriages, allDeaths, totalIncome - totalExpense))
                .demographics(buildDemographics(members, deceasedMemberIds))
                .familyStatus(buildFamilyStatus(families, allMembers))
                .financial(buildFinancial(allTransactions))
                .membership(buildMembership(allMemberships))
                .activity(buildActivity(allFamilies, allMembers, allMarriages,
                        allDeaths, allTransactions, periodFrom, periodTo))
                .trends(buildTrends(allTransactions, allMarriages, allDeaths))
                .build();
    }

    private HeaderStats buildHeaderStats(List<FamilyDetail> families, List<MemberDetail> members,
                                         Set<Long> deceasedIds, List<MarriageDetail> marriages,
                                         List<DeathDetail> deaths, double balance) {
        long alive = members.stream().filter(m -> !deceasedIds.contains(m.getId())).count();
        return HeaderStats.builder()
                .totalFamilies(families.size())
                .totalMembers(members.size())
                .aliveMembers(alive)
                .deceasedMembers(members.size() - alive)
                .totalMarriages(marriages.size())
                .totalDeaths(deaths.size())
                .currentBalance(balance)
                .build();
    }

    private Demographics buildDemographics(List<MemberDetail> members, Set<Long> deceasedIds) {
        Map<Gender, Long> gender = new EnumMap<>(Gender.class);
        Map<MartialStatus, Long> marital = new EnumMap<>(MartialStatus.class);
        Map<String, Long> bloodGroup = new HashMap<>();

        long students = 0, medicalIssue = 0, disabilityTotal = 0;
        long disabilityMale = 0, disabilityFemale = 0;
        long children = 0, teens = 0, adults = 0, seniors = 0;
        long ageSum = 0, ageCount = 0;

        for (MemberDetail m : members) {
            if (deceasedIds.contains(m.getId())) continue;

            if (m.getGender() != null) gender.merge(m.getGender(), 1L, Long::sum);
            if (m.getMartialStatus() != null) marital.merge(m.getMartialStatus(), 1L, Long::sum);
            if (m.getBloodGroup() != null && !m.getBloodGroup().isEmpty()) {
                bloodGroup.merge(m.getBloodGroup(), 1L, Long::sum);
            }
            if (Boolean.TRUE.equals(m.getIsStudent())) students++;
            if (Boolean.TRUE.equals(m.getHasMedicalIssue())) medicalIssue++;
            if (Boolean.TRUE.equals(m.getHasDisability())) {
                disabilityTotal++;
                if (m.getGender() == Gender.MALE) disabilityMale++;
                else if (m.getGender() == Gender.FEMALE) disabilityFemale++;
            }
            if (m.getAge() != null) {
                long a = m.getAge();
                ageSum += a;
                ageCount++;
                if (a <= 12) children++;
                else if (a <= 19) teens++;
                else if (a <= 59) adults++;
                else seniors++;
            }
        }

        return Demographics.builder()
                .gender(gender)
                .maritalStatus(marital)
                .ageBuckets(AgeBuckets.builder()
                        .children(children).teens(teens).adults(adults).seniors(seniors)
                        .build())
                .studentsCount(students)
                .disability(DisabilityBlock.builder()
                        .total(disabilityTotal).male(disabilityMale).female(disabilityFemale)
                        .build())
                .medicalIssueCount(medicalIssue)
                .averageAge(ageCount == 0 ? 0.0 : (double) ageSum / ageCount)
                .bloodGroupBreakdown(bloodGroup)
                .build();
    }

    private FamilyStatusBlock buildFamilyStatus(List<FamilyDetail> families, List<MemberDetail> allMembers) {
        Map<FamilyStatus, Long> breakdown = new EnumMap<>(FamilyStatus.class);
        double totalIncome = 0;
        long incomeCount = 0;
        long withHouseNumber = 0;
        long declaredTotal = 0;
        long registeredTotal = 0;
        long pendingTotal = 0;
        long incompleteFamilies = 0;

        Map<Long, Long> registeredByFamily = new HashMap<>();
        for (MemberDetail m : allMembers) {
            if (m.getFamilyDetail() != null) {
                registeredByFamily.merge(m.getFamilyDetail().getId(), 1L, Long::sum);
            }
        }

        for (FamilyDetail f : families) {
            if (f.getFamilyStatus() != null) {
                breakdown.merge(f.getFamilyStatus(), 1L, Long::sum);
            }
            if (f.getHouseholdIncome() != null) {
                totalIncome += f.getHouseholdIncome();
                incomeCount++;
            }
            if (f.getHouseNumber() != null && !f.getHouseNumber().isEmpty()) {
                withHouseNumber++;
            }

            long registered = registeredByFamily.getOrDefault(f.getId(), 0L);
            registeredTotal += registered;
            if (f.getTotalMembersCount() != null) {
                long declared = f.getTotalMembersCount();
                declaredTotal += declared;
                long pending = Math.max(0, declared - registered);
                pendingTotal += pending;
                if (pending > 0) incompleteFamilies++;
            }
        }

        return FamilyStatusBlock.builder()
                .breakdown(breakdown)
                .totalHouseholdIncome(totalIncome)
                .averageHouseholdIncome(incomeCount == 0 ? 0.0 : totalIncome / incomeCount)
                .familiesWithHouseNumber(withHouseNumber)
                .familiesWithoutHouseNumber(families.size() - withHouseNumber)
                .totalDeclaredMembers(declaredTotal)
                .totalRegisteredMembers(registeredTotal)
                .pendingMemberRegistrations(pendingTotal)
                .familiesWithIncompleteRegistration(incompleteFamilies)
                .build();
    }

    private FinancialBlock buildFinancial(List<FinancialAccount> txs) {
        double totalIncome = 0, totalExpense = 0, pendingIncome = 0, pendingExpense = 0;
        long cancelled = 0;
        Map<FinancialCategory, Double> incomeByCat = new EnumMap<>(FinancialCategory.class);
        Map<FinancialCategory, Double> expenseByCat = new EnumMap<>(FinancialCategory.class);

        for (FinancialAccount f : txs) {
            if (f.getAmount() == null || f.getTransactionType() == null) continue;
            PaymentStatus status = f.getPaymentStatus();
            if (status == PaymentStatus.CANCELLED) {
                cancelled++;
                continue;
            }

            boolean pending = status == PaymentStatus.PENDING;
            if (f.getTransactionType() == TransactionType.INCOME) {
                if (pending) pendingIncome += f.getAmount();
                else {
                    totalIncome += f.getAmount();
                    if (f.getCategory() != null)
                        incomeByCat.merge(f.getCategory(), f.getAmount(), Double::sum);
                }
            } else if (f.getTransactionType() == TransactionType.EXPENSE) {
                if (pending) pendingExpense += f.getAmount();
                else {
                    totalExpense += f.getAmount();
                    if (f.getCategory() != null)
                        expenseByCat.merge(f.getCategory(), f.getAmount(), Double::sum);
                }
            }
        }

        return FinancialBlock.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(totalIncome - totalExpense)
                .pendingIncome(pendingIncome)
                .pendingExpense(pendingExpense)
                .cancelledCount(cancelled)
                .incomeByCategory(incomeByCat)
                .expenseByCategory(expenseByCat)
                .build();
    }

    private MembershipBlock buildMembership(List<MembershipDetail> memberships) {
        Map<PaymentStatus, Long> counts = new EnumMap<>(PaymentStatus.class);
        double collected = 0, outstanding = 0, cancelledAmt = 0;

        for (MembershipDetail m : memberships) {
            PaymentStatus s = m.getPaymentStatus();
            if (s != null) counts.merge(s, 1L, Long::sum);
            if (m.getAmount() == null) continue;

            if (s == PaymentStatus.RECEIVED) collected += m.getAmount();
            else if (s == PaymentStatus.PENDING) outstanding += m.getAmount();
            else if (s == PaymentStatus.CANCELLED) cancelledAmt += m.getAmount();
        }

        return MembershipBlock.builder()
                .statusCounts(counts)
                .totalCollected(collected)
                .outstandingDues(outstanding)
                .cancelledAmount(cancelledAmt)
                .build();
    }

    private ActivityBlock buildActivity(List<FamilyDetail> families, List<MemberDetail> members,
                                        List<MarriageDetail> marriages, List<DeathDetail> deaths,
                                        List<FinancialAccount> txs,
                                        LocalDate from, LocalDate to) {
        return ActivityBlock.builder()
                .newFamilies(families.stream().filter(f -> inRange(f.getCreatedDate(), from, to)).count())
                .newMembers(members.stream().filter(m -> inRange(m.getCreatedDate(), from, to)).count())
                .newMarriages(marriages.stream().filter(m -> inRange(m.getCreatedDate(), from, to)).count())
                .newDeaths(deaths.stream().filter(d -> inRange(d.getCreatedDate(), from, to)).count())
                .newTransactions(txs.stream().filter(t -> inRange(t.getCreatedDate(), from, to)).count())
                .build();
    }

    private Trends buildTrends(List<FinancialAccount> txs, List<MarriageDetail> marriages,
                               List<DeathDetail> deaths) {
        YearMonth current = YearMonth.now();
        List<YearMonth> monthsList = new ArrayList<>();
        for (int i = 11; i >= 0; i--) monthsList.add(current.minusMonths(i));

        Map<YearMonth, Double> income = new HashMap<>();
        Map<YearMonth, Double> expense = new HashMap<>();
        Map<YearMonth, Long> marriagesPerMonth = new HashMap<>();
        Map<YearMonth, Long> deathsPerMonth = new HashMap<>();

        for (YearMonth ym : monthsList) {
            income.put(ym, 0.0);
            expense.put(ym, 0.0);
            marriagesPerMonth.put(ym, 0L);
            deathsPerMonth.put(ym, 0L);
        }

        for (FinancialAccount f : txs) {
            if (f.getTransactionDate() == null || f.getAmount() == null
                    || f.getPaymentStatus() != PaymentStatus.RECEIVED) continue;
            YearMonth ym = toYearMonth(f.getTransactionDate());
            if (!income.containsKey(ym)) continue;
            if (f.getTransactionType() == TransactionType.INCOME)
                income.merge(ym, f.getAmount(), Double::sum);
            else if (f.getTransactionType() == TransactionType.EXPENSE)
                expense.merge(ym, f.getAmount(), Double::sum);
        }

        for (MarriageDetail m : marriages) {
            if (m.getDateOfMarriage() == null) continue;
            YearMonth ym = YearMonth.from(m.getDateOfMarriage());
            if (marriagesPerMonth.containsKey(ym))
                marriagesPerMonth.merge(ym, 1L, Long::sum);
        }

        for (DeathDetail d : deaths) {
            if (d.getDateOfDeath() == null) continue;
            YearMonth ym = YearMonth.from(d.getDateOfDeath());
            if (deathsPerMonth.containsKey(ym))
                deathsPerMonth.merge(ym, 1L, Long::sum);
        }

        List<String> labels = new ArrayList<>();
        List<Double> incomeSeries = new ArrayList<>();
        List<Double> expenseSeries = new ArrayList<>();
        List<Long> marriageSeries = new ArrayList<>();
        List<Long> deathSeries = new ArrayList<>();
        for (YearMonth ym : monthsList) {
            labels.add(ym.toString());
            incomeSeries.add(income.get(ym));
            expenseSeries.add(expense.get(ym));
            marriageSeries.add(marriagesPerMonth.get(ym));
            deathSeries.add(deathsPerMonth.get(ym));
        }

        return Trends.builder()
                .months(labels)
                .income(incomeSeries)
                .expense(expenseSeries)
                .marriages(marriageSeries)
                .deaths(deathSeries)
                .build();
    }

    private YearMonth toYearMonth(Date date) {
        return YearMonth.from(toLocalDate(date));
    }

    private boolean inRange(Date date, LocalDate from, LocalDate to) {
        if (date == null) return false;
        LocalDate d = toLocalDate(date);
        return !d.isBefore(from) && !d.isAfter(to);
    }

    private LocalDate toLocalDate(Date date) {
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private <T> List<T> filter(List<T> list, java.util.function.Predicate<T> pred) {
        List<T> out = new ArrayList<>();
        for (T t : list) if (pred.test(t)) out.add(t);
        return out;
    }

    private double sumByType(List<FinancialAccount> txs, TransactionType type, PaymentStatus status) {
        double sum = 0;
        for (FinancialAccount f : txs) {
            if (f.getAmount() == null) continue;
            if (f.getTransactionType() == type && f.getPaymentStatus() == status) {
                sum += f.getAmount();
            }
        }
        return sum;
    }
}
