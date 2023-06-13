package com.api.bkland.service;

import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.entity.response.IRepEnableRequest;
import com.api.bkland.entity.response.IRepRequested;
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

    @Transactional
    public void updatePostStatus(String status, String id) {
        repository.updateStatus(status, id);
    }

    @Transactional
    public void updateView(String realEstatePostId) {
        repository.updateView(realEstatePostId);
    }

    @Transactional
    public void updateClickedView(String realEstatePostId) {
        repository.updateClickedView(realEstatePostId);
    }

    public RealEstatePost findByIdAndEnable(String id) {
        Optional<RealEstatePost> realEstatePost = repository.findByIdAndEnable(id, true);
        if (realEstatePost.isEmpty()) {
            return null;
        }
        return realEstatePost.get();
    }

    public boolean existsByIdAndEnable(String id) {
        return repository.existsByIdAndEnable(id, true);
    }

    public List<IRepEnableRequest> enableRequestRep(String userId) {
        return repository.enableRequest(userId);
    }

    public List<IRepRequested> repRequested(String userId) {
        return repository.repRequested(userId);
    }

    public List<IRepRequested> requestedOfAgency(String agencyId) {
        return repository.requestedOfAgency(agencyId);
    }
}
