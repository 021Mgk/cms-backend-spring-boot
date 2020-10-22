package com.cms.controller;

import com.cms.model.entity.AppUser;
import com.cms.model.entity.Link;
import com.cms.model.service.AppUserService;
import com.cms.model.service.FileUploaderService;
import com.cms.model.service.LinkService;
import com.cms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1")
public class LinkController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LinkService linksService;

    @Autowired
    private FileUploaderService fileUploaderService;

    @Autowired
    private AppUserService appUserService;


    @RequestMapping("/links")
    public List<Link> getLinks() {
        List<Link> links = linksService.findAll();
        return links;
    }

    @RequestMapping("/userLinks")
    public List<Link> getUserLinks(HttpServletRequest request) {
        List<Link> links = null;
        try {
            String username = jwtUtil.extractUsername(jwtUtil.extractTokenFromCookie(request));
            AppUser appUser = appUserService.findByWhere("o.username = '" + username + "'").get(0);
            links = linksService.findByWhere("o.userId =" + appUser.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }


    @RequestMapping("/links/{Id}")
    public Link findById(@PathVariable("Id") String linkId) {
        Link links = new Link();
        Link link = linksService.findOne(links, Long.parseLong(linkId));
        link.setIcon(fileUploaderService.getFileDownloadUri(link.getIcon()));
        return link;
    }


    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public void saveArticle(@RequestParam(required = false, name = "file") MultipartFile file, @ModelAttribute Link links, HttpServletRequest request) {
        try {
            String username = jwtUtil.extractUsername(jwtUtil.extractTokenFromCookie(request));
            String fileName = file.getOriginalFilename();
            fileUploaderService.storeFile(file);
            AppUser appUser = appUserService.findByWhere("o.username = '" + username + "'").get(0);
            links.setIcon(fileName);
            links.setUserId(appUser.getId());
            linksService.save(links);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/links/{Id}", method = RequestMethod.PUT)
    public void updateArticle(@PathVariable("Id") String linkId, @RequestParam(required = false, name = "file") MultipartFile file, @ModelAttribute Link links) {
        Link link = linksService.findOne(links, Long.parseLong(linkId));
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                fileUploaderService.storeFile(file);
                links.setIcon(fileName);
            } else {
                links.setIcon(link.getIcon());
            }
            linksService.update(links.setId(Long.parseLong(linkId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/links/{Id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("Id") String linkId) {
        Link links = new Link();
        links.setId(Long.parseLong(linkId));
        linksService.delete(links);
    }


    @RequestMapping("/links/footer")
    public List<Link> getFooterLinks() {
        List<Link> links = linksService.findByWhere("o.place = 2");
        return links;
    }

    @RequestMapping("/links/header")
    public List<Link> getHeaderLinks() {
        List<Link> links = linksService.findByWhere("o.place = 1");
        return links;
    }

}
