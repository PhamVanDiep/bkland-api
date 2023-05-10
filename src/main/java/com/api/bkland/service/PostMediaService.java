package com.api.bkland.service;

import com.api.bkland.entity.PostMedia;
import com.api.bkland.repository.PostMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostMediaService {
    @Autowired
    private PostMediaRepository repository;

    public List<PostMedia> findByPostId(String postId) {
        return repository.findByPostId(postId);
    }

    public void save(PostMedia postMedia) {
        repository.save(postMedia);
    }
}
