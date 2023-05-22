package com.api.bkland.repository;

import com.api.bkland.entity.SpecialAccountPay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialAccountPayRepository extends JpaRepository<SpecialAccountPay, Long> {
    List<SpecialAccountPay> findByUserIdOrderByCreateAtDesc(String userId);
}
