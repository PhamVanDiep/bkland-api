package com.api.bkland.repository;

import com.api.bkland.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {
    List<PostMedia> findByPostId(String postId);

    @Modifying
    void deleteByPostId(String postId);

    @Query(value = "select id from post_media where post_id = :postId limit 1", nativeQuery = true)
    Optional<String> getOneImageOfPost(String postId);
}
