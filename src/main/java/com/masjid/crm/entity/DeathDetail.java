package com.masjid.crm.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "death_detail")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeathDetail extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String causeOfDeath;

    @Column
    private String placeOfDeath;

    @Column
    private LocalDate dateOfDeath;

    @Column
    private String deathCertificateNumber;

    @OneToOne
    private MemberDetail memberDetail;

    @OneToOne
    private String notes;

}
