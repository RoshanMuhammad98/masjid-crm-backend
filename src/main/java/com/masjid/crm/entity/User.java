package com.masjid.crm.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @OneToOne
    private Role role;

    @Column
    private String code;

    @Column
    private String username;

    @Column
    private String phoneNumber;

    @Column
    private String name;

    @Column
    private Boolean active;

    @Column
    private Long centerId;

}
