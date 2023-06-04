package com.api.bkland.repository;

import com.api.bkland.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    long countByPostId(String postId);
}
