package com.api.bkland.service;

import com.api.bkland.entity.PostMedia;
import com.api.bkland.repository.PhotoRepository;
import com.api.bkland.repository.PostMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostMediaService {
    @Autowired
    private PostMediaRepository repository;

    @Autowired
    private PhotoRepository photoRepository;

    public List<PostMedia> findByPostId(String postId) {
        return repository.findByPostId(postId);
    }

    public void save(PostMedia postMedia) {
        repository.save(postMedia);
    }

    public void deleteByPostId(String postId) {
        repository.deleteByPostId(postId);
        List<PostMedia> postMediaList = repository.findByPostId(postId);
        for (PostMedia postMedia :
                postMediaList) {
            photoRepository.deleteById(postMedia.getId());
        }
    }
}
