package com.api.bkland.service;

import com.api.bkland.constant.enumeric.ERepAgencyStatus;
import com.api.bkland.entity.RealEstatePostAgency;
import com.api.bkland.repository.RealEstatePostAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RealEstatePostAgencyService {
    @Autowired
    private RealEstatePostAgencyRepository repository;

    @Transactional
    public RealEstatePostAgency save(RealEstatePostAgency realEstatePostAgency) {
        return repository.save(realEstatePostAgency);
    }

    @Transactional
    public void saveAll(List<RealEstatePostAgency> realEstatePostAgencies) {
        repository.saveAll(realEstatePostAgencies);
    }

    @Transactional
    public Long updateStatus(RealEstatePostAgency realEstatePostAgency) {
        RealEstatePostAgency realEstatePostAgency1 = repository.save(realEstatePostAgency);
        if (realEstatePostAgency1.getStatus().equals(ERepAgencyStatus.DA_XAC_NHAN)) {
            repository.updateRep(realEstatePostAgency1.getRealEstatePostId());
        }
        return realEstatePostAgency1.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<RealEstatePostAgency> findByAgencyId(String agencyId) {
        return repository.findByAgencyId(agencyId);
    }

    public List<RealEstatePostAgency> findByCreateBy(String userId) {
        return repository.findByCreateBy(userId);
    }

    public boolean inArea(String repId, String agencyId) {
        Integer integer = repository.checkInArea(repId, agencyId);
        if (integer > 0) {
            return true;
        }
        return false;
    }

    public Optional<RealEstatePostAgency> findById(Long id) {
        return repository.findById(id);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
