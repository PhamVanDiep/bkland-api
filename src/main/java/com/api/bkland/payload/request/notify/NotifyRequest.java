package com.api.bkland.payload.request.notify;

public class NotifyRequest {
    private Notification notification;
    private String to;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
