package com.company.TaronBot.Game;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public interface Move {

    String toString();
    List<Integer>[][] performMove(Board map, boolean control);
    boolean checkFeasible(Board map, boolean cont);
    double getWeight();
    int  getType();
    String toPlayTakString();

    boolean checkVictory(Board map, boolean cont);
}
