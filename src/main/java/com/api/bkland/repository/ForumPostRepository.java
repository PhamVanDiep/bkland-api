package com.api.bkland.repository;

import com.api.bkland.entity.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, String> {
    List<ForumPost> findByCreateByNot(String createBy);
    Page<ForumPost> findByCreateBy(String createBy, Pageable pageable);
}
