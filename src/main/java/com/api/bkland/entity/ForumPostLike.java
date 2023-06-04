package com.api.bkland.entity;

import com.api.bkland.entity.id.ForumPostLikeId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "forum_post_like")
@IdClass(ForumPostLikeId.class)
public class ForumPostLike {
    @Id
    @Column(name = "forum_post_id")
    private String forumPostId;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private Instant createAt;

    public String getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(String forumPostId) {
        this.forumPostId = forumPostId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
