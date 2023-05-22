package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.DistrictDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AgencyRegisterRequest {
    @NotNull
    @NotBlank
    private String userId;
    @NotEmpty
    private List<DistrictDTO> districts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DistrictDTO> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDTO> districts) {
        this.districts = districts;
    }
}
