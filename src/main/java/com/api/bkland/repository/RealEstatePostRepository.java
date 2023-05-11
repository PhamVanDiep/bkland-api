package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstatePostRepository extends JpaRepository<RealEstatePost, String> {
    @Modifying
    @Query(value = "update real_estate_post set enable=0 where datediff(now(), create_at) > period;", nativeQuery = true)
    void disablePostExpire();
}
