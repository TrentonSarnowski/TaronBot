package com.company.TaronBot.Game;

import com.company.TaronBot.Network.TakNetwork;

/**
 * Created by sarnowskit on 2/8/2017.
 */
public interface PerformOnMove {

    public void performOnMove(TakNetwork n, Move m, int depth, Board b);
}
