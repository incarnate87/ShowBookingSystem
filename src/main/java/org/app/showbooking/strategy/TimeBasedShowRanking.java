package org.app.showbooking.strategy;

import org.app.showbooking.model.LiveShow;
import org.app.showbooking.model.ShowSlot;


import java.util.Comparator;
import java.util.List;

public class TimeBasedShowRanking implements ShowRankingStrategy {
    @Override
    public List<LiveShow> rank(List<LiveShow> shows) {
        shows.sort(Comparator.comparing(show -> show.getSlots().values().stream()
                .map(ShowSlot::getTime)
                .min(String::compareTo)
                .orElse("")));
        return shows;
    }
}