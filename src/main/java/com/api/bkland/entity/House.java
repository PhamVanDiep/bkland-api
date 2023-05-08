package com.api.bkland.entity;

import com.api.bkland.constant.enumeric.EDirection;

import javax.persistence.*;

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "real_estate_post_id")
    private RealEstatePost realEstatePost;

    @Column(name = "no_floor")
    private Integer noFloor;

    @Column(name = "no_bedroom")
    private Integer noBedroom;

    @Column(name = "no_bathroom")
    private Integer noBathroom;

    @Column(name = "furniture")
    private String furniture;

    @Column(name = "balcony_direction")
    private EDirection balconyDirection;

    @Column(name = "front_width")
    private Double frontWidth;

    @Column(name = "behind_width")
    private Double behindWidth;

    @Column(name = "street_width")
    private Double streetWidth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealEstatePost getRealEstatePost() {
        return realEstatePost;
    }

    public void setRealEstatePost(RealEstatePost realEstatePost) {
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
