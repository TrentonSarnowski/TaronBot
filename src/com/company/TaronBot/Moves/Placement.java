package com.company.TaronBot.Moves;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public class Placement implements Move {
    int x;
    int y;
    int type;
    public Placement(int x, int y, int type){
        this.x=x;
        this.y=y;
        this.type=type;
    }
    @Override
    public List<Integer>[][] performMove(List<Integer>[][] map) {
        if (map[x][y].isEmpty()==true) {
            map[x][y].add(type);

        }
        return map;
    }
    public String toString(){
        String ret="";
        switch (type){
            case 1:
                ret+="F";
                break;
            case 2:
                ret+="W";
                break;
            case 3:
                ret+="C";
                break;
            default:
                return "";
        }
        switch (x){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
            default:
                return "";
        }
        ret+=""+(y+1);

        return ret;
    }
}
