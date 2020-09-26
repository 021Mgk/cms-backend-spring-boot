package com.cms.model.entity;



import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;


@ConfigurationProperties(prefix = "file")
@Entity(name = "link")
@Table(name="link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private int place;
    private int ord;
    private String icon;

    @Column(name = "upload_dir")
    private String uploadDir;

    @Column(columnDefinition = "int", length = 1)
    private boolean active;


    public Link() {

    }

    public Link(String title, String link, int place, int ord, String icon, String uploadDir, boolean active) {
        this.title = title;
        this.link = link;
        this.place = place;
        this.ord = ord;
        this.icon = icon;
        this.uploadDir = uploadDir;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Link setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Link setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Link setLink(String link) {
        this.link = link;
        return this;
    }

    public int getPlace() {
        return place;
    }

    public Link setPlace(int place) {
        this.place = place;
        return this;
    }

    public int getOrd() {
        return ord;
    }

    public Link setOrd(int ord) {
        this.ord = ord;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Link setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public Link setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Link setActive(boolean active) {
        this.active = active;
        return this;
    }
}