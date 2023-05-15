package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstatePostRepository extends JpaRepository<RealEstatePost, String> {
    @Modifying
    @Query(value = "update real_estate_post set enable=0 where datediff(now(), create_at) > period;", nativeQuery = true)
    void disablePostExpire();

    @Query(value = "select * from real_estate_post x where x.owner_id = :ownerId", nativeQuery = true)
    List<RealEstatePost> findByOwnerId(String ownerId);
}
