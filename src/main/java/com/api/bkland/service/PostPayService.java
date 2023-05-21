package com.api.bkland.service;

import com.api.bkland.entity.PostPay;
import com.api.bkland.repository.PostPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostPayService {
    @Autowired
    private PostPayRepository repository;

    @Transactional
    public PostPay createPostPay(PostPay postPay) {
        return repository.save(postPay);
    }

    public List<PostPay> findByUserId(String userId) {
        return repository.findByUserIdOrderByCreateAtDesc(userId);
    }

    public List<PostPay> findAllPostPays() {
        List<PostPay> postPays = repository.findAll(Sort.by(Sort.Direction.ASC, "createAt"));
        return postPays;
    }
}
