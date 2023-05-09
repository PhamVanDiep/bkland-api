package com.api.bkland.service;

import com.api.bkland.entity.Apartment;
import com.api.bkland.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository repository;

    public Apartment findByRealEstatePostId(String id) {
        return repository.findByRealEstatePostId(id).get();
    }

    @Transactional
    public Apartment create(Apartment apartment) {
        return repository.save(apartment);
    }
}
