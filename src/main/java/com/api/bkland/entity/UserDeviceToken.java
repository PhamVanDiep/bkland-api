package com.api.bkland.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_device_token")
public class UserDeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "notify_token")
    private String notifyToken;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "is_logout")
    private boolean logout;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Instant updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNotifyToken() {
        return notifyToken;
    }

    public void setNotifyToken(String notifyToken) {
        this.notifyToken = notifyToken;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
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
