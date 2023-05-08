package com.api.bkland.payload.dto.post;

import com.api.bkland.payload.dto.RealEstatePostDTO;


public class PlotDTO extends BasePost{
    private Long id;
    private RealEstatePostDTO realEstatePost;

    private Double frontWidth;

    private Double behindWidth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealEstatePostDTO getRealEstatePost() {
        return realEstatePost;
    }

    public void setRealEstatePost(RealEstatePostDTO realEstatePost) {
        this.realEstatePost = realEstatePost;
    }

    public Double getFrontWidth() {
        return frontWidth;
    }

    public void setFrontWidth(Double frontWidth) {
        this.frontWidth = frontWidth;
    }

    public Double getBehindWidth() {
        return behindWidth;
    }

    public void setBehindWidth(Double behindWidth) {
        this.behindWidth = behindWidth;
    }
}
