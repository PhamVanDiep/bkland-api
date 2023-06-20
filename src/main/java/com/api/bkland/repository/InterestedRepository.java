package com.api.bkland.repository;

import com.api.bkland.entity.Interested;
import com.api.bkland.entity.response.IPostInterested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterestedRepository extends JpaRepository<Interested, Long> {
    @Query(value = "select rep.id, rep.type, rep.title, rep.price, rep.area, rep.is_sell as isSell, rep.address_show as addressShow\n" +
            "from real_estate_post rep inner join interested i \n" +
            "on i.real_estate_post_id = rep.id\n" +
            "where rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and i.user_id = :userId", nativeQuery = true)
    List<IPostInterested> findRepDetailByUserId(String userId);
    @Query(value = "select rep.id, rep.type, rep.title, rep.price, rep.area, rep.is_sell as isSell, rep.address_show as addressShow\n" +
            "from real_estate_post rep inner join interested i \n" +
            "on i.real_estate_post_id = rep.id\n" +
            "where rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and i.user_id = 'anonymous'\n" +
            "and i.device_info = :deviceInfo", nativeQuery = true)
    List<IPostInterested> findRepDetailByDeviceInfo(String deviceInfo);
    boolean existsByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId);
    boolean existsByUserIdAndRealEstatePostId(String userId, String realEstatePostId);
    Optional<Interested> findByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId);
    Optional<Interested> findByUserIdAndRealEstatePostId(String userId, String realEstatePostId);
    Integer countByUserIdAndDeviceInfo(String userId, String deviceInfo);
    Integer countByUserId(String userId);
}
