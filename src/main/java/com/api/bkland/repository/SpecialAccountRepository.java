package com.api.bkland.repository;

import com.api.bkland.entity.District;
import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.entity.response.IDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface SpecialAccountRepository extends JpaRepository<SpecialAccount, String> {
    Optional<SpecialAccount> findByUserId(String userId);
    void deleteByUserId(String userId);

    @Query(value = "select d.* from districts d, agency_district ad " +
            "where d.code = ad.district_code and ad.user_id = :userId ;",
            nativeQuery = true)
    List<IDistrict> findAllDistrictsAgency(@Param("userId") String userId);

    @Modifying
    @Query(value = "delete from agency_district where user_id=:userId", nativeQuery = true)
    void agencyDistrictDeleteByUserId(@Param("userId") String userId);

    @Modifying
    @Query(value = "delete from user_role where user_id=:userId and role_id=2", nativeQuery = true)
    void userRoleDeleteByUserId(@Param("userId") String userId);

    @Query(value = "select district_code from agency_district where user_id = :userId", nativeQuery = true)
    List<String> getAllDistrictCodeOfAgency(@Param("userId") String userId);
}
