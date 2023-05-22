package com.api.bkland.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "info_post")
public class InfoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "info_type_id")
    private InfoType infoType;

    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @NotBlank
    @Column(name = "content")
    private String content;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Instant updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoType infoType) {
        this.infoType = infoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
