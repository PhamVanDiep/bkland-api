package com.api.bkland.service;

import com.api.bkland.entity.AdministrativeUnit;
import com.api.bkland.repository.AdministrativeUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrativeUnitService {

    @Autowired
    private AdministrativeUnitRepository administrativeUnitRepository;

    public List<AdministrativeUnit> getAll() {
        return administrativeUnitRepository.findAll();
    }
}
