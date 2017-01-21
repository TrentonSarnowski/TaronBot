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
    boolean checkFeasible;
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
                    //System.out.println(board.getNegativeCapRemain());
                }else{
                    board.reduceNegativeFlatRemain();

                }
                map[x][y].add(-1*type);
                return map;
            }
        }
        System.out.print("Failed Check");
        return null;
    }

    @Override
    public boolean checkVictory(Board map, boolean cont) {
        if (!checkFeasible(map, cont)) {
            return false;

        }
        boolean b[][] = topLevelAfterMove(map, cont);
        for (int i = 0; i < b.length; i++) {
            if (RoadCheck(i, 0, b, new boolean[b.length][b.length], false)) {
                return true;
            }
            if (RoadCheck(0, i, b, new boolean[b.length][b.length], true)) {
                return true;
            }
        }
        return false;
    }

    private boolean RoadCheck(int x, int y, boolean topLevel[][], boolean saidNo[][], boolean vertical) {
        /*System.err.println(x+" "+y);
    	for( boolean[] b:saidNo){
    		for(boolean b2:b){
                if(b2){
                    System.err.print("0 ");
                }else {
                    System.err.print("_ ");
                }
    		}
    		System.err.println();
    	}
    	System.err.println();
         //*/
        int xActive = 0;
        int yActive = 0;
        if (vertical) {
            yActive = 1;
        } else {
            xActive = 1;
        }
        //System.err.println(x+" "+y);

        if (x < topLevel.length && y < topLevel.length && x >= 0 && y >= 0) {
            if (!topLevel[x][y]) {
                //System.err.println("Broke1");
                return false;
            } else if (topLevel.length - 1 == x * xActive + y * yActive) {
                return true;
            } else {
                boolean saidNoSendDown[][] = new boolean[saidNo.length][saidNo.length];
                for (int i = 0; i < saidNo.length; i++) {
                    for (int j = 0; j < saidNo.length; j++) {
                        saidNoSendDown[i][j] = saidNo[i][j];

                    }
                }
                if (x > 0) {
                    saidNoSendDown[x - 1][y] = true;
                }
                if (x < saidNo.length - 1) {
                    saidNoSendDown[x + 1][y] = true;
                }
                if (y > 0) {
                    saidNoSendDown[x][y - 1] = true;
                }
                if (y < saidNo.length - 1) {
                    saidNoSendDown[x][y + 1] = true;
                }

                if (x + 1 < saidNo.length && !saidNo[x + 1][y] && topLevel[x + 1][y] && RoadCheck(x + 1, y, topLevel, saidNoSendDown, vertical)) {
                    return true;
                }
                if (y + 1 < saidNo.length && !saidNo[x][y + 1] && topLevel[x][y + 1] && RoadCheck(x, y + 1, topLevel, saidNoSendDown, vertical)) {
                    return true;
                }
                if (x - 1 >= 0 && !saidNo[x - 1][y] && topLevel[x - 1][y] && RoadCheck(x - 1, y, topLevel, saidNoSendDown, vertical)) {
                    return true;
                }
                if (y - 1 >= 0 && !saidNo[x][y - 1] && topLevel[x][y - 1] && RoadCheck(x, y - 1, topLevel, saidNoSendDown, vertical)) {
                    return true;
                }


            }
        }
        //System.err.println("Broke2");

        return false;
    }

    private boolean[][] topLevelAfterMove(Board map, boolean cont) {
        boolean[][] ret = map.topLevel(cont);
        if (this.type == 1 || this.type == 3) {
            ret[this.x][this.y] = true;
        }

        return ret;
    }

    @Override
    public boolean checkFeasible(Board map, boolean cont) {
        if (map.getAIMap(cont)[x][y][0] != 0 || (!map.getMap()[x][y].isEmpty())) {

            checkFeasible=false;
            return false;
        }

        if(Math.abs(type)!=3){
            checkFeasible=true;
            return true;

        }else if( type==3&&((cont && (map.getPositiveCapRemain()>0))||(!cont&&map.getNegativeCapRemain()>0))){
            checkFeasible=true;
            return true;
        }else{
            checkFeasible=false;
            return false;
        }



        //return map.getMap()[x][y].isEmpty();
    }

    public String toString(){
        String ret="";
        switch (type){
            case 1:
                ret+="F";
                break;
            case 2:
                ret+="S";
                break;
            case 3:
                ret+="C";
                break;
            default:
                return "fail type";
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
                return "fail position: " + x;
        }
        ret+=""+(y+1);

        return ret;
    }
    public String toPlayTakString(){
        String ret = "P ";

        switch (x) {
            case 0:
                ret += "A";
                break;
            case 1:
                ret += "B";
                break;
            case 2:
                ret += "C";
                break;
            case 3:
                ret += "D";
                break;
            case 4:
                ret += "E";
                break;
            case 5:
                ret += "F";
                break;
            case 6:
                ret += "G";
                break;
            case 7:
                ret += "H";
                break;
            default:
                return "fail position: " + x;
        }
        ret += "" + (y + 1) + " ";
        switch (type) {
            case 1:
                ret += "";
                break;
            case 2:
                ret += "W";
                break;
            case 3:
                ret += "C";
                break;
            default:
                return "fail type";
        }
        return ret;
    }
    @Override
    public double getWeight() {
        return weight;
    }
}
