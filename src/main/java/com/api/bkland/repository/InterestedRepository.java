package com.api.bkland.repository;

import com.api.bkland.entity.Interested;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<Interested, Long> {
    List<Interested> findByUserIdAndDeviceInfo(String userId, String deviceInfo);
    List<Interested> findByUserId(String userId);
    boolean existsByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId);
    boolean existsByUserIdAndRealEstatePostId(String userId, String realEstatePostId);
    Optional<Interested> findByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId);
    Optional<Interested> findByUserIdAndRealEstatePostId(String userId, String realEstatePostId);
}
