package com.api.bkland.service;

import com.api.bkland.entity.Interested;
import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.entity.RealEstatePostPrice;
import com.api.bkland.entity.response.IEnableUserChat;
import com.api.bkland.entity.response.IPostInterested;
import com.api.bkland.entity.response.IRepEnableRequest;
import com.api.bkland.entity.response.IRepRequested;
import com.api.bkland.payload.dto.InterestedDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.repository.InterestedRepository;
import com.api.bkland.repository.RealEstatePostPriceRepository;
import com.api.bkland.repository.RealEstatePostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RealEstatePostService {
    @Autowired
    private RealEstatePostRepository repository;

    @Autowired
    private RealEstatePostPriceRepository realEstatePostPriceRepository;

    @Autowired
    private InterestedRepository interestedRepository;

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
        RealEstatePost saved = repository.save(realEstatePost);
        RealEstatePostPrice realEstatePostPrice = new RealEstatePostPrice();
        realEstatePostPrice.setId(0L);
        realEstatePostPrice.setPrice(saved.getPrice());
        realEstatePostPrice.setRealEstatePost(saved);
        realEstatePostPrice.setCreateBy(saved.getCreateBy());
        realEstatePostPrice.setCreateAt(Instant.now());
        realEstatePostPriceRepository.save(realEstatePostPrice);
        return saved;
    }

    @Transactional
    public void createRepPrice(Double price, String repId, String userId) {
        RealEstatePostPrice realEstatePostPrice = new RealEstatePostPrice();
        realEstatePostPrice.setId(0L);
        realEstatePostPrice.setRealEstatePost(findByIdAndEnable(repId));
        realEstatePostPrice.setCreateAt(Instant.now());
        realEstatePostPrice.setCreateBy(userId);
        realEstatePostPrice.setPrice(price);
        realEstatePostPriceRepository.save(realEstatePostPrice);
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

    @Transactional
    public Interested saveInterested(Interested interested) {
        return interestedRepository.save(interested);
    }

    public List<IPostInterested> findListInterestPostsOfUser(String userId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.findRepDetailByDeviceInfo(deviceInfo);
        }
        return interestedRepository.findRepDetailByUserId(userId);
    }

    @Transactional
    public void deleteInterested(Long id) {
        interestedRepository.deleteById(id);
    }

    public Optional<Interested> findByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId) {
        return interestedRepository.findByDeviceInfoAndRealEstatePostId(deviceInfo, realEstatePostId);
    }

    public Optional<Interested> findByUserIdAndRealEstatePostId(String userId, String realEstatePostId) {
        return interestedRepository.findByUserIdAndRealEstatePostId(userId, realEstatePostId);
    }

    public Object findContact(String id) {
        Optional<IEnableUserChat> optional = repository.findContact(id);
        if (optional.isEmpty()) {
            Optional<IEnableUserChat> ownerOptional = repository.findOwnerContact(id);
            if (ownerOptional.isEmpty()) {
                return null;
            }
            return ownerOptional.get();
        } else {
            return optional.get();
        }
    }

    public boolean isInterested(String userId, String realEstatePostId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.existsByDeviceInfoAndRealEstatePostId(deviceInfo, realEstatePostId);
        }
        return interestedRepository.existsByUserIdAndRealEstatePostId(userId, realEstatePostId);
    }

    public Integer countInterested(String userId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.countByUserIdAndDeviceInfo("anonymous", deviceInfo);
        }
        return interestedRepository.countByUserId(userId);
    }
}
