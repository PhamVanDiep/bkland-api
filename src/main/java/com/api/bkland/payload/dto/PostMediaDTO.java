package com.api.bkland.payload.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostMediaDTO {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String mediaType;

    @NotNull
    @NotBlank
    private String postId;

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
