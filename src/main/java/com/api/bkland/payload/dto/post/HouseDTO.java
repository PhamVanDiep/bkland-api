package com.api.bkland.payload.dto.post;

import com.api.bkland.constant.enumeric.EDirection;
import com.api.bkland.payload.dto.RealEstatePostDTO;


public class HouseDTO extends BasePost {
    private Long id;

    private RealEstatePostDTO realEstatePost;

    private Integer noFloor;

    private Integer noBedroom;

    private Integer noBathroom;

    private String furniture;

    private EDirection balconyDirection;

    private Double frontWidth;

    private Double behindWidth;

    private Double streetWidth;

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

    public Integer getNoFloor() {
        return noFloor;
    }

    public void setNoFloor(Integer noFloor) {
        this.noFloor = noFloor;
    }

    public Integer getNoBedroom() {
        return noBedroom;
    }

    public void setNoBedroom(Integer noBedroom) {
        this.noBedroom = noBedroom;
    }

    public Integer getNoBathroom() {
        return noBathroom;
    }

    public void setNoBathroom(Integer noBathroom) {
        this.noBathroom = noBathroom;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public EDirection getBalconyDirection() {
        return balconyDirection;
    }

    public void setBalconyDirection(EDirection balconyDirection) {
        this.balconyDirection = balconyDirection;
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

    public Double getStreetWidth() {
        return streetWidth;
    }

    public void setStreetWidth(Double streetWidth) {
        this.streetWidth = streetWidth;
    }
}
