package com.company.TaronBot.Game.Moves;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;

import java.util.ArrayList;
import java.util.LinkedList;
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


    /**
     * Initilizes destack with the properties
     *
     * @param x                 x cord
     * @param y                 y cord
     * @param left              Pieces left on position(Does not include starting position)
     * @param pickup            Number of pieces to be picked up: Must agree with left
     * @param verticalAxis      does the move go along the vertical acces
     * @param positiveDirection does the move go in the positive direction
     * @param weight            what is the weight of the move(for sorting and AI usage)
     * @return
     * @throws Exception
     */
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

    /**
     * Destack inilization based off boolean iterations for future AI generations and all move generation
     *
     * @param map               represents whether the next piece is left on the same square or moves to the next square--includes starting position
     * @param verticalAxis      moving along the vertical axis
     * @param positiveDirection moving in the positive direction
     * @param weight            weight of the move
     * @param x                 starting x
     * @param y                 starting y
     */
    public DeStack(boolean map[], boolean verticalAxis, boolean positiveDirection, double weight, int x, int y) {
        LinkedList<Integer> i = new LinkedList<>();
        i.add(0);
        int pickUp = 0;
        for (Boolean b : map) {

            if (b) {
                i.set(i.size() - 1, i.getLast() + 1);
                pickUp++;
            } else {
                i.add(1);
                pickUp++;
            }
        }
        int xActive = 0;
        int yActive = 1;
        if (!verticalAxis) {
            xActive = 1;
            yActive = 0;
        }
        int sign = -1;

        if (positiveDirection) {
            sign = 1;
        }
        pickUpC = pickUp - i.getFirst();
        i.removeFirst();
        this.leftBehind = i;
        this.positive = positiveDirection;
        this.up = verticalAxis;
        this.weight = weight;
        this.x = x;
        this.xend = x + sign * xActive * i.size();
        this.yend = y + sign * yActive * i.size();

        this.y = y;
    }

    /**
     * Standard usage init
     *
     * @param x                 starting x
     * @param y                 starting y
     * @param left              left behind on each square does not include starting square
     * @param pickup            pickup count must agree with left
     * @param verticalAxis      along the vertical
     * @param positiveDirection in the posotive
     * @param weight            eight of the move--use zero if not AI
     */
    public DeStack(int x, int y, List<Integer> left, int pickup, boolean verticalAxis, boolean positiveDirection, double weight) {
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.leftBehind = left;
        this.pickUpC = pickup;
        this.up = verticalAxis;
        this.positive = positiveDirection;
        int dif = left.size();
        int xActive = 0;
        int yActive = 1;
        if (!verticalAxis) {
            xActive = 1;
            yActive = 0;
        }
        int sign = -1;

        if (positiveDirection) {
            sign = 1;
        }
        this.xend = x + sign * xActive * dif;
        this.yend = y + sign * yActive * dif;

    }

    /**
     * Turns the move into a playtakstring(the format for playtak to read it
     * "M " + sq1+" " +sq2+" " +left+ " " +left + ect.
     * sq1= starting square in format s=letter(x), q=number(y+1)
     * sq2= ending position se sq1
     *
     * @return
     */

    public String toPlayTakString() {
        String ret = "M ";
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
                return "";
        }
        ret += (y + 1) + " ";
        switch (xend) {
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
                return "";
        }
        ret += (yend + 1);
        for (Integer e : leftBehind) {
            ret += " " + e;
        }
        return ret;
    }

    /**
     * turns the move to ahuman readable string
     * M + sq + direction(><+-)+ left+left
     *
     * @return
     */
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

    /**
     * checks for victory on a given map post move--may not work use at own risk
     *
     * @param map
     * @param cont
     * @return
     */
    public boolean checkVictory(Board map, boolean cont) {
        Board testMap = map.deepCopy(map);
        if (this.checkFeasible(map, cont)) {
            this.performMove(testMap, cont);
            if (testMap.checkVictory(cont) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Destack=type zero
     *
     * @return
     */
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

                try {
                    map[x + i * (sign * xChange)][y + i * (sign * yChange)].add(pickUp.get(sum));

                    sum++;
                } catch (Exception e) {
                    System.err.println("Errr");
                }
            }
        }
        for (int i = 0; i < pickUpC; i++) {
            map[x][y].remove(map[x][y].size() - 1);
        }

        return map;
    }


    public boolean checkFeasible(Board board, boolean control) {
        //System.err.println(toString());
        if(leftBehind.size()<=0){
            return false;
        }
        if (x >= board.getMap().length || y >= board.getMap().length) {
            return false;
        }

        if(x<0||y<0){
            return false;
        }
        if (xend >= board.getMap().length || yend >= board.getMap().length) {
            return false;
        }

        if(xend<0||yend<0){
            return false;
        }
        if (board.getMap()[x][y].isEmpty()) {
            return false;
        }
        if (board.getMap()[x][y].get(board.getMap()[x][y].size() - 1) >= 0 && !control) {
            return false;
        }
        if (board.getMap()[x][y].get(board.getMap()[x][y].size() - 1) <= 0 && control) {
            return false;
        }
        if (board.getMap()[x][y].size() < pickUpC) {
            return false;
        }
        if(capCheck(board)){
            return true;
        }
            if (yend >= board.getMap()[0].length) {
                return false;
            }
            if (xend >= board.getMap()[0].length) {
                return false;
            }
            if (yend < 0) {
                return false;
            }

            if (xend < 0) {
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

        int sum3 = pickUp.size();

        if (sum3 < pickUpC) {

            return false;
        }
        int distance=Math.abs((xend-x)+yend-y);
        for (int i = 1; i <=distance ; i++) {
            if(!map[x+sign*xChange*i][y+sign*yChange*i].isEmpty()&&Math.abs(map[x+sign*xChange*i][y+sign*yChange*i].get(map[x+sign*xChange*i][y+sign*yChange*i].size()-1))>=2){
                return false;
            }
        }
        return true;
    }
    private boolean capCheck(Board b){
        List<Integer> map[][]=b.getMap();
        if(map[x][y].get(map[x][y].size()-1)!=3){
            return false;
        }

        int distance = leftBehind.size()-1;
        if(!map[xend][yend].isEmpty()&&
                ((Math.abs(map[xend][yend].get(map[xend][yend].size()-1))==2&& leftBehind.get(distance)!=1)
                        ||Math.abs(map[xend][yend].get(map[xend][yend].size()-1))==3)){
            return false;
        }
        int sign=-1;
        if(positive){
            sign=1;
        }
        int xChange=1;
        int yChange=0;
        if(up){
            yChange=1;
            xChange=0;
        }

        for (int i = 1; i <distance ; i++) {
            if(!map[x+sign*xChange*i][y+sign*yChange*i].isEmpty()){
                    if(Math.abs(map[x+sign*xChange*i][y+sign*yChange*i].get(map[x+sign*xChange*i][y+sign*yChange*i].size()-1))>=2) {
                        return false;
                    }
            }
        }
        return true;
    }

}
