package com.cms.controller;

import com.cms.model.entity.AppUser;
import com.cms.model.entity.File;
import com.cms.model.service.AppUserService;
import com.cms.model.service.FileServices;
import com.cms.model.service.FileUploaderService;
import com.cms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1")
public class FileManagerController {

    @Autowired
    private FileServices services;
    @Autowired
    private FileUploaderService fileUploaderService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AppUserService appUserService;


    @RequestMapping(value = {"/files/{pageNo}/{pageSize}" , "/files/{pageNo}/{pageSize}/{fileName}"})
    public Object getAllFiles(@PathVariable("pageNo") String pageNo, @PathVariable("pageSize") String pageSize,@PathVariable(name = "fileName" , required = false) String fileName ) {
        System.out.println(pageNo);
        System.out.println(pageSize);
        System.out.println(fileName);
       return services.filePaging(Integer.parseInt(pageNo), Integer.parseInt(pageSize) , fileName);
    }




    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public void saveArticle(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        try {
            String username = jwtUtil.extractUsername(jwtUtil.extractTokenFromCookie(request));
            AppUser appUser = appUserService.findByWhere("o.username = '" + username + "'").get(0);

            Arrays.asList(files).stream().forEach( fileValue -> {
                File file = new File();
                file.setUserId(appUser.getId());
                String fileName = fileValue.getOriginalFilename();
                fileUploaderService.storeFile(fileValue);
                file.setName(fileName);
                services.save(file);
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

}
