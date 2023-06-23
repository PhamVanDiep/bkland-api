package com.api.bkland.entity.response;

import java.time.Instant;

public interface IRepAdmin {
    String getId();
    String getType();
    String getFullName();
    Byte getSell();
    String getPhoneNumber();
    String getStatus();
    Byte getEnable();
    Double getPrice();
    Double getArea();
    Instant getCreateAt();
}
