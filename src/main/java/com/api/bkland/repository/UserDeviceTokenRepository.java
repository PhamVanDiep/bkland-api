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

    // Thong bao cho tai khoan dac biet khi den han dong tien
    @Query(value = "select udt.notify_token\n" +
            "from special_account sa inner join user_device_token udt\n" +
            "on udt.user_id = sa.user_id\n" +
            "where udt.notify_token is not null \n" +
            "and length(udt.notify_token) > 0\n" +
            "and udt.enable = 1\n" +
            "and udt.is_logout = 0\n" +
            "and sa.last_paid is not null\n" +
            "and datediff(sa.last_paid, now()) between (30 - sa.notify_before) and 30;", nativeQuery = true)
    List<String> thongBaoDenHanDongTien();

    // Thong bao cho tai khoan doanh nghiep khi tao tai khoan nhung chua dong phi
    @Query(value = "select udt.notify_token\n" +
            "from special_account sa inner join user_device_token udt\n" +
            "on udt.user_id = sa.user_id inner join user u\n" +
            "on sa.user_id = u.id \n" +
            "where udt.notify_token is not null \n" +
            "and length(udt.notify_token) > 0\n" +
            "and udt.enable = 1\n" +
            "and udt.is_logout = 0\n" +
            "and sa.last_paid is null\n" +
            "and sa.is_agency = 0\n" +
            "and datediff(sa.last_paid, u.create_at) between 0 and sa.notify_before;", nativeQuery = true)
    List<String> thongBaoChuaDongTien();

    // Gui thong bao cho chu bai dang khi duoc chap nhan / tu choi bai viet tu moi gioi
    @Query(value = "select udt.notify_token\n" +
            "from user_device_token udt inner join real_estate_post rep\n" +
            "on rep.owner_id = udt.user_id inner join real_estate_post_agency repa\n" +
            "on repa.real_estate_post_id = rep.id \n" +
            "where udt.notify_token is not null \n" +
            "and length(udt.notify_token) > 0\n" +
            "and udt.enable = 1\n" +
            "and udt.is_logout = 0 \n" +
            "and rep.id = :realEstatePostId", nativeQuery = true)
    List<String> thongBaoCapNhatTrangThaiBaiDang(@Param("realEstatePostId") String realEstatePostId);

    // Gui thong bao cho moi gioi khi co bai dang nho giup do
//    @Query(value = "select udt.notify_token\n" +
//            "from user_device_token udt inner join real_estate_post_agency repa\n" +
//            "on repa.agency_id = udt.user_id \n" +
//            "where udt.notify_token is not null \n" +
//            "and length(udt.notify_token) > 0\n" +
//            "and udt.enable = 1\n" +
//            "and udt.is_logout = 0 \n" +
//            "and repa.real_estate_post_id = :realEstatePostId", nativeQuery = true)
    @Query(value = "select udt.notify_token from user_device_token udt\n" +
            "where udt.notify_token is not null \n" +
            "and length(udt.notify_token) > 0\n" +
            "and udt.enable = 1\n" +
            "and udt.is_logout = 0 \n" +
            "and udt.user_id = :agencyId", nativeQuery = true)
    List<String> thongBaoCoBaiDangNhoGiup(@Param("agencyId") String agencyId);
}
