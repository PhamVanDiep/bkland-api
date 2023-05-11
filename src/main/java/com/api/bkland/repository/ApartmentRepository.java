package com.api.bkland.repository;

import com.api.bkland.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    Optional<Apartment> findByRealEstatePostId(String realEstatePostId);
    void deleteByRealEstatePostId(String realEstatePostId);
}
