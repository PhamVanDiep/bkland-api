package com.api.bkland.payload.dto;

import java.time.Instant;
import java.util.List;

public class ForumPostDTO {
    private String id;
    private String content;
    private String createBy;
    private Instant createAt;
    private String updateBy;
    private Instant updateAt;
    private List<PostMediaDTO> postMedia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public List<PostMediaDTO> getPostMedia() {
        return postMedia;
    }

    public void setPostMedia(List<PostMediaDTO> postMedia) {
        this.postMedia = postMedia;
    }
}
