package com.api.bkland.service;

import com.api.bkland.entity.Plot;
import com.api.bkland.repository.PlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlotService {
    @Autowired
    private PlotRepository repository;

    public Plot findByRealEstatePostId(String id) {
        return repository.findByRealEstatePostId(id).get();
    }

    @Transactional
    public Plot create(Plot plot) {
        return repository.save(plot);
    }

    @Transactional
    public Plot update(Plot plot) {
        return repository.save(plot);
    }

    public void deleteByRealEstatePostId(String realEstatePostId) {
        repository.deleteByRealEstatePostId(realEstatePostId);
    }
}
