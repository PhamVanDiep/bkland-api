package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.DistrictDTO;
import com.api.bkland.payload.dto.SpecialAccountDTO;

import java.util.List;

public class AgencyInfoResponse {
    private SpecialAccountDTO specialAccount;
    private List<DistrictDTO> districts;

    public SpecialAccountDTO getSpecialAccount() {
        return specialAccount;
    }

    public void setSpecialAccount(SpecialAccountDTO specialAccount) {
        this.specialAccount = specialAccount;
    }

    public List<DistrictDTO> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDTO> districts) {
        this.districts = districts;
    }
}
