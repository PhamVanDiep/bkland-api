package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.RealEstatePostAgencyDTO;
import com.api.bkland.payload.dto.post.RealEstatePostDTO;

public class REPAgencyResponse extends RealEstatePostAgencyDTO {
    private RealEstatePostDTO realEstatePostDTO;

    public RealEstatePostDTO getRealEstatePostDTO() {
        return realEstatePostDTO;
    }

    public void setRealEstatePostDTO(RealEstatePostDTO realEstatePostDTO) {
        this.realEstatePostDTO = realEstatePostDTO;
    }
}
