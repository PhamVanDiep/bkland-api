package com.api.bkland.payload.dto;

import java.time.Instant;

public class SpecialAccountDTO {
    private String userId;

    private Integer monthlyCharge;

    private boolean agency;

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
