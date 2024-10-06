package com.masjid.crm.entity;

import com.masjid.crm.model.MarriageMemberType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "marriage_detail")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarriageDetail extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate dateOfMarriage;

    @Column
    private String certificateNumber;

    @Column
    private String details;

    @Enumerated(EnumType.STRING)
    @Column
    private MarriageMemberType marriageMemberType;

    @Column
    private String marriageMemberName;

    @Column
    private String marriageMemberPhone;

    @OneToOne
    private MemberDetail memberDetail;

    @Column
    private String notes;

}
