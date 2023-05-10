package com.api.bkland.payload.request;

import com.api.bkland.payload.dto.PostMediaDTO;

import java.util.List;

public class ListImageUpload {
    private List<PostMediaDTO> images;

    public List<PostMediaDTO> getImages() {
        return images;
    }

    public void setImages(List<PostMediaDTO> images) {
        this.images = images;
    }
}
