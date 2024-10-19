package org.app.showbooking.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    String name;
    Map<String, Booking> bookings;

    public User(String name) {
        this.name = name;
        this.bookings = new HashMap<>();
    }

    public void addBooking(Booking booking) {
        bookings.put(booking.id, booking);
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.remove(bookingId);
        if (booking != null) {
            booking.slot.cancelBooking(booking);
            return true;
        } else {
            System.out.println("Booking not found.");
            return false;
        }
    }
}
