package com.api.bkland.service;

import com.api.bkland.entity.Plot;
import com.api.bkland.repository.PlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlotService {
    @Autowired
    private PlotRepository repository;

    public Plot findByRealEstatePostId(String id) {
        return repository.findByRealEstatePostId(id).get();
    }
}
