package org.app.showbooking.main;

import org.app.showbooking.system.ShowBookingSystem;

public class Main {
    public static void main(String[] args) {
        ShowBookingSystem system = new ShowBookingSystem();



    //system.setShowRankingStrategy(new NameBasedShowRanking());
    //system.showAvailByGenre("Comedy");

        // Default ranking by time
        system.registerShow("TMKOC", "Comedy");
        system.addShowSlot("TMKOC", "09:00-10:00", 3);
        system.addShowSlot("TMKOC", "12:00-14:00", 2);
        system.addShowSlot("TMKOC", "15:00-16:00", 5);

        system.registerShow("The Sonu Nigam Live Event", "Singing");
        system.addShowSlot("The Sonu Nigam Live Event", "10:00-11:00", 3);
        system.addShowSlot("The Sonu Nigam Live Event", "13:00-14:00", 2);

        // Show available slots by genre
        System.out.println("\nAvailable slots for Comedy shows:");
        system.showAvailByGenre("Comedy");


        String bookingIdUserA = system.bookTicket("UserA", "TMKOC", "12:00-13:00"); // Invalid, should not book
        String bookingIdUserB = system.bookTicket("UserB", "TMKOC", "15:00-16:00"); // Valid slot

        if (bookingIdUserB != null) {
            system.cancelBooking("UserB", bookingIdUserB);
        } else {
            System.out.println("Booking ID not found for UserB.");
        }

        // Trending shows
        system.trendingLiveShow();
    }
}