package com.company.TaronBot.Game.Moves;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public class DeStack implements Move {

    List<Integer> leftBehind;// should not contain zero
    Integer pickUpC;//the amount initially taken of the stack
    boolean up; // if true goes along the y axis, if false along the x
    boolean positive;// positive goes up, else goes down
    int x;//inside of bounds
    int y;//inside of bounds
    int xend;
    int yend;
    double weight;


    public double getWeight() {
        return weight;
    }


    public DeStack(String move) {

    }



    public static DeStack DeStack(int x, int y, Integer[] left, int pickup, boolean verticalAxis, boolean positiveDirection, double weight) throws Exception {
        ArrayList<Integer> leftbehind = new ArrayList<>();
        int PickUp = 0;
        for (Integer i : left) {
            if (i != null) {
                PickUp += i;
                leftbehind.add(i);
            }
        }
        if (pickup != PickUp) {
            throw new Exception();
        }

        DeStack temp = new DeStack(x, y, leftbehind, pickup, verticalAxis, positiveDirection, weight);
        return temp;
    }


    public DeStack(int x, int y, List<Integer> left, int pickup, boolean verticalAxis, boolean positiveDirection, double weight) {
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.leftBehind = left;
        this.pickUpC = pickup;
        this.up = verticalAxis;
        this.positive = positiveDirection;
        int dif = left.size();
        if (verticalAxis && positiveDirection) {
            xend = x;
            yend = y + dif;
        } else if (verticalAxis) {
            xend = x;
            yend = y - dif;
        } else if (positiveDirection) {
            xend = x + dif;
            yend = y;
        } else {
            xend = x - dif;
            yend = y;
        }

    }


    public String toPlayTakString() {
        String ret = "M ";
        switch (x) {
            case 0:
                ret += "a";
                break;
            case 1:
                ret += "b";
                break;
            case 2:
                ret += "c";
                break;
            case 3:
                ret += "d";
                break;
            case 4:
                ret += "e";
                break;
            case 5:
                ret += "f";
                break;
            case 6:
                ret += "g";
                break;
            case 7:
                ret += "h";
                break;
            default:
                return "";
        }
        ret += (y + 1) + " ";
        switch (xend) {
            case 0:
                ret += "a";
                break;
            case 1:
                ret += "b";
                break;
            case 2:
                ret += "c";
                break;
            case 3:
                ret += "d";
                break;
            case 4:
                ret += "e";
                break;
            case 5:
                ret += "f";
                break;
            case 6:
                ret += "g";
                break;
            case 7:
                ret += "h";
                break;
            default:
                return "";
        }
        ret += (yend + 1);
        for (Integer e : leftBehind) {
            ret += " " + e;
        }
        return ret;
    }


    @Override
    public String toString() {
        String ret = "" + pickUpC;
        switch (x) {
            case 0:
                ret += "a";
                break;
            case 1:
                ret += "b";
                break;
            case 2:
                ret += "c";
                break;
            case 3:
                ret += "d";
                break;
            case 4:
                ret += "e";
                break;
            case 5:
                ret += "f";
                break;
            case 6:
                ret += "g";
                break;
            case 7:
                ret += "h";
                break;
            default:
                return "";
        }
        ret += (y + 1);
        if (up) {
            if (positive) {
                ret += "+";
            } else {
                ret += "-";
            }
        } else {
            if (positive) {
                ret += ">";
            } else {
                ret += "<";
            }
        }
        for (Integer i :
                leftBehind) {
            if (i != null) {
                ret += i;
            }
        }
        return ret;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    //Map should be a square array
    public List<Integer>[][] performMove(Board board, boolean control) {
        List<Integer>[][] map = board.getMap();
        List<Integer> pickUp = map[x][y];
        int sum = pickUp.size() - pickUpC;
        int yChange;
        int xChange;
        int sign;
        if (up) {
            xChange = 0;
            yChange = 1;
        } else {
            xChange = 1;
            yChange = 0;
        }
        if (positive) {
            sign = 1;
        } else {
            sign = -1;
        }

        if (!checkFeasible(board, control)) {
            return null;
        }

        for (int i = 1; i <= leftBehind.size(); i++) {

            if (!map[x + i * xChange * sign][y + i * yChange * sign].isEmpty() &&
                    Math.abs(map[x + i * xChange * sign][y + i * yChange * sign].get(map[x + i * xChange * sign][y + i * yChange * sign].size() - 1)) == 2) {
                map[x + i * xChange * sign][y + i * yChange * sign].set(
                        map[x + i * xChange * sign][y + i * yChange * sign].size() - 1,
                        map[x + i * xChange * sign][y + i * yChange * sign].get(map[x + i * xChange * sign][y + i * yChange * sign].size() - 1) / 2);
            }

            for (int j = 0; j < leftBehind.get(i - 1); j++) {


                map[x + i * (sign * xChange)][y + i * (sign * yChange)].add(pickUp.get(sum));
                for (List<Integer> l[]:board.getMap()) {
                    for (List<Integer> list:l) {
                        if(list.isEmpty()){
                            System.out.print("0 ");
                        }else{
                            System.out.print(list.get(list.size()-1)+" ");

                        }
                    }
                    System.out.println();

                }
                sum++;
            }
        }
        for (int i = 0; i < pickUpC; i++) {
            map[x][y].remove(map[x][y].size() - 1);
        }
        for (List<Integer> l[]:board.getMap()) {
            for (List<Integer> list:l) {
                if(list.isEmpty()){
                    System.out.print("0 ");
                }else{
                    System.out.print(list.get(list.size()-1)+" ");

                }
            }
            System.out.println();

        }
        return map;
    }


    public boolean checkFeasible(Board board, boolean control) {
        //System.err.println(toString());
        if (board.getMap()[x][y].isEmpty()) {
            return false;
        }
        if (board.getMap()[x][y].get(board.getMap()[x][y].size() - 1) > 0 && !control) {
            return false;
        }
        if (board.getMap()[x][y].get(board.getMap()[x][y].size() - 1) < 0 && control) {
            return false;
        }
        if (board.getMap()[x][y].size() < pickUpC) {
            return false;
        }
        List<Integer>[][] map = board.getMap();
        List<Integer> pickUp = map[x][y];
        int xChange;
        int yChange;
        int sign;
        if (up) {
            xChange = 0;
            yChange = 1;
        } else {
            xChange = 1;
            yChange = 0;
        }
        if (positive) {
            sign = 1;
        } else {
            sign = -1;
        }

        int sum = pickUp.size() - pickUpC;
        int sum2 = 0;
        int sum3 = 0;

        for (Integer left : leftBehind) {
            sum2 += (left != null ? left : 0);
        }
        for (Integer left : pickUp) {
            sum3 += 1;
        }

        if (sum3 < pickUpC) {

            return false;
        }

        if (sign * (sign * leftBehind.size() + y * yChange + x * xChange) > map.length * ((1 + sign) / 2)) {
            //System.err.println("3");

            return false;
            //edge check
        }

        for (int i = 1; i <= leftBehind.size(); i++) {
            int xTrue=x + (i * xChange * sign);
            int yTrue=y + (i * yChange * sign);
            try {
                if (map[xTrue][yTrue].size() != 0) {
                    int topValue = Math.abs(board.getMap()[xTrue][yTrue].get(board.getMap()[xTrue][yTrue].size()-1));
                    if (topValue <= 1) {

                    } else if (topValue <= 2) {
                        if (Math.abs(pickUp.get(pickUp.size() - 1)) == 3 && leftBehind.get(i) == 1 && i == leftBehind.size() - 1) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (topValue <= 3) {
                        return false;

                    } else {
                        return false;
                    }

                }

            } catch (Exception e) {
                return false;
            }
            //wall/cap check

        }
        return true;
    }

}
