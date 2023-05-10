package com.api.bkland.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post_media")
public class PostMedia {
    @Id
    @Column(name = "id")
    @NotNull
    @NotBlank
    private String id;

    @Column(name = "media_type")
    @NotNull
    @NotBlank
    private String mediaType;

    @Column(name = "post_id")
    @NotNull
    @NotBlank
    private String postId;

    @Column(name = "post_type")
    @NotNull
    @NotBlank
    private String postType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }
}
