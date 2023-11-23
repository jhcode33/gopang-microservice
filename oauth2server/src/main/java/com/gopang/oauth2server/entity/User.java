package com.gopang.oauth2server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "active")
    private Boolean active;

    @Builder
    public User(String username, String password, Role role, Boolean active) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(){}
}
