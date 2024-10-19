package org.app.showbooking.strategy;

import org.app.showbooking.model.LiveShow;

import java.util.List;

public interface ShowRankingStrategy {
    List<LiveShow> rank(List<LiveShow> shows);
}