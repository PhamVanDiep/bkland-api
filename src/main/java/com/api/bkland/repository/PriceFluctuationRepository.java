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

    @Query(value = "select udt.notify_token from user_device_token udt, price_fluctuation pf " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and pf.district_code = :districtCode " +
            "and pf.enable = 1 " +
            "and udt.user_id = pf.user_id", nativeQuery = true)
    List<String> getTokensByDistrict(@Param("districtCode") String districtCode);
}
