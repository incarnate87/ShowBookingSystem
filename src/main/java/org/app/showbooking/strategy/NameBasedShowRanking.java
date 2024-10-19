package org.app.showbooking.strategy;

import org.app.showbooking.model.LiveShow;

import java.util.Comparator;
import java.util.List;

public class NameBasedShowRanking implements ShowRankingStrategy {
    @Override
    public List<LiveShow> rank(List<LiveShow> shows) {
        shows.sort(Comparator.comparing(LiveShow::getName));
        return shows;
    }
}
