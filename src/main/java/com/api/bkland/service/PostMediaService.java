package com.api.bkland.service;

import com.api.bkland.entity.PostMedia;
import com.api.bkland.repository.PostMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostMediaService {
    @Autowired
    private PostMediaRepository repository;

    public List<PostMedia> findByPostId(String postId) {
        return repository.findByPostId(postId);
    }

    @Transactional
    public void save(PostMedia postMedia) {
        repository.save(postMedia);
    }

    @Transactional
    public void deleteByPostId(String postId) {
        repository.deleteByPostId(postId);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

}
