package com.api.bkland.payload;

import com.api.bkland.payload.dto.ReportTypeDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

public class PostReportDTO {
    @NotBlank
    @NotNull
    private Long id;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private String postId;
    @NotBlank
    @NotNull
    private boolean isForumPost;
    private String createBy;
    private Instant createAt;
    private Set<ReportTypeDTO> reportTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isForumPost() {
        return isForumPost;
    }

    public void setForumPost(boolean forumPost) {
        isForumPost = forumPost;
    }

    public Set<ReportTypeDTO> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(Set<ReportTypeDTO> reportTypes) {
        this.reportTypes = reportTypes;
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
