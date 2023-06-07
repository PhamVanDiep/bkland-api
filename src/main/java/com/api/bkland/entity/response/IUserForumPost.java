package com.api.bkland.entity.response;

public interface IUserForumPost {
    String getId();
    String getContent();
    Integer getNoLikes();
    Integer getNoComments();
    Integer getNoReports();
    String getFullName();
    String getPhoneNumber();
}
