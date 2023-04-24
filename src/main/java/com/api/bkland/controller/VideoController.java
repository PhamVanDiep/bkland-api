/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.bkland.controller;

import com.api.bkland.payload.dto.VideoDTO;
import com.api.bkland.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author dieppv
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/add")
    public String addVideo(@RequestParam("title") String title,
            @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = videoService.addVideo(title, file);
        return "redirect:/videos/" + id;
    }

    @GetMapping("/{id}")
    public String getVideo(@PathVariable String id, Model model) throws Exception {
        VideoDTO video = videoService.getVideo(id);
        model.addAttribute("title", video.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return "videos";
    }

    @GetMapping("/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        VideoDTO video = videoService.getVideo(id);
        FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }
}
