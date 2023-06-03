package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.DistrictDTO;
import com.api.bkland.payload.dto.UserDTO;

import java.time.Instant;
import java.util.List;

public class PriceFluctuationResponse {
    private UserDTO user;
    private List<DistrictDTO> districts;
    private Long districtPrice;
    private boolean enable;
    private String createBy;
    private String updateBy;
    private Instant createAt;
    private Instant updateAt;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<DistrictDTO> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDTO> districts) {
        this.districts = districts;
    }

    public Long getDistrictPrice() {
        return districtPrice;
    }

    public void setDistrictPrice(Long districtPrice) {
        this.districtPrice = districtPrice;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }
}
