package com.masjid.crm.entity;

import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

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

    @OneToOne
    private FamilyDetail familyDetail;

}
