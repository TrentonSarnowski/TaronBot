package com.company.TaronBot.Moves;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public interface Move {

    public String toString();

    public List<Integer>[][] performMove(List<Integer>[][] map);
}
