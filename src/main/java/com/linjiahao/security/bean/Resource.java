package com.linjiahao.security.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_resource")
public class Resource {
    @Id
    @Column(name = "c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "c_url")
    private String url;
    @Column(name = "c_resource_name")
    private String resourceName;
    @ManyToMany
    @JoinColumn
    private List<Role> roles;
}
