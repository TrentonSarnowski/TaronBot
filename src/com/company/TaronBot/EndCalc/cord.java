package com.company.TaronBot.EndCalc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sarnowskit on 10/24/2016.
 */
public class cord {
    int x,y;
    boolean real;
    List<Road> partOf;
    public cord(int xs, int ys , boolean reals ){
        x=xs;
        y=ys;
        real=reals;
        partOf= new LinkedList<>();
    }
    public void addRoad(Road road){
        partOf.add(road);
    }
}
