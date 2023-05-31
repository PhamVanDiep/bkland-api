package com.api.bkland.service;

import com.api.bkland.entity.PriceFluctuation;
import com.api.bkland.repository.PriceFluctuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PriceFluctuationService {
    @Autowired
    private PriceFluctuationRepository repository;

    public List<PriceFluctuation> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Transactional
    public void save(PriceFluctuation priceFluctuation) {
        repository.save(priceFluctuation);
    }

    @Transactional
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    @Transactional
    public void updateStatus(boolean enable, String userId) {
        if (enable) {

        }
    }
}
