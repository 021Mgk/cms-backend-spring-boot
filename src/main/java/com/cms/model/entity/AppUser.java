package com.cms.model.entity;

import javax.persistence.*;

@Entity(name = "user")
@Table(name = "user_tbl")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String family;
    private String email;
    private String username;
    private String password;

    public AppUser(String name, String family, String email, String username , String password) {
        this.name = name;
        this.family = family;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public AppUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AppUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public AppUser setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AppUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
