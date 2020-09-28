package com.cms.controller;

import com.cms.model.entity.AppUser;
import com.cms.model.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppUserController {

    @Autowired
    private AppUserService service;


    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/users")
    public List<AppUser> getAllUsers(){
        return service.findAll();
    }

    @RequestMapping("/users/{Id}")
    public AppUser findById(@PathVariable("Id") String appUserId){
        AppUser appUser = new AppUser();
        AppUser user = service.findOne(appUser ,Long.parseLong(appUserId));
        return  user;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void saveAppUser(@RequestBody AppUser appUser) {
            String hashedPassword = passwordEncoder.encode(appUser.getPassword());
            service.save(appUser.setPassword(hashedPassword));
    }


    @RequestMapping(value = "/users/{Id}", method = RequestMethod.PUT)
    public void updateAppUser(@PathVariable("Id") String appUserId, @RequestBody AppUser appUser) {
            service.update(appUser.setId(Long.parseLong(appUserId)));
    }

    @RequestMapping(value = "/users/{Id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("Id") String appUserId) {
        AppUser appUser = new AppUser();
        appUser.setId(Long.parseLong(appUserId));
        service.delete(appUser);
    }


}
