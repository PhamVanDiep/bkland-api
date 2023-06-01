package com.api.bkland.repository;

import com.api.bkland.entity.PriceFluctuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceFluctuationRepository extends JpaRepository<PriceFluctuation, Long> {
    List<PriceFluctuation> findByUserId(String userId);
    void deleteByUserId(String userId);
}
