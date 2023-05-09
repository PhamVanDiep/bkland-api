package com.api.bkland.payload.dto.post;

import com.api.bkland.constant.enumeric.EDirection;
import com.api.bkland.constant.enumeric.EStatus;
import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.User;
import com.api.bkland.payload.dto.DistrictDTO;
import com.api.bkland.payload.dto.ProvinceDTO;
import com.api.bkland.payload.dto.WardDTO;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class RealEstatePostDTO {

    @NotNull
    private String id;

    @NotNull
    private EType type;

    @NotNull
    private User ownerId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String addressShow;

    @NotNull
    private Double area;

    @NotNull
    private Double price;

    @NotNull
    private ProvinceDTO province;

    @NotNull
    private DistrictDTO district;

    @NotNull
    private WardDTO ward;

    @NotNull
    private EStatus status;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private boolean enable;

    @NotNull
    private Integer priority;

    @NotNull
    private Integer period;

    @NotNull
    private EDirection direction;

    @NotNull
    private boolean sell;

    private String createBy;

    private Instant createAt;

    private String updateBy;

    private Instant updateAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressShow() {
        return addressShow;
    }

    public void setAddressShow(String addressShow) {
        this.addressShow = addressShow;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public WardDTO getWard() {
        return ward;
    }

    public void setWard(WardDTO ward) {
        this.ward = ward;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public EDirection getDirection() {
        return direction;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }
}
