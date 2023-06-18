package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
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
}
