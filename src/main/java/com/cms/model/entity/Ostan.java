package com.cms.model.entity;


import javax.persistence.*;

@Entity(name = "state")
@Table(name ="state")
public class Ostan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active;

    public Ostan() {
    }

    public Ostan(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Ostan setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ostan setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Ostan setActive(boolean active) {
        this.active = active;
        return this;
    }
}
