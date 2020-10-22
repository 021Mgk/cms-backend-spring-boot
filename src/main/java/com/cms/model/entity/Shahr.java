package com.cms.model.entity;


import javax.persistence.*;

@Entity(name="city")
@Table(name="city")
public class Shahr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active;
    @Column(name ="ostan_id")
    private Long ostanId;

    public Shahr() {
    }

    public Shahr(Long id, String name, boolean active, Long ostanId) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.ostanId = ostanId;
    }

    public Long getId() {
        return id;
    }

    public Shahr setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Shahr setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Shahr setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Long getOstanId() {
        return ostanId;
    }

    public Shahr setOstanId(Long ostanId) {
        this.ostanId = ostanId;
        return this;
    }
}
