package com.cms.model.service;

import com.cms.model.entity.AppUser;
import com.cms.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    GenericRepository<Long, AppUser,String> repository;

    public void save(AppUser appUser) {
        repository.save(appUser);
    }


    public void update(AppUser appUser) {
        repository.update(appUser);
    }

    public void delete(AppUser appUser){
        repository.delete(appUser);
    }


    public List<AppUser> findAll() {
        return repository.findAll(AppUser.class);
    }

    public AppUser findOne(AppUser appUser, Long id) {
        return repository.findOne(appUser.getClass(), id);
    }

    public List<AppUser> findByWhere(String where) {
        return repository.findByWhere(AppUser.class , where);
    }
}
