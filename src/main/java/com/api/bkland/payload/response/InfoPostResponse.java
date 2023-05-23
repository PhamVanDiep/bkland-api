package com.api.bkland.payload.response;

import com.api.bkland.payload.dto.InfoPostDTO;
import com.api.bkland.payload.dto.UserDTO;

public class InfoPostResponse extends InfoPostDTO {
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
