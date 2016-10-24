package com.company.TaronBot.MoveCalc;

import java.util.List;

/**
 * Created by sarnowskit on 10/22/2016.
 */
public class cord {
    private int x;
    private int y;
    private boolean cap;
    private boolean wall;
    private List<Integer> underSide;
    private boolean conrol;
    private boolean real;
    private int roadCount=0;
    public List<Road> checks;
    public cord (int x, int y, boolean real) {
        this.x=x;
        this.y=y;
        this.real=real;

    }

    public boolean getControlFlat(){
        return conrol&&!wall;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * calling this potential or realised for the moment
     * @return
     */
    public boolean isReal() {
        return real;
    }

    public int getRoadCount() {
        return roadCount;
    }

    public void incrementRoadCount() {
        this.roadCount = roadCount+1;
    }
}
