package com.api.bkland.repository;

import com.api.bkland.entity.District;
import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.entity.response.IAgencyRep;
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

    @Query(value = "select distinct concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName, \n" +
            "u.phone_number as phoneNumber, u.id\n" +
            "from agency_district ad, special_account sa, user u, real_estate_post rep\n" +
            "where rep.district_code = ad.district_code\n" +
            "and ad.enable = 1 \n" +
            "and ad.user_id = sa.user_id\n" +
            "and sa.is_agency = 1\n" +
            "and sa.user_id = u.id \n" +
            "and rep.id = :repId \n" +
            "and u.id not in (select agency_id from real_estate_post_agency where real_estate_post_id = :repId )", nativeQuery = true)
    List<IAgencyRep> listAgencyByRepDistrict(@Param("repId") String repId);
}
