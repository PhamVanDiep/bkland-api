package com.api.bkland.repository;

import com.api.bkland.entity.UserDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceToken, Integer> {
    Optional<UserDeviceToken> findByUserIdAndDeviceInfo(String userId, String deviceInfo);

    @Query(value = "select distinct udt.notify_token from user_device_token udt, price_fluctuation pf " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and pf.district_code = :districtCode " +
            "and pf.enable = 1 " +
            "and udt.user_id = pf.user_id " +
            "and udt.user_id != 'admin'", nativeQuery = true)
    List<String> getTokensByDistrict(@Param("districtCode") String districtCode);

    @Query(value = "select distinct udt.notify_token " +
            "from user_device_token udt, agency_district ad " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and ad.district_code = :districtCode " +
            "and ad.enable = 1 " +
            "and udt.user_id = ad.user_id "+
            "and udt.user_id != 'admin'", nativeQuery = true)
    List<String> notifyAgencyREPUpdate (@Param("districtCode") String districtCode);

    @Query(value = "select distinct udt.notify_token " +
            "from user_device_token udt " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and udt.user_id = :userId "+
            "and udt.user_id != 'admin'", nativeQuery = true)
    List<String> notifyAcceptRejectREP(@Param("userId") String userId);

    @Query(value = "select distinct udt.notify_token " +
            "from user_device_token udt, interested i  " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and udt.user_id = i.user_id " +
            "and i.real_estate_post_id = :repId " +
            "and udt.user_id != 'admin'", nativeQuery = true)
    List<String> notifyInterested(@Param("repId") String repId);

    @Query(value = "select distinct udt.notify_token " +
            "from user_device_token udt " +
            "where udt.enable = 1 " +
            "and udt.is_logout = 0 " +
            "and length(udt.notify_token) > 0 " +
            "and udt.user_id = 'admin'", nativeQuery = true)
    List<String> getAllAdminToken();
}
