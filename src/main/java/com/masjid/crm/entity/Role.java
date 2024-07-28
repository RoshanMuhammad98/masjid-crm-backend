package com.masjid.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String prefix;

    @Column
    private String kareflowUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permissions", joinColumns = {
            @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permissions_id") })
    private Set<Permission> permissions = new HashSet<>();


}
