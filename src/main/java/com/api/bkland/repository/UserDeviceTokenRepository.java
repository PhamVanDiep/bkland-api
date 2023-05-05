package com.api.bkland.repository;

import com.api.bkland.entity.UserDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceToken, Integer> {
    Optional<UserDeviceToken> findByUserIdAndDeviceInfo(String userId, String deviceInfo);
}
