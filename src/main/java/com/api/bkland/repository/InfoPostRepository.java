package com.api.bkland.repository;

import com.api.bkland.entity.InfoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoPostRepository extends JpaRepository<InfoPost, Long> {
    List<InfoPost> findByCreateByOrderByCreateAtDesc(String createBy);

    @Modifying
    @Query(value = "delete from info_post where info_type_id = :infoTypeId", nativeQuery = true)
    void deleteAllInfoPostByInfoTypeId(@Param("infoTypeId") Integer infoTypeId);
}
