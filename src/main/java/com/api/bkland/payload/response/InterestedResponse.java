package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.InterestedDTO;
import com.api.bkland.payload.dto.post.RealEstatePostDTO;

public class InterestedResponse extends InterestedDTO {
    private RealEstatePostDTO realEstatePost;

    public RealEstatePostDTO getRealEstatePost() {
        return realEstatePost;
    }

    public void setRealEstatePost(RealEstatePostDTO realEstatePost) {
        this.realEstatePost = realEstatePost;
    }
}
