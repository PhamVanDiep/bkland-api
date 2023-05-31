package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.DistrictDTO;

import java.util.List;

public class PriceFluctuationRequest {
    private String userId;
    private List<String> districts;
    private Long districtPrice;
    private boolean enable;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getDistricts() {
        return districts;
    }

    public void setDistricts(List<String> districts) {
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
}
