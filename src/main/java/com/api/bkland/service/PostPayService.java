package com.api.bkland.service;

import com.api.bkland.entity.PostPay;
import com.api.bkland.repository.PostPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostPayService {
    @Autowired
    private PostPayRepository repository;

    @Transactional
    public PostPay createPostPay(PostPay postPay) {
        return repository.save(postPay);
    }
}
