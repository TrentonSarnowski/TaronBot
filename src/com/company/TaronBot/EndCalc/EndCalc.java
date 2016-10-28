package com.company.TaronBot.EndCalc;

import com.company.Main;
import com.company.TaronBot.MoveCalc.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class EndCalc {
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

    public static String winningMove(boolean[][] map, String ending) {
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
    private static boolean checkMap(boolean[][] map) {

        if (map.length <= 4) {
            for (int i = 0; i < map.length; i++) {
                if (map[0][i] && map[1][i]) {
                    if (checkSquareRight(1, i, map, 0)) {//minor efficiency to prevent wall backtracking
                        return true;
                    }
                }
            }
            for (int i = 0; i < map.length; i++) {
                if (map[i][0] && map[i][1]) {
                    {
                        if (checkSquareDown(i, 1, map, 0)) {//minor efficiency to prevent wall backtracking
                            return true;
                        }
                    }
                }
            }
            return false;
        } else {
            for (int i = 0; i < map.length; i++) {
                if (map[0][i] && map[1][i]) {
                    if (checkSquareRight(1, i, map)) {//minor efficiency to prevent wall backtracking
                        return true;
                    }
                }
            }
            for (int i = 0; i < map.length; i++) {
                if (map[i][0] && map[i][1]) {
                    {
                        if (checkSquareDown(i, 1, map)) {//minor efficiency to prevent wall backtracking
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * @param x
     * @param y
     * @param map
     * @param defaultUpDown 0==nothing, 1= skip -1, -1== skip 1
     * @return
     */
    private static boolean checkSquareRight(int x, int y, boolean[][] map, int defaultUpDown) {
        if (x == map.length) {
            return true;
        } else {
            if (map[x + 1][y]) {
                if (checkSquareRight(x + 1, y, map, 0)) {
                    return true;
                }

            }
            if (defaultUpDown > -1) {
                if (y < map.length) {
                    if (map[x][y + 1]) {
                        if (checkSquareRight(x, y + 1, map, 1)) {
                            return true;
                        }

                    }
                }
            }
            if (defaultUpDown < 1) {
                if (y > 0) {
                    if (map[x][y - 1]) {
                        if (checkSquareRight(x, y - 1, map, -1)) {
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    private static boolean checkSquareDown(int x, int y, boolean[][] map, int defaultUpDown) {
        if (y >= map.length) {
            return true;
        } else {
            if (map[x][y + 1]) {
                if (checkSquareDown(x, y + 1, map, 0)) {
                    return true;
                }

            }
            if (defaultUpDown > -1) {
                if (x < map.length) {
                    if (map[x + 1][y]) {
                        if (checkSquareDown(x + 1, y, map, 1)) {
                            return true;
                        }

                    }
                }
            }
            if (defaultUpDown < 1) {
                if (y > 0) {
                    if (map[x - 1][y]) {
                        if (checkSquareDown(x - 1, y, map, 0)) {
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }



    private static boolean checkSquareRight(int x,int y, boolean[][] map){
        if(x==map.length){
            return true;
        }
        else {
            boolean held=map[x][y];
            map[x][y]=false;
            if(map[x+1][y]){

                if(checkSquareRight(x+1,y,map)){
                    return true;
                }

            }

                if (y < map.length) {
                    if (map[x][y + 1]) {
                        if (checkSquareRight(x, y + 1, map)) {
                            return true;
                        }

                    }
                }

                if (y > 0) {
                    if (map[x][y - 1]) {
                        if (checkSquareRight(x, y - 1, map)) {
                            return true;
                        }

                    }
                }
            if(x>1) {
                if (map[x - 1][y]) {

                    if (checkSquareRight(x - 1, y, map)) {
                        return true;
                    }

                }
            }
            map[x][y]=held;

        }
        return false;
    }

    private static boolean checkSquareDown(int x,int y, boolean[][] map){
        if(x==map.length){
            return true;
        }
        else {
            boolean held=map[x][y];
            map[x][y]=false;
            if(map[x][y+1]){

                if(checkSquareDown(x,y+1,map)){
                    return true;
                }

            }

            if (x < map.length) {
                if (map[x +1][y]) {
                    if (checkSquareDown(x + 1, y, map)) {
                        return true;
                    }

                }
            }

            if (x > 0) {
                if (map[x -1][y]) {
                    if (checkSquareDown(x, y - 1, map)) {
                        return true;
                    }

                }
            }
            if(y>1) {
                if (map[x ][y-1]) {

                    if (checkSquareDown(x, y-1, map)) {
                        return true;
                    }

                }
            }
            map[x][y]=held;
        }
        return false;
    }
    private long[] things;
    public List<Road> getRoads(boolean road) {
        return null;
    }
}
