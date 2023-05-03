package com.api.bkland.service;

import com.api.bkland.entity.Province;
import com.api.bkland.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository repository;

    public List<Province> getAll() {
        return repository.findAll();
    }
}
