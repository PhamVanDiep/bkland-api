package com.api.bkland.service;

import com.api.bkland.entity.House;
import com.api.bkland.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    @Autowired
    private HouseRepository repository;

    public House findByRealEstatePostId(String id) {
        return repository.findByRealEstatePostId(id).get();
    }
}
