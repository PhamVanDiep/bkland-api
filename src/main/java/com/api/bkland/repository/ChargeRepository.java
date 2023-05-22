package com.api.bkland.repository;

import com.api.bkland.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge,Long> {
    List<Charge> findByUserIdOrderByCreateAtDesc(String userId);
}
