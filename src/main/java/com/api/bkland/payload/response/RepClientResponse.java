package com.api.bkland.payload.response;

import java.time.Instant;

public class RepClientResponse {
    private String id;
    private String title;
    private Double price;
    private Double area;
    private Byte sell;
    private String addressShow;
    private Instant createAt;
    private String imageUrl;

    public RepClientResponse() {
    }

    public RepClientResponse(String id, String title, Double price, Double area, Byte sell, String addressShow, Instant createAt, String imageUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.area = area;
        this.sell = sell;
        this.addressShow = addressShow;
        this.createAt = createAt;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Byte getSell() {
        return sell;
    }

    public void setSell(Byte sell) {
        this.sell = sell;
    }

    public String getAddressShow() {
        return addressShow;
    }

    public void setAddressShow(String addressShow) {
        this.addressShow = addressShow;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
