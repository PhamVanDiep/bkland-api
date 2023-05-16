package com.api.bkland.payload.request;

import com.api.bkland.constant.enumeric.EStatus;

public class UpdatePostStatusRequest {
    private String postId;
    private EStatus status;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }
}
