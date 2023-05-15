package com.api.bkland.service;

import com.api.bkland.entity.District;
import com.api.bkland.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository repository;

    public District findByCode(String code) {
        Optional<District> repoReturn = repository.findByCode(code);
        if (repoReturn.isEmpty()) {
            return null;
        }
        return repoReturn.get();
    }

    public List<District> findByProvinceCode(String provinceCode) {
        return repository.findByProvinceCode(provinceCode);
    }
}
