/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.bkland.controller;

import com.api.bkland.entity.Photo;
import com.api.bkland.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author dieppv
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @PostMapping("/api/v1/photos")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public String addPhoto(@RequestParam("title") String title,
            @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        logger.info("title: {}", title);
        logger.info("photo: {}", image);
        String id = photoService.addPhoto(title, image);
        return id;
    }

    @GetMapping("/api/no-auth/photos/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
//        model.addAttribute("title", photo.getTitle());
//        model.addAttribute("image",
//                Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return Base64.getEncoder().encodeToString(photo.getImage().getData());
    }
}
