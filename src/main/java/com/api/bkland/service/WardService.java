package com.api.bkland.service;

import com.api.bkland.entity.Ward;
import com.api.bkland.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardService {
    @Autowired
    private WardRepository wardRepository;

    public List<Ward> getAll() {
        return wardRepository.findAll();
    }

    public Ward findByCode(String code) {
        Optional<Ward> repoReturn = wardRepository.findByCode(code);
        if (repoReturn.isEmpty()) return null;
        return repoReturn.get();
    }
}
