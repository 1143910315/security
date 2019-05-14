package com.linjiahao.security.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_role")
public class Role {
    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "c_rolename")
    private int rolename;
    @Column(name = "c_rolename_zh")
    private int rolenameZh;
}
