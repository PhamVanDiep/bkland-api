package com.api.bkland.repository;

import com.api.bkland.entity.ForumPostLike;
import com.api.bkland.entity.id.ForumPostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostLikeRepository extends JpaRepository<ForumPostLike, ForumPostLikeId> {
    boolean existsByForumPostIdAndUserId(String forumPostId, String userId);

    void deleteByForumPostIdAndUserId(String forumPostId, String userId);
    long countByForumPostId(String postId);
}
