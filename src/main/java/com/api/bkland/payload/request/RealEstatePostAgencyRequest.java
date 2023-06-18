package com.api.bkland.payload.request;

import java.util.List;

public class RealEstatePostAgencyRequest {
    private String realEstatePostId;
    private List<String> agencies;

    public String getRealEstatePostId() {
        return realEstatePostId;
    }

    public void setRealEstatePostId(String realEstatePostId) {
        this.realEstatePostId = realEstatePostId;
    }

    public List<String> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<String> agencies) {
        this.agencies = agencies;
    }
}
