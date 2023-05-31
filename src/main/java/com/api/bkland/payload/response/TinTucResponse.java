package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.InfoTypeDTO;

import java.util.List;

public class TinTucResponse {
    private InfoTypeDTO infoType;
    private List<InfoPostResponse> infoPosts;

    public InfoTypeDTO getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoTypeDTO infoType) {
        this.infoType = infoType;
    }

    public List<InfoPostResponse> getInfoPosts() {
        return infoPosts;
    }

    public void setInfoPosts(List<InfoPostResponse> infoPosts) {
        this.infoPosts = infoPosts;
    }
}
