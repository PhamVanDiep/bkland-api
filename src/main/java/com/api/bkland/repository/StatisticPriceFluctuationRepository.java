package com.api.bkland.repository;

import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.StatisticPriceFluctuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticPriceFluctuationRepository extends JpaRepository<StatisticPriceFluctuation, Long> {
}
