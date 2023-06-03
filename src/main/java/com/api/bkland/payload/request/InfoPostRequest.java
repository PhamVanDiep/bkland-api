package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.InfoPostDTO;

import java.util.List;

public class InfoPostRequest {
    private InfoPostDTO infoPost;
    private List<String> districtCodes;

    public InfoPostDTO getInfoPost() {
        return infoPost;
    }

    public void setInfoPost(InfoPostDTO infoPost) {
        this.infoPost = infoPost;
    }

    public List<String> getDistrictCodes() {
        return districtCodes;
    }

    public void setDistrictCodes(List<String> districtCodes) {
        this.districtCodes = districtCodes;
    }
}
