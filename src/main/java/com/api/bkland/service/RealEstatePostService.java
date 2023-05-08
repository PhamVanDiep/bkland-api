package com.api.bkland.service;

import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.repository.RealEstatePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RealEstatePostService {
    @Autowired
    private RealEstatePostRepository repository;

    public RealEstatePost findById(String id) {
        Optional<RealEstatePost> realEstatePost = repository.findById(id);
        return realEstatePost.get();
    }
}
