package com.api.bkland.repository;

import com.api.bkland.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {
    Optional<District> findByCode(String code);
    List<District> findByProvinceCode(String provinceCode);
}
