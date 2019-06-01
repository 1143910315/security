package com.linjiahao.security.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "persistent_logins")
public class PersistentLogins {
    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String series;
    @Column(name = "username", nullable = false, length = 64)
    private String username;
    @Column(name = "token", nullable = false, length = 64)
    private String token;
    @Column(name = "last_used", nullable = false)
    private Timestamp last_used;
}
