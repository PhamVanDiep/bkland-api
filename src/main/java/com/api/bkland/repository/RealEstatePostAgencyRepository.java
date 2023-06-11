package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePostAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstatePostAgencyRepository extends JpaRepository<RealEstatePostAgency, Long> {
    List<RealEstatePostAgency> findByAgencyId(String agencyId);
    List<RealEstatePostAgency> findByCreateBy(String createBy);

    @Query(value = "select count(*)\n" +
            "from real_estate_post rep, agency_district ad, real_estate_post_agency repa\n" +
            "where rep.id = repa.real_estate_post_id\n" +
            "and rep.district_code = ad.district_code\n" +
            "and repa.agency_id = ad.user_id\n" +
            "and rep.enable = 1\n" +
            "and rep.id = :repId\n" +
            "and ad.user_id = :agencyId", nativeQuery = true)
    Integer checkInArea(@Param("repId") String repId, @Param("agencyId") String agencyId);

    @Modifying
    @Query(value = "update real_estate_post set priority = 4, period = 365 where id = :repId", nativeQuery = true)
    void updateRep(@Param("repId") String repId);
}
