package com.api.bkland.service;

import com.api.bkland.entity.UserDeviceToken;
import com.api.bkland.repository.UserDeviceTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDeviceTokenService {
    @Autowired
    private UserDeviceTokenRepository repository;

    public UserDeviceToken findByUserIdAndDeviceInfo(String userId, String deviceInfo) {
        Optional<UserDeviceToken> userDeviceToken = repository.findByUserIdAndDeviceInfo(userId, deviceInfo);
        if (userDeviceToken.isEmpty()) {
            return null;
        }
        return userDeviceToken.get();
    }

    @Transactional
    public void update(UserDeviceToken userDeviceToken) {
        repository.save(userDeviceToken);
    }
}
