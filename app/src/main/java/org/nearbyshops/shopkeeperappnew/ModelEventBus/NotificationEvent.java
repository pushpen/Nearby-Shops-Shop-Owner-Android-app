package org.nearbyshops.shopkeeperappnew.ModelEventBus;

public class NotificationEvent {

    private int notificationType;

    public NotificationEvent(int notificationType) {
        this.notificationType = notificationType;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
