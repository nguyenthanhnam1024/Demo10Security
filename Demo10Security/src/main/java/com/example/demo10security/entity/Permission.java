package com.example.demo10security.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "permission")
public class Permission {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    public Permission() {
    }

    public Permission(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
