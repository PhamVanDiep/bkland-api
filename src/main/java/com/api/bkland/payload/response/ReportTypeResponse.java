package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.ReportTypeDTO;

public class ReportTypeResponse extends ReportTypeDTO {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
