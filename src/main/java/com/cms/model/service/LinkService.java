package com.cms.model.service;


import com.cms.model.entity.Link;
import com.cms.model.exception.FileStorageException;
import com.cms.model.exception.MyFileNotFoundException;
import com.cms.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class LinkService {

    @Autowired
    GenericRepository<Long , Link, String> repository;

    private final Path fileStorageLocation;

    @Autowired
    public LinkService(Link link) {
        this.fileStorageLocation = Paths.get(link.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Link links) {
        repository.save(links);
    }


    @Transactional
    public void update(Link links) {
        repository.update(links);
    }

    @Transactional
    public void delete(Link links){
        repository.delete(links);
    }


    public List<Link> findAll() {
        return repository.findAll(Link.class);
    }


    public Link findOne(Link links, Long id) {
        return repository.findOne(links.getClass(), id);
    }


    public List<Link> findByWhere(String where) {
        return repository.findByWhere(Link.class , where);
    }

}