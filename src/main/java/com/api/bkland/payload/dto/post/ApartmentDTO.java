package com.api.bkland.payload.dto.post;

import com.api.bkland.constant.enumeric.EDirection;
import com.api.bkland.payload.dto.RealEstatePostDTO;

public class ApartmentDTO extends BasePost{
    private Long id;

    private RealEstatePostDTO realEstatePost;

    private Integer floorNo;

    private Integer noBedroom;

    private Integer noBathroom;

    private String furniture;

    private EDirection balconyDirection;

    private String construction;

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

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
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

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }
}
