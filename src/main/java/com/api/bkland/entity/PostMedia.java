package com.api.bkland.entity;

import com.api.bkland.constant.enumeric.EPostType;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    private EPostType postType;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

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

    public EPostType getPostType() {
        return postType;
    }

    public void setPostType(EPostType postType) {
        this.postType = postType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
