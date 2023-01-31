package com.example.demo10security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();

    public Role(Long id, String name, Set<Permission> permissionSet) {
        this.id = id;
        this.name = name;
        this.permissions = permissionSet;
    }

    public Role() {

    }
}
