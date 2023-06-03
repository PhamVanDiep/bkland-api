package com.api.bkland.constant;

public class Message {
    public static final String TAO_BAI_DANG = "Có bài viết mới về sự biến động giá nằm trong khu vực bạn quan tâm.";

    public static final String CAP_NHAT_BAI_DANG = "Quản trị viên đã cập nhật bài viết về sự biến động giá nằm trong khu vực bạn quan tâm.";

    public static final String TAO_REP = "Đã có bài đăng mới được đăng lên trong khu vực bạn đăng ký môi giới.";

    public static final String CAP_NHAT_REP = "Bài đăng nằm trong khu vực bạn đăng ký môi giới được cập nhật.";

    public static final String NEW_REP_ADMIN = "Có bài đăng bán/cho thuê mới chờ kiểm duyệt.";

    public static String getCAP_NHAT_REP_INTERESTED(String title) {
        return "Bài đăng " + title + " đã được cập nhật";
    }
}
