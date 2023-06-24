package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.entity.response.IEnableUserChat;
import com.api.bkland.entity.response.IRepAdmin;
import com.api.bkland.entity.response.IRepEnableRequest;
import com.api.bkland.entity.response.IRepRequested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealEstatePostRepository extends JpaRepository<RealEstatePost, String> {
    @Modifying
    @Query(value = "update real_estate_post set enable=0 where datediff(now(), create_at) > period;", nativeQuery = true)
    void disablePostExpire();

    @Modifying
    @Query(value = "update real_estate_post set enable=0 where id = :id", nativeQuery = true)
    void disablePostById(String id);

    @Query(value = "select * from real_estate_post x where x.owner_id = :ownerId", nativeQuery = true)
    List<RealEstatePost> findByOwnerId(String ownerId);

    @Modifying
    @Query(value = "update real_estate_post set status = :status where id = :id ;", nativeQuery = true)
    void updateStatus(@PathVariable("status") String status, @PathVariable("id") String id);

    @Modifying
    @Query(value = "update real_estate_post rep set view = (rep.view + 1) where rep.id = :realEstatePostId", nativeQuery = true)
    void updateView(@Param("realEstatePostId") String realEstatePostId);

    @Modifying
    @Query(value = "update real_estate_post rep set clicked_view = (rep.clicked_view + 1) where rep.id = :realEstatePostId", nativeQuery = true)
    void updateClickedView(@Param("realEstatePostId") String realEstatePostId);

    Optional<RealEstatePost> findByIdAndEnable(String id, boolean enable);
    boolean existsByIdAndEnable(String id, boolean enable);

    @Query(value = "select rep.id, rep.type, rep.title, rep.district_code as districtCode, rep.is_sell as isSell, rep.price\n" +
            "from real_estate_post rep\n" +
            "where rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and rep.owner_id = :userId ;", nativeQuery = true)
    List<IRepEnableRequest> enableRequest(@Param("userId") String userId);

    @Query(value = "select rep.id, rep.type, rep.title, rep.is_sell as isSell, rep.price, repa.status,\n" +
            "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName, \n" +
            "u.phone_number as phoneNumber, repa.id as repaId\n" +
            "from real_estate_post rep inner join real_estate_post_agency repa \n" +
            "on repa.real_estate_post_id = rep.id inner join user u\n" +
            "on repa.agency_id = u.id\n" +
            "and rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and rep.owner_id = :userId", nativeQuery = true)
    List<IRepRequested> repRequested(@Param("userId") String userId);

    @Query(value = "select rep.id, rep.type, rep.title, rep.is_sell as isSell, rep.price, repa.status,\n" +
            "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName, \n" +
            "u.phone_number as phoneNumber, repa.id as repaId\n" +
            "from real_estate_post rep inner join real_estate_post_agency repa \n" +
            "on repa.real_estate_post_id = rep.id inner join user u\n" +
            "on rep.owner_id = u.id\n" +
            "and rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and repa.agency_id = :agencyId", nativeQuery = true)
    List<IRepRequested> requestedOfAgency(@Param("agencyId") String agencyId);

    @Query(value = "select u.id, u.phone_number as phoneNumber, u.avatar_url as avatarUrl,\n" +
            "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName\n" +
            "from user u inner join real_estate_post_agency repa\n" +
            "on u.id = repa.agency_id\n" +
            "where repa.status = 'DA_XAC_NHAN'\n" +
            "and repa.real_estate_post_id = :id", nativeQuery = true)
    Optional<IEnableUserChat> findContact(String id);

    @Query(value = "select u.id, u.phone_number as phoneNumber, u.avatar_url as avatarUrl,\n" +
            "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName\n" +
            "from user u inner join real_estate_post rep\n" +
            "on u.id = rep.owner_id\n" +
            "where rep.id = :id", nativeQuery = true)
    Optional<IEnableUserChat> findOwnerContact(String id);

    @Query(value = "select rep.id, rep.type, rep.is_sell as sell, rep.status, rep.enable, rep.price, rep.area, rep.create_at as createAt, " +
            "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName, u.phone_number as phoneNumber " +
            "from real_estate_post rep inner join user u on rep.owner_id = u.id " +
            "order by rep.create_at desc", nativeQuery = true)
    List<IRepAdmin> findAllByAdmin();

    @Query(value = "select rep.id\n" +
            "from real_estate_post rep left join interested i on rep.id = i.real_estate_post_id\n" +
            "where rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and datediff(now(), rep.create_at) <= rep.period\n" +
            "and rep.is_sell = :sell " +
            "and rep.type = :type " +
            "group by rep.id\n" +
            "order by rep.priority, count(i.real_estate_post_id) desc " +
            "limit :limit offset :offset" ,nativeQuery = true)
    List<String> getRepIdDetailPage(Byte sell, String type, Integer limit, Integer offset);

    @Query(value = "select count(*)\n" +
            "from real_estate_post rep\n" +
            "where rep.enable = 1\n" +
            "and rep.status = 'DA_KIEM_DUYET'\n" +
            "and rep.is_sell = :sell " +
            "and rep.type = :type " +
            "and datediff(now(), rep.create_at) <= rep.period", nativeQuery = true)
    Integer countTotalBySellAndTypeClient(Byte sell, String type);
}
