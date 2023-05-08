package com.api.bkland.repository;

import com.api.bkland.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByRealEstatePostId(String realEstatePostId);
}
