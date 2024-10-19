package org.app.showbooking.system;

import org.app.showbooking.model.Booking;
import org.app.showbooking.model.LiveShow;
import org.app.showbooking.model.ShowSlot;
import org.app.showbooking.model.User;
import org.app.showbooking.strategy.ShowRankingStrategy;
import org.app.showbooking.strategy.TimeBasedShowRanking;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowBookingSystem {
    private Map<String, LiveShow> shows;
    private Map<String, User> users;
    private ShowRankingStrategy showRankingStrategy;

    public ShowBookingSystem() {
        shows = new HashMap<>();
        users = new HashMap<>();
        this.showRankingStrategy = new TimeBasedShowRanking();
    }

    public void setShowRankingStrategy(ShowRankingStrategy showRankingStrategy) {
        this.showRankingStrategy = showRankingStrategy;
    }

    public void registerShow(String name, String genre) {
        if (!shows.containsKey(name)) {
            shows.put(name, new LiveShow(name, genre));
            System.out.println(name + " show is registered!!");
        } else {
            System.out.println("Show '" + name + "' is already registered. Cannot register the same show twice.");
        }
    }

    public void addShowSlot(String showName, String time, int capacity) {
        LiveShow show = shows.get(showName);
        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        if (show.addSlot(time, capacity)) {
            System.out.println("Slot added successfully!");
        } else {
            System.out.println("Failed to add slot. Check if the time is valid or the slot already exists.");
        }
    }

    public void showAvailByGenre(String genre) {
        List<LiveShow> genreShows = new ArrayList<>();
        for (LiveShow show : shows.values()) {
            if (show.getGenre().equals(genre)) {
                genreShows.add(show);
            }
        }

        List<LiveShow> rankedShows = showRankingStrategy.rank(genreShows);

        for (LiveShow show : rankedShows) {
            for (ShowSlot slot : show.getSlots().values()) {
                System.out.println(show.getName() + ": (" + slot.getTime() + ") " + slot.getAvailableTickets() + " tickets available.");
            }
        }
    }

    public String bookTicket(String userName, String showName, String time) {
        User user = users.computeIfAbsent(userName, User::new);
        LiveShow show = shows.get(showName);

        if (show == null) {
            System.out.println("Show '" + showName + "' not found.");
            return null;
        }

        if (!show.getSlots().containsKey(time)) {
            System.out.println("Slot unavailable for show '" + showName + "'.");
            return null ;
        }

        ShowSlot slot = show.getSlots().get(time);
        boolean booked = slot.bookTicket(user);
        if (booked) {
            Booking booking = new Booking(user, slot);
            user.addBooking(booking);
            System.out.println("Booked. Booking id: " + booking.getId());

            return booking.getId();
        } else {
            System.out.println("Slot full. Added to waitlist.");
            return null;
        }
    }

    public void cancelBooking(String userName, String bookingId) {
        User user = users.get(userName);
        if (user != null) {
            boolean canceled = user.cancelBooking(bookingId);
            if (canceled) {
                System.out.println("Booking Canceled");
            } else {
                System.out.println("Booking not found.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public void trendingLiveShow() {
        String trendingShow = null;
        int maxBookings = 0;

        for (LiveShow show : shows.values()) {
            int bookingsCount = 0;
            for (ShowSlot slot : show.getSlots().values()) {
                bookingsCount += slot.getBookings().size();
            }
            if (bookingsCount > maxBookings) {
                maxBookings = bookingsCount;
                trendingShow = show.getName();
            }
        }

        if (trendingShow != null) {
            System.out.println("Trending live show: " + trendingShow + " with " + maxBookings + " bookings.");
        } else {
            System.out.println("No bookings yet.");
        }
    }
}