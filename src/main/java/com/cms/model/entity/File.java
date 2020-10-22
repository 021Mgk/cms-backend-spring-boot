package com.cms.model.entity;


import javax.persistence.*;

@Entity(name = "file")
@Table(name = "file")
@NamedQueries({
        @NamedQuery(name = "File.all", query = "SELECT o FROM file o"),
        @NamedQuery(name = "File.countAll", query = "SELECT COUNT(o) FROM file o"),
        @NamedQuery(name="File.nameFilter" , query = "SELECT o FROM file o WHERE o.name LIKE :name"),
        @NamedQuery(name="File.countAllNameFilter" , query = "SELECT COUNT(o) FROM file o WHERE o.name LIKE :name")
})
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "user_id")
    private Long userId;
    @Transient
    private String fileURL;

    public File() {

    }

    public File(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }

    public File(String name, Long userId, String fileURL) {
        this.name = name;
        this.userId = userId;
        this.fileURL = fileURL;
    }

    public Long getId() {
        return id;
    }

    public File setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public File setName(String name) {
        this.name = name;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public File setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFileURL() {
        return fileURL;
    }

    public File setFileURL(String fileURL) {
        this.fileURL = fileURL;
        return this;
    }
}
