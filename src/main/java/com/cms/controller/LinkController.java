package com.cms.controller;

import com.cms.model.entity.Link;
import com.cms.model.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class LinkController {

    @Autowired
    private LinkService linksService;


    @RequestMapping("/links")
    public List<Link> getLinks() {
        List<Link> links = linksService.findAll();
        return links;
    }


    @RequestMapping("/links/{Id}")
    public Link findById(@PathVariable("Id") String linkId) {
        Link links = new Link();
        Link link = linksService.findOne(links, Long.parseLong(linkId));
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/downloadFile/")
                .path(link.getIcon())
                .toUriString();
        link.setIcon(fileDownloadUri);
        return link;
    }


    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public void saveArticle(@RequestParam("file") MultipartFile file, @ModelAttribute Link links) {
        try {
            String fileName = file.getOriginalFilename();
            //Uploader.saveFile(file.getInputStream() , fileName);
            linksService.storeFile(file);
            links.setIcon(fileName);
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
                linksService.storeFile(file);
                links.setIcon(fileName);
            }
            links.setIcon(link.getIcon());
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


    @RequestMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = linksService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
