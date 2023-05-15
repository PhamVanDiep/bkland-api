package com.api.bkland.repository;

import com.api.bkland.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    Optional<Ward> findByCode(String code);
    List<Ward> findByDistrictCode(String districtCode);
}
