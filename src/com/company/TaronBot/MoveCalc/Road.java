package com.company.TaronBot.MoveCalc;

import java.util.List;

/**
 * Created by sarnowskit on 10/22/2016.
 */
public class Road {
    List<cord> road;
    int free;
    boolean row;
    public Road( List<cord> road, int free, boolean row){
        this.road=road;
        this.free=free;
        this.row=row;
    }
}
