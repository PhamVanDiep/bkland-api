package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
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
}
