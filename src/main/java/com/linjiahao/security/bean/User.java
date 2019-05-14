package com.linjiahao.security.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_user")
public class User {
    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "c_username")
    private int username;
    @Column(name = "c_password")
    private int password;
    @Column(name = "c_nickname")
    private int nickname;
    @ManyToMany
    @JoinColumn
    private List<Role> roles;
}
