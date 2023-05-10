package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.PostMediaDTO;
import com.api.bkland.payload.dto.post.BasePost;

import java.util.List;

public class RealEstatePostResponse {
    private BasePost basePost;
    private List<PostMediaDTO> images;

    public RealEstatePostResponse(BasePost basePost, List<PostMediaDTO> postMediaDTOS) {
        this.basePost = basePost;
        this.images = postMediaDTOS;
    }

    public BasePost getBasePost() {
        return basePost;
    }

    public void setBasePost(BasePost basePost) {
        this.basePost = basePost;
    }

    public List<PostMediaDTO> getPostMediaDTOS() {
        return images;
    }

    public void setPostMediaDTOS(List<PostMediaDTO> postMediaDTOS) {
        this.images = postMediaDTOS;
    }
}
