package com.api.bkland.service;

import com.api.bkland.entity.House;
import com.api.bkland.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseService {
    @Autowired
    private HouseRepository repository;

    public House findByRealEstatePostId(String id) {
        return repository.findByRealEstatePostId(id).get();
    }

    @Transactional
    public House create(House house) {
        return repository.save(house);
    }

    @Transactional
    public House update(House house) {
        return repository.save(house);
    }

    public void deleteByRealEstatePostId(String realEstatePostId) {
        repository.deleteByRealEstatePostId(realEstatePostId);
    }
}
