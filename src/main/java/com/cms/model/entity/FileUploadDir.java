package com.cms.model.entity;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@ConfigurationProperties(prefix = "file")
@Component
public class FileUploadDir {

    public FileUploadDir(){

    }

    public FileUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Column(name = "upload_dir")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public FileUploadDir setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
        return this;
    }
}
