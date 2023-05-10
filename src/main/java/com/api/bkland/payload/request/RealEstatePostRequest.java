package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.PostMediaDTO;
import com.api.bkland.payload.dto.post.*;

import java.util.List;

public class RealEstatePostRequest {
    private RealEstatePostDTO realEstatePost;
    private List<PostMediaDTO> images;
    private PlotDTO plot;
    private ApartmentDTO apartment;
    private HouseDTO house;

    public RealEstatePostDTO getRealEstatePost() {
        return realEstatePost;
    }

    public void setRealEstatePost(RealEstatePostDTO realEstatePost) {
        this.realEstatePost = realEstatePost;
    }

    public List<PostMediaDTO> getImages() {
        return images;
    }

    public void setImages(List<PostMediaDTO> images) {
        this.images = images;
    }

    public PlotDTO getPlot() {
        return plot;
    }

    public void setPlot(PlotDTO plot) {
        this.plot = plot;
    }

    public ApartmentDTO getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }
}
