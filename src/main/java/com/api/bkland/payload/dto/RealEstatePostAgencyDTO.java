package com.api.bkland.payload.dto;

import com.api.bkland.constant.enumeric.ERepAgencyStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class RealEstatePostAgencyDTO {
    @NotBlank
    @NotNull
    private Long id;
    @NotBlank
    @NotNull
    private String realEstatePostId;
    @NotBlank
    @NotNull
    private String agencyId;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private ERepAgencyStatus status;
    private String createBy;
    private Instant createAt;
    private String updateBy;
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
