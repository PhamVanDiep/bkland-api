package com.api.bkland.service;

import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.repository.RealEstatePostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RealEstatePostService {
    @Autowired
    private RealEstatePostRepository repository;

    private Logger logger = LoggerFactory.getLogger(RealEstatePostService.class);

    public RealEstatePost findById(String id) {
        Optional<RealEstatePost> realEstatePost = repository.findById(id);
        return realEstatePost.get();
    }

    public List<RealEstatePost> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Transactional
    public RealEstatePost create(RealEstatePost realEstatePost) {
        return repository.save(realEstatePost);
    }

    @Transactional
    public RealEstatePost update(RealEstatePost realEstatePost) {
        return repository.save(realEstatePost);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public void disablePostExpire() {
        try {
            repository.disablePostExpire();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void disablePostById(String id) {
        repository.disablePostById(id);
    }

    public List<RealEstatePost> findAll() {
        return repository.findAll();
    }
}
