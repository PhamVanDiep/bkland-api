package com.api.bkland.service;

import com.api.bkland.entity.Charge;
import com.api.bkland.repository.ChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChargeService {
    @Autowired
    private ChargeRepository repository;

    @Transactional
    public Charge insert(Charge charge) {
        return repository.save(charge);
    }

    @Transactional
    public Charge update(Charge charge) {
        return repository.save(charge);
    }

    public List<Charge> findAll() {
        return repository.findAll();
    }

    public List<Charge> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
