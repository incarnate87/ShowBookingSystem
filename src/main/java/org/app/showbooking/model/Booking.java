package org.app.showbooking.model;

import java.util.UUID;

public class Booking {
    User user;
    ShowSlot slot;
    String id;

    public String getId() {
        return id;
    }
    public Booking(User user, ShowSlot slot) {
        this.user = user;
        this.slot = slot;
        this.id = UUID.randomUUID().toString();
    }
}
