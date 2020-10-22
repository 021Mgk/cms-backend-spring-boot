package com.cms.model.service;


import com.cms.model.entity.File;
import com.cms.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServices {

    @Autowired
    GenericRepository<Long, File, String> repository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FileUploaderService fileUploaderService;


    @Transactional(rollbackFor = Exception.class)
    public void save(File file) {
        repository.save(file);
    }

    @Transactional
    public void update(File file) {
        repository.update(file);
    }

    @Transactional
    public void delete(File file) {
        repository.delete(file);
    }

    public List<File> findAll() {
        return repository.findAll(File.class);
    }

    public File findOne(File file, Long id) {
        return repository.findOne(file.getClass(), id);
    }

    public List<File> findByWhere(String where) {
        return repository.findByWhere(File.class, where);
    }

    public Object filePaging(int pageNo, int pageSize, String fileName) {
        Map map = new HashMap<String, Object>();
        int firstResult = (pageNo - 1) * pageSize;
        Query query;
        if (fileName == null) {
            query = entityManager.createNamedQuery("File.all");
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            List<File> files = query.getResultList();
            List<File> files2 = new ArrayList<>();
            for (File file : files) {
                file.setFileURL(fileUploaderService.getFileDownloadUri(file.getName()));
                files2.add(file);
            }
            map.put("data", files2);
            int count = ((Number) entityManager.createNamedQuery("File.countAll").getSingleResult()).intValue();
            map.put("count", count);
            return map;

        } else {
            query = entityManager.createNamedQuery("File.nameFilter");
            query.setParameter("name", "%" + fileName + "%");
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            List<File> files = query.getResultList();
            List<File> files2 = new ArrayList<>();
            for (File file : files) {
                file.setFileURL(fileUploaderService.getFileDownloadUri(file.getName()));
                files2.add(file);
            }
            map.put("data", files2);
            int count = ((Number) entityManager.createNamedQuery("File.countAllNameFilter")
                    .setParameter("name", "%" + fileName + "%")
                    .getSingleResult()).intValue();
            map.put("count", count);
            return map;
        }

    }
}
