package com.api.bkland.repository;

import com.api.bkland.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {
    List<PostMedia> findByPostId(String postId);

    @Modifying
    void deleteByPostId(String postId);
}
