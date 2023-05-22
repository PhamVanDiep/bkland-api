package com.api.bkland.service;

import com.api.bkland.entity.InfoType;
import com.api.bkland.repository.InfoTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoTypeService {
    @Autowired
    private InfoTypeRepository repository;

    public InfoType createInfoType(InfoType infoType) {
        return repository.save(infoType);
    }

    public InfoType updateInfoType(InfoType infoType) {
        return repository.save(infoType);
    }

    public List<InfoType> getAllSkip5() {
        return repository.findAll()
                .stream()
                .filter(e -> e.getId() > 5)
                .collect(Collectors.toList());
    }

    public List<InfoType> getAll() {
        return repository.findAll();
    }
}
