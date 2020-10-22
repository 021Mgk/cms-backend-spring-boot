package com.cms.controller;


import com.cms.model.entity.AppUser;
import com.cms.model.entity.AuthRequest;
import com.cms.model.service.AppUserService;
import com.cms.service.CustomUserDetailsService;
import com.cms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService service;
    @Autowired
    private AppUserService appUserService;


    @RequestMapping(value = "/isValid")
    public Object isValid(HttpServletRequest request) throws Exception {
        Map map = new HashMap<String, String>();
        try {
            String token = jwtUtil.extractTokenFromCookie(request);
            System.out.println("token " + token);
            if (token == null) {
                map.put("success", false);
                return map;
            }
            String username = jwtUtil.extractUsername(token);
            System.out.println("username " + username);
            if (username == null) {
                map.put("success", false);
                return map;
            }
            UserDetails userDetails = service.loadUserByUsername(username);
            Boolean isValidToken = jwtUtil.validateToken(token, userDetails);
            System.out.println("valid " + isValidToken);
            if (!isValidToken) {
                map.put("success", false);
                return map;
            } else {
                AppUser appUser = appUserService.findByWhere("o.username = '" + username + "'").get(0);
                Map userInf = new HashMap<String, String>();
                userInf.put("name", appUser.getName());
                userInf.put("family", appUser.getFamily());
                userInf.put("username", appUser.getUsername());
                userInf.put("id", appUser.getId());
                map.put("userInfo", userInf);
                map.put("success", true);
                return map;
            }
        } catch (Exception e) {
            map.put("success", false);
            return map;
        }
    }


    @RequestMapping(value = "/logout")
    public Object logOut(HttpServletResponse response) throws Exception {
        Map map = new HashMap<String, String>();
        map.put("success", true);
        map.put("token", null);
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return map;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object getToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        Map map = new HashMap<String, String>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            String token = jwtUtil.generateToken(authRequest.getUsername());
            map.put("token", token);

            AppUser appUser = appUserService.findByWhere("o.username = '" + authRequest.getUsername() + "'").get(0);
            Map userInf = new HashMap<String, String>();
            userInf.put("name", appUser.getName());
            userInf.put("family", appUser.getFamily());
            userInf.put("username", appUser.getUsername());
            userInf.put("id", appUser.getId());
            map.put("userInfo", userInf);

            map.put("success", true);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            //cookie.setSecure(true);
            response.addCookie(cookie);

            return map;
        } catch (Exception e) {
            //throw  new Exception("invalid password or username");
            map.put("success", false);
            map.put("message", "invalid password or username");
            return map;
        }
    }
}
