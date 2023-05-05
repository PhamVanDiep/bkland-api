package com.api.bkland.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "special_account")
public class SpecialAccount {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "monthly_charge")
    private Integer monthlyCharge;

    @Column(name = "is_agency")
    private boolean agency;

    @Column(name = "last_paid")
    private Instant lastPaid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(Integer monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public boolean isAgency() {
        return agency;
    }

    public void setAgency(boolean agency) {
        this.agency = agency;
    }

    public Instant getLastPaid() {
        return lastPaid;
    }

    public void setLastPaid(Instant lastPaid) {
        this.lastPaid = lastPaid;
    }
}
