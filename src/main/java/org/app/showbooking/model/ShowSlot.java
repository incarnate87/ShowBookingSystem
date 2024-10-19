package org.app.showbooking.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShowSlot {
    String time;
    int capacity;
    int availableTickets;
    Queue<User> waitlist;
    List<Booking> bookings;

    public ShowSlot(String time, int capacity) {
        this.time = time;
        this.capacity = capacity;
        this.availableTickets = capacity;
        this.waitlist = new LinkedList<>();
        this.bookings = new ArrayList<>();
    }

    public String getTime() {
        return time;
    }

    public List<Booking> getBookings () {
        return bookings;
    }

    public int getAvailableTickets () {
        return availableTickets;
    }

    public boolean bookTicket(User user) {
        if (availableTickets > 0) {
            bookings.add(new Booking(user, this));
            availableTickets--;
            return true;
        } else {
            waitlist.add(user);
            return false;
        }
    }

    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
        availableTickets++;
        if (!waitlist.isEmpty()) {
            User nextUser = waitlist.poll();
            bookTicket(nextUser);
            System.out.println("Waitlisted user " + nextUser.name + " has been booked.");
        }
    }
}
