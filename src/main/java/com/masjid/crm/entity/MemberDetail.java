package com.masjid.crm.entity;

import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "member_detail")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetail extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private MartialStatus martialStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Long age;

    @Column
    private String educationQualification;

    @Column
    private String occupation;

    @Column
    private String phoneNumber;

    @Column
    private String alternativeNumber;

    @Column
    private String bloodGroup;

    @ManyToOne
    @JoinColumn(name = "family_detail_id")
    private FamilyDetail familyDetail;

    @Column
    private String medicalCondition;

    @Column
    private Boolean hasMedicalIssue;

    @Column
    private Boolean hasDisability;

    @Column
    private String disabilityNotes;

    @Column
    private Boolean isStudent;

    @Column
    private LocalDate dateOfDivorce;

    @Column
    private String divorcedFromName;

    @Column
    private String divorceNotes;

    @Column
    private Boolean isHead;

}
