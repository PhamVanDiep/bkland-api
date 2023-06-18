package com.api.bkland.entity;

import com.api.bkland.constant.enumeric.ERepAgencyStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "real_estate_post_agency")
public class RealEstatePostAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "real_estate_post_id")
    @NotBlank
    @NotNull
    private String realEstatePostId;

    @Column(name = "agency_id")
    @NotNull
    @NotBlank
    private String agencyId;

    @Column(name = "status")
    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private ERepAgencyStatus status;

    @Column(name = "create_by", updatable = false)
    private String createBy;

    @Column(name = "create_at", updatable = false)
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

    public String getRealEstatePostId() {
        return realEstatePostId;
    }

    public void setRealEstatePostId(String realEstatePostId) {
        this.realEstatePostId = realEstatePostId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public ERepAgencyStatus getStatus() {
        return status;
    }

    public void setStatus(ERepAgencyStatus status) {
        this.status = status;
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
