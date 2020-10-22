package com.cms.controller;


import com.cms.model.entity.AppUser;
import com.cms.model.entity.Article;
import com.cms.model.service.AppUserService;
import com.cms.model.service.ArticleService;
import com.cms.model.service.FileUploaderService;
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
public class ArticleController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private FileUploaderService fileUploaderService;


    @RequestMapping("/articles")
    public List<Article> getArticles() {
        List<Article> articles = articleService.findAll();
        return articles;
    }

    @RequestMapping("/userArticles")
    public List<Article> getUserLinks(HttpServletRequest request) {
        List<Article> articles = null;
        try {
            String username = jwtUtil.extractUsername(jwtUtil.extractTokenFromCookie(request));
            AppUser appUser = appUserService.findByWhere("o.username = '" + username + "'").get(0);
            articles = articleService.findByWhere("o.userId =" + appUser.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }


    @RequestMapping("/articles/{Id}")
    public Article findById(@PathVariable("Id") String articleId) {
        Article article = new Article();
        Article art = articleService.findOne(article, Long.parseLong(articleId));
        String cover = fileUploaderService.getFileDownloadUri(art.getCover());
        art.setCover(cover);
        String thumbnail = fileUploaderService.getFileDownloadUri(art.getThumbnail());
        art.setThumbnail(thumbnail);
        String attachment = fileUploaderService.getFileDownloadUri(art.getAttachment());
        art.setAttachment(attachment);
        return art;
    }


    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public void saveArticle(@ModelAttribute Article article,
                            @RequestParam("cov") MultipartFile cov,
                            @RequestParam("thu") MultipartFile thu,
                            @RequestParam("att") MultipartFile att) {
        try {
            String covName = cov.getOriginalFilename();
            fileUploaderService.storeFile(cov);
            article.setCover(covName);

            String thuName = thu.getOriginalFilename();
            fileUploaderService.storeFile(thu);
            article.setThumbnail(thuName);

            String attName = att.getOriginalFilename();
            fileUploaderService.storeFile(att);
            article.setAttachment(attName);

            articleService.save(article);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @RequestMapping(value = "/articles/{Id}", method = RequestMethod.PUT)
    public void updateArticle(@PathVariable("Id") String articleId,
                              @RequestParam(required = false, name = "cov") MultipartFile cov,
                              @RequestParam(required = false, name = "thu") MultipartFile thu,
                              @RequestParam(required = false, name = "att") MultipartFile att,
                              @ModelAttribute Article article) {
        Article art = articleService.findOne(article, Long.parseLong(articleId));
        try {
            if (cov != null) {
                String covName = cov.getOriginalFilename();
                fileUploaderService.storeFile(cov);
                article.setCover(covName);
            } else {
                article.setCover(art.getCover());
            }
            if (thu != null) {
                String thuName = thu.getOriginalFilename();
                fileUploaderService.storeFile(thu);
                article.setThumbnail(thuName);
            } else {
                article.setThumbnail(art.getThumbnail());
            }
            if (att != null) {
                String attName = att.getOriginalFilename();
                fileUploaderService.storeFile(att);
                article.setAttachment(attName);
            } else {
                article.setAttachment(art.getAttachment());
            }

            articleService.update(article.setId(Long.parseLong(articleId)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/articles/{Id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("Id") String articleId) {
        Article article = new Article();
        article.setId(Long.parseLong(articleId));
        articleService.delete(article);
    }

}