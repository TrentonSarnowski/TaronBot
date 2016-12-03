package com.company.TaronBot.Game.Moves;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public class Placement implements Move {
    protected int x;
    protected int y;
    protected int type;
    protected double weight;
    public Placement(String move){

    }
    public Placement(int x, int y, int type, double weight){
        this.weight=weight;
        this.x=x;
        this.y=y;
        this.type=type;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public List<Integer>[][] performMove(Board board, boolean control) {
        List<Integer>[][] map=board.getMap();
        if (checkFeasible(board, control)) {
            if(control){
                if(type==3){
                    board.reducePositiveCapRemain();
                }else{
                    board.reducePositiveFlatRemain();
                }
                map[x][y].add(type);

                return map;
            }else{
                if(type==3){
                    board.reduceNegativeCapRemain();
                }else{
                    board.reducePositiveFlatRemain();
                }
                map[x][y].add(type*-1);
                return map;
            }
        }
        return null;
    }

    @Override
    public boolean checkFeasible(Board map, boolean cont) {
        if(Math.abs(type)!=3){
            if(map.getMap()[x][y].isEmpty()){

            };
            return map.getMap()[x][y].isEmpty();

        }
        if( type==3&&(cont && (map.getPositiveCapRemain()>0)||(!cont&&map.getNegativeCapRemain()>0))){
            return map.getMap()[x][y].isEmpty();
        }


        return false;
        //return map.getMap()[x][y].isEmpty();
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
    public String toPlayTakString(){
        return toString();
    }
    @Override
    public double getWeight() {
        return weight;
    }
}
