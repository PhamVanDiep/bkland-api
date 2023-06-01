package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.InfoPostDTO;

public class InfoPostRequest {
    private InfoPostDTO infoPost;
    private String districtCode;

    public InfoPostDTO getInfoPost() {
        return infoPost;
    }

    public void setInfoPost(InfoPostDTO infoPost) {
        this.infoPost = infoPost;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
