package com.api.bkland.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "clicked_info_view")
public class ClickedInfoView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "real_estate_post_id")
    @NotNull
    @NotBlank
    private String realEstatePostId;

    @Column(name = "create_by")
    @NotNull
    @NotBlank
    private String createBy;

    @Column(name = "create_at")
    @NotNull
    @NotBlank
    private Instant createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealEstatePostId() {
        return realEstatePostId;
    }

    public void setRealEstatePostId(String realEstatePostId) {
        this.realEstatePostId = realEstatePostId;
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
