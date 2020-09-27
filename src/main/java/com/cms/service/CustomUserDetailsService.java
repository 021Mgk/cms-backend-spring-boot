package com.cms.service;

import com.cms.model.entity.AppUser;
import com.cms.model.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AppUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser =  service.findByWhere("o.username = '" + username + "'").get(0);
        return new User(appUser.getUsername() , appUser.getPassword() , new ArrayList<>());
    }
}
