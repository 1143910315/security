package com.linjiahao.security.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_user", uniqueConstraints = {@UniqueConstraint(columnNames = "c_username")})
public class User {
    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "c_username")
    private String username;
    @Column(name = "c_password")
    private String password;
    @Column(name = "c_nickname")
    private String nickname;
    @ManyToMany
    @JoinColumn
    private List<Role> roles;
}
