package com.api.bkland.entity;

import com.api.bkland.constant.enumeric.EProjectType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "content")
    @NotNull
    @NotBlank
    private String content;

    @OneToOne
    @JoinColumn(name = "province_code", updatable = false)
    @NotNull
    @NotBlank
    private Province province;

    @OneToOne
    @JoinColumn(name = "district_code", updatable = false)
    @NotNull
    @NotBlank
    private District district;

    @OneToOne
    @JoinColumn(name = "ward_code", updatable = false)
    @NotNull
    @NotBlank
    private Ward ward;

    @Column(name = "area")
    private Double area;

    @Column(name = "type")
    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private EProjectType type;

    @Column(name = "enable")
    @NotNull
    @NotBlank
    private boolean enable;

    @Column(name = "phone_number")
    @NotNull
    @NotBlank
    private String phoneNumber;

    @Column(name = "email")
    @NotNull
    @NotBlank
    private String email;

    @Column(name = "image_url")
    @NotNull
    @NotBlank
    private String imageUrl;

    @Column(name = "address")
    @NotNull
    @NotBlank
    private String address;

    @Column(name = "create_by", updatable = false)
    private String createBy;

    @Column(name = "create_at", updatable = false)
    private Instant createAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Instant updateAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectParam> projectParams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public EProjectType getType() {
        return type;
    }

    public void setType(EProjectType type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
    public List<ProjectParam> getProjectParams() {
        return projectParams;
    }

    public void setProjectParams(List<ProjectParam> projectParams) {
        this.projectParams = projectParams;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
