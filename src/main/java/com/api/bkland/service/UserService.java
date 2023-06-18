package com.api.bkland.service;

import com.api.bkland.entity.User;
import com.api.bkland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User findById(String id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) return null;
        return user.get();
    }

    @Transactional
    public User updateUserInfo(User user) {
        user.setUpdateAt(Instant.now());
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findByIdNot("anonymous");
    }

    public List<String> listRoles(String userId) {
        return repository.findRolesByUserId(userId);
    }
}
