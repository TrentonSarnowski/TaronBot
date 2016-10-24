package com.company.TaronBot.MoveCalc;

import java.util.List;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class moveCalc {
    /**
     * Should identify a winning move of depth 1 ply
     * return string "-1" if no winning move
     * <p>
     * y________
     * x [
     * [
     * [
     * [
     * [
     * [
     */
    private static String end;
    private static List String;

    public static String winningMove(String[][] map, String ending) {
        end = ending;


        return "-1";
    }


    /**
     * This will check if the map has a winning position
     * currently assumes w as it's letter
     *
     * @param map
     * @return
     */
    private static cord[][] checkMap(String[][] map) {
        cord[][] ret= new cord[map.length][map.length];

            for (int i = 0; i < ret.length; i++) {
                if (map[0][i].endsWith(end) && map[1][i].endsWith(end)) {

                }
                    checkSquareRight(1, i, map, -1, true);//minor efficiency to prevent wall backtracking


                }

            for (int i = 0; i < map.length; i++) {
                if (map[i][0].endsWith(end) && map[i][1].endsWith(end)) {
                    {
                        if (checkSquareRight(i, 1, map, -1, false)) {//minor efficiency to prevent wall backtracking
                           // return true;
                        }
                    }
                }
            }
            //return false;
        return null;
        }



    /**
     *
     * @param x
     * @param y column
     * @param map
     * @param rowExempt
     * @return
     */



    private static boolean checkSquareRight(int x,int y, String[][] map, int rowExempt, boolean directionCheck){
        if(x==map.length&&directionCheck){
            return true;
        }else if(y==map.length){
            return true;
        }else if(rowExempt>=0){
            String held=map[x][y];
            map[x][y]="-1";
            if(map[x+1][y].endsWith(end)||rowExempt==x+1){

                if(checkSquareRight(x+1,y,map, rowExempt,directionCheck)){
                    return true;
                }

            }

                if (y < map.length) {
                    if (map[x][y + 1].endsWith(end)||rowExempt==x) {
                        if (checkSquareRight(x, y + 1, map, rowExempt,directionCheck)) {
                            return true;
                        }

                    }
                }

                if (y > 0) {
                    if (map[x][y - 1].endsWith(end)||rowExempt==x) {
                        if (checkSquareRight(x, y - 1, map, rowExempt,directionCheck)) {
                            return true;
                        }

                    }
                }
            if(x>1) {
                if (map[x - 1][y].endsWith(end)||rowExempt==x-1) {

                    if (checkSquareRight(x - 1, y, map, rowExempt, directionCheck)) {
                        return true;
                    }

                }
            }
            map[x][y]=held;

        } else {
            String held=map[x][y];
            map[x][y]="-1";
            if(map[x+1][y].endsWith(end)||rowExempt==-(y+1)){

                if(checkSquareRight(x+1,y,map, rowExempt,directionCheck)){
                    return true;
                }

            }

            if (y < map.length) {
                if (map[x][y + 1].endsWith(end)||rowExempt==-(y+2)) {
                    if (checkSquareRight(x, y + 1, map, rowExempt,directionCheck)) {
                        return true;
                    }

                }
            }

            if (y > 0) {
                if (map[x][y - 1].endsWith(end)||rowExempt==-(y)) {
                    if (checkSquareRight(x, y - 1, map, rowExempt,directionCheck)) {
                        return true;
                    }

                }
            }
            if(x>1) {
                if (map[x - 1][y].endsWith(end)||rowExempt==-(y+1)) {

                    if (checkSquareRight(x - 1, y, map, rowExempt, directionCheck)) {
                        return true;
                    }

                }
            }
            map[x][y]=held;

        }
        return false;
    }


}
