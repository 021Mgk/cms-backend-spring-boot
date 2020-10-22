package com.cms.model.service;


import com.cms.model.entity.Ostan;
import com.cms.model.entity.Shahr;
import com.cms.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OstanSharService {
    @Autowired
    GenericRepository<Long, Shahr, String> shahrRepository;

    @Autowired
    GenericRepository<Long, Ostan, String> ostanRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveShahr(Shahr shahr) {
        shahrRepository.save(shahr);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOstan(Ostan ostan) {
        ostanRepository.save(ostan);
    }



    @Transactional
    public void updateShahr(Shahr shahr) {
        shahrRepository.update(shahr);
    }

    @Transactional
    public void updateOstan(Ostan ostan) {
        ostanRepository.update(ostan);
    }


    @Transactional
    public void deleteShahr(Shahr shahr) {
        shahrRepository.delete(shahr);
    }

    @Transactional
    public void deleteOstan(Ostan ostan) {
        ostanRepository.delete(ostan);
    }



    public List<Shahr> findAllShar() {
        return shahrRepository.findAll(Shahr.class);
    }
    public List<Ostan> findAllOstan() {
        return ostanRepository.findAll(Ostan.class);
    }

    public Shahr findOneShahr(Shahr shahr, Long id) {
        return shahrRepository.findOne(shahr.getClass(), id);
    }
    public Ostan findOneOstan(Ostan ostan, Long id) {
        return ostanRepository.findOne(ostan.getClass(), id);
    }

    public List<Shahr> findByWhereShahr(String where) {
        return shahrRepository.findByWhere(Shahr.class, where);
    }
    public List<Ostan> findByWhereOstan(String where) {
        return ostanRepository.findByWhere(Ostan.class, where);
    }
}

