package com.api.bkland.entity;

import javax.persistence.*;

@Entity
@Table(name = "plot")
public class Plot {
    @Id
    @JoinColumn(name = "real_estate_post_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private RealEstatePost realEstatePost;

    @Column(name = "front_width")
    private Double frontWidth;

    @Column(name = "behind_width")
    private Double behindWidth;

    public RealEstatePost getRealEstatePost() {
        return realEstatePost;
    }

    public void setRealEstatePost(RealEstatePost realEstatePost) {
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
