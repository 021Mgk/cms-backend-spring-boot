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