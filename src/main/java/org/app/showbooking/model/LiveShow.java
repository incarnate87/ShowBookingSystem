package org.app.showbooking.model;

import java.util.HashMap;
import java.util.Map;

public class LiveShow {
    String name;
    String genre;
    Map<String, ShowSlot> slots;

    public LiveShow(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.slots = new HashMap<>();
    }

    public boolean addSlot(String time, int capacity) {
        if (slots.containsKey(time)) {
            System.out.println("Slot already exists for this time.");
            return false;
        }

        if (!isOneHourDuration(time)) {
            System.out.println("Sorry, show timings are of 1 hour only.");
            return false;
        }

        for (ShowSlot slot : slots.values()) {
            if (overlaps(slot.time, time)) {
                System.out.println("Slot overlaps with an existing one for this show.");
                return false;
            }
        }

        slots.put(time, new ShowSlot(time, capacity));
        return true;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Map<String, ShowSlot> getSlots() {
        return slots;
    }

    private boolean isOneHourDuration(String time) {
        String[] times = time.split("-");
        if (times.length != 2) return false;

        // Assuming time format is HH:mm
        String startTime = times[0];
        String endTime = times[1];

        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        if (startParts.length != 2 || endParts.length != 2) return false;

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);
        return (endHour == startHour + 1 && endMinute == startMinute);
    }

    private boolean overlaps(String slotTime, String newTime) {
        String[] existing = slotTime.split("-");
        String[] newSlot = newTime.split("-");

        // Assuming time format is HH:mm-HH:mm
        int existingStart = Integer.parseInt(existing[0].replace(":", ""));
        int existingEnd = Integer.parseInt(existing[1].replace(":", ""));
        int newStart = Integer.parseInt(newSlot[0].replace(":", ""));
        int newEnd = Integer.parseInt(newSlot[1].replace(":", ""));

        return (newStart < existingEnd && newEnd > existingStart);
    }
}
