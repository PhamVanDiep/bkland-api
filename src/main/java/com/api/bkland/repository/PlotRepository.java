package com.api.bkland.repository;

import com.api.bkland.entity.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    Optional<Plot> findByRealEstatePostId(String realEstatePostId);
    void deleteByRealEstatePostId(String realEstatePostId);
}
