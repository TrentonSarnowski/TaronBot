package com.company.TaronBot.Game;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public interface Move {

    String toString();
    List<Integer>[][] performMove(List<Integer>[][] map, boolean control);
}
