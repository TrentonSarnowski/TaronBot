package com.company.TaronBot.Game;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.company.TaronBot.Game.Moves.*;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class Board {
    private List<Integer> map[][];

    public Board(int sideLength, List<Move> boardState, boolean start){
        map=new List[sideLength][sideLength];
        for (List[] e:map) {
            for (List here:e) {
                here=new ArrayList();
            }

        }
        for (int i = 0; i <sideLength ; i++) {
            for (int j = 0; j <sideLength ; j++) {
                map[i][j]=new ArrayList<>();
            }
        }
        boolean ready=start;
        for (Move e:boardState) {
            System.err.println(tryMove(e, ready));
            ready=!ready;
        }
    }

    public List<Integer>[][] getMap() {
        return map;
    }

    public boolean tryMove(Move e, boolean positive){
        e.performMove(map, positive);

        return false;
    }
    public Move checkForVictory(){
        boolean topLevel[][]=topLevel();
        Move m=null;
        for (int i = 0; i < topLevel.length ; i++) {
            //// TODO: 10/28/2016
            for (int j = 4; j >=0 ; j--) {
                m=needFill(i,0,new boolean[topLevel.length][topLevel.length],topLevel,j,
                        true,new boolean[topLevel.length][topLevel.length]);
                if(m!=null){
                    return m;
                }
                m=needFill(i,0,new boolean[topLevel.length][topLevel.length],topLevel,j,
                        false,new boolean[topLevel.length][topLevel.length]);
                if(m!=null){
                    return m;
                }
            }
        }

        return null;
    }
    private Move getMove(boolean[][] needFill, boolean[][] topLevel, int row, boolean vertical){
        if(vertical){
            int sum=0;
            int value=-1;
            for (int i = 0; i <needFill.length ; i++) {
                if(needFill[i][row]&&!topLevel[i][row]){
                    sum++;
                    value=i;
                }
            }
            if(value==-1){
                return null;
            }
            if(sum==1){
                if(map[value][row].isEmpty()){
                    Placement n=new Placement(value,row,1,3);
                    return n;
                }else if(map[value][row].get(map[value][row].size()-1)==-3){


                    for (int i = 0; i < map.length; i++) {
                        if(map[value][i].get(map[value][i].size()-1)>0){
                            int inStack=0;
                            for (int piece:map[value][i]) {
                                if(piece==1||piece==3){
                                    inStack++;
                                }
                            }
                            if(inStack>0) {
                                //// TODO: 10/28/2016
                                //check for movement
                            }
                        }
                        if(map[i][row].get(map[i][row].size()-1-1)>0){
                            int inStack=0;
                            for (int piece:map[i][row]) {
                                if(piece==1||piece==3){
                                    inStack++;
                                }
                            }
                            if(inStack>0) {
                                //// TODO: 10/28/2016
                                //check for movement
                            }
                        }

                    }

                }else if(map[value][row].get(map[value][row].size()-1)==-3){
                        return null;
                }
                if(map[value][row].get(map[value][row].size()-1)==2){
                    int inStack=0;
                    for (int piece:map[value][row]) {
                        if(piece==1||piece==3){
                            inStack++;
                        }
                    }
                    if(inStack>0) {
                        //// TODO: 10/28/2016
                        //check for movement
                    }
                }

                if(Math.abs(map[value][row].get(map[value][row].size()-1))==2){
                    for (int i = 0; i < map.length; i++) {
                        if(map[value][i].get(map[value][i].size()-1)==3){
                            //todo will need to add walls to calc
                            getDeStack(value,i,needFill,topLevel,row>i,vertical);
                        }
                        if(map[i][row].get(map[i][row].size()-1)==3){

                            getDeStack(i,row,needFill,topLevel,value>i,vertical);

                        }

                    }
                }
            }
            //check for controlled stacks with enough controlled flats(capstone) in the mask
            if(vertical) {
                for (int i = 0; i < needFill.length; i++) {
                    int inStack = 0;
                    for (int piece : map[i][row]) {
                        if (piece == 1 || piece == 3) {
                            inStack++;
                        }
                    }
                    if (inStack > sum) {
                        //// TODO: 10/28/2016
                        getDeStack(i, row, needFill, topLevel, value>i, vertical);

                    }
                }
            }
        }else {
            //todo add other direction
            int sum = 0;
            int value = -1;
            for (int i = 0; i < needFill.length; i++) {
                if (needFill[row][i] && !topLevel[row][i]) {
                    sum++;
                    value = i;
                }
            }
            if (value == -1) {
                return null;
            }
            if (sum == 1) {
                if (map[row][value].isEmpty()) {
                    Placement n = new Placement(row, value, 1,1);
                    return n;
                } else if (map[row][value].get(map[row][value].size() - 1) == -3) {
                    return null;
                }

                for (int i = 0; i < map.length; i++) {
                    if (!map[i][value].isEmpty()&&map[i][value].get(map[i][value].size() - 1) > 0) {
                        int inStack = 0;
                        for (int piece : map[i][value]) {
                            if (piece == 1 || piece == 3) {
                                inStack++;
                            }
                        }
                        if (inStack > 0) {
                            //// TODO: 10/28/2016
                            //check for movement
                        }
                    }
                    if (map[row][i].get(map[row][i].size() - 1 ) > 0) {
                        int inStack = 0;
                        for (int piece : map[row][i]) {
                            if (piece == 1 || piece == 3) {
                                inStack++;
                            }
                        }
                        if (inStack > 0) {
                            //// TODO: 10/28/2016
                            //check for movement
                        }
                    }

                }

            } else if (!map[row][value].isEmpty()&&map[row][value].get(map[row][value].size() - 1) == -3) {
                return null;
            }
            if (!map[row][value].isEmpty()&&map[row][value].get(map[row][value].size() - 1) == 2) {
                int inStack = 0;
                for (int piece : map[row][value]) {
                    if (piece == 1 || piece == 3) {
                        inStack++;
                    }
                }
                if (inStack > 0) {
                    //// TODO: 10/28/2016
                    //check for movement
                }
            }

            if (!map[row][value].isEmpty()&&Math.abs(map[row][value].get(map[row][value].size() - 1)) == 2) {
                for (int i = 0; i < map.length; i++) {
                    if (map[i][value].get(map[i][value].size() - 1) == 3) {
                        getDeStack(i, value, needFill, topLevel, row > i, vertical);
                    }
                    if (map[row][i].get(map[row][i].size() - 1) == 3) {

                        getDeStack(i, row, needFill, topLevel, value > i, vertical);

                    }

                }
            }

            //check for controlled stacks with enough controlled flats(capstone) in the mask
            if (vertical) {
                for (int i = 0; i < needFill.length; i++) {
                    int inStack = 0;
                    for (int piece : map[i][row]) {
                        if (piece == 1 || piece == 3) {
                            inStack++;
                        }
                    }
                    if (inStack > sum) {
                        //// TODO: 10/28/2016
                        getDeStack(i, row, needFill, topLevel, value > i, vertical);

                    }
                }
            }
        }

        return null;

}
    /* step 1:place top piece on furthest need fill not part of top level
         * step 2:if location 2 needs to be controlled leave appropriate number on 1
         * if 3 leave apropriate number on 2 and so on till end
         *
         * //todo add feasability check(walls and caps)
         */
    private Move getDeStack(int x, int y, boolean[][] needFill, boolean[][] topLevel, boolean positive, boolean vertical){
        List<Integer> stack=map[x][y];
        int pickUp=needFill.length;
        if (stack.size()<pickUp) {
            pickUp=stack.size();
        }
        boolean topLevelClone[][]=topLevel.clone();
        int distance;
        if (checkConsistentDirections(x, y, needFill, topLevel)) {
            return null;
        }
        ArrayList<Integer> leftBehind=new ArrayList<>();

        return getMoveVerticalPositive(x, y, needFill, positive, vertical, stack, pickUp, topLevelClone, leftBehind);





    }


    /**
     * gets the vertical positive move
     * @param x starting location
     * @param y starting location
     * @param needFill mandatory to fill
     * @param positive up or down the axis
     * @param vertical along the y axis?
     * @param stack starting locations contains
     * @param pickUp pick up count from stack
     * @param topLevelClone clone of the top level to use as a guine pig
     * @param leftBehind the number of pieces to be left behind on a given square
     * @return
     */
    private Move getMoveVerticalPositive(int x, int y, boolean[][] needFill, boolean positive, boolean vertical, List<Integer> stack, int pickUp, boolean[][] topLevelClone, ArrayList<Integer> leftBehind) {

        int sign;
        int xChange;
        int yChange;
        if(positive){
            sign=1;
        }else{
            sign=-1;
        }
        if(vertical){
            xChange=0;
            yChange=1;
        }else{
            yChange=0;
            xChange=1;
        }
        int furthest=y*yChange+x*xChange;
        for (int i = y*(yChange)+x*(xChange); i < needFill.length&&i>0; i+=sign) {
            if(needFill[x][y]){
                furthest=i;
            }
        }
        if(needFill[x][y]){
            int pickUpDown;
            for (pickUpDown = 0;
                 pickUpDown < pickUp&&
                         !(stack.get(stack.size()-(pickUp-pickUpDown))==1
                                 ||stack.get(stack.size()-(pickUp-pickUpDown))==3);
                 pickUpDown++);
            pickUp=pickUp-pickUpDown;
            topLevelClone[x][y]=true;
        }
        int useable=pickUp;
        //int i = 1; i <= y*yChange+x*xChange&&useable>0 && furthest<y-i; i++
        for (int i = 1; i <= (((-sign)+1)/2)*needFill.length-sign*(y*yChange+x*xChange)&&useable>0&&sign*furthest>sign*(y*yChange+x*xChange+sign*i); i++) {
            if(Math.abs(map[x+xChange*i][y+yChange*i].get(map[x+xChange*i][y+yChange*i].size()-1))==3){
                return null;
            }
            if(Math.abs(map[x+xChange*i][y+yChange*i].get(map[x+xChange*i][y+yChange*i].size()-1))==2){
                if(stack.get(stack.size()-1)==3){
                    if(needFill[x+xChange*(i-sign)][y+yChange*(i-sign)]){
                        if(stack.get(stack.size()-2)==1){

                            int prev=leftBehind.remove(leftBehind.size()-1);
                            leftBehind.add(useable+prev-1);
                            leftBehind.add(1);
                            useable=0;
                        }else{
                            return null;
                        }
                    }else{
                        leftBehind.remove(leftBehind.size()-1);
                        leftBehind.add(useable-1);
                        leftBehind.add(1);
                        useable=0;
                    }
                }else{
                    return null;
                }
            }else if(needFill[x+xChange*i][y+yChange*i]){
                useable = getUsableNeedControl(x+xChange*i, y+yChange*i, stack, topLevelClone, leftBehind, useable);

            }else{
                useable = getUsableNoControl(x+xChange*i, y+yChange*i, stack, topLevelClone, leftBehind, useable);

            }
        }
        if(useable!=0) {
            leftBehind.add(useable);
        }
        if (checkRoadEquivalence(needFill, topLevelClone)) {
            return null;
        }
        return new DeStack(x,y,leftBehind,pickUp,vertical,positive,1);
    }


    private int getUsableNeedControl(int x, int y, List<Integer> stack, boolean[][] topLevelClone, ArrayList<Integer> leftBehind, int useable) {

        int pickUpDown;
        for (pickUpDown = 1;
             pickUpDown < useable&&
                     !(stack.get(stack.size()-(useable-pickUpDown))==1
                             ||stack.get(stack.size()-(useable-pickUpDown))==3);
             pickUpDown++);
        leftBehind.add(pickUpDown);
        useable=useable-pickUpDown;
        topLevelClone[x][y]=true;
        return useable;
    }

    private int getUsableNoControl(int x, int y, List<Integer> stack, boolean[][] topLevelClone, ArrayList<Integer> leftBehind, int useable) {
        topLevelClone[x][y] = stack.get(stack.size() - (1 + useable)) == 1 || (stack.get((stack.size() - (1 + useable))) == 3);
        leftBehind.add(1);
        useable--;
        return useable;
    }

    private boolean checkRoadEquivalence(boolean[][] needFill, boolean[][] topLevelClone) {
        for (int i = 0; i <topLevelClone.length ; i++) {
            for (int j = 0; j <topLevelClone.length ; j++) {
                if(needFill[i][j]&&!topLevelClone[i][j]){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkConsistentDirections(int x, int y, boolean[][] needFill, boolean[][] topLevel) {
        boolean before=false;
        boolean checktrue=false;
        for (int i = 0; i <map.length ; i++) {
            if ((needFill[x][i] && !topLevel[x][i] && i < y) || (needFill[y][i] && !topLevel[y][i] && i < y)) {
                before = true;
            }
            if ((needFill[x][i] && !topLevel[x][i] && i > y && before)||(needFill[y][i]&&!topLevel[y][i]&&i>y&&before)) {
                checktrue=true;
                //check for Consistant direction;
            }

        }
        return checktrue;
    }

    /**
     *
     * @param x current x cord
     * @param y current y cord
     * @param visitable can not visit things marked as 1
     * @param topLevel can not visit things marked as zero
     * @param row allowed empty row
     * @param vertical vertical/horizontal
     * @param needFill returns the positions in a row that need to be filled to get a road
     * @return
     */
    private Move needFill(int x, int y,
                                 boolean[][] visitable, boolean[][] topLevel,
                                 int row, boolean vertical, boolean[][] needFill){
        Move ret;
        needFill[x][y] = true;
        boolean forwardVisitable[][]=new boolean[visitable.length][visitable.length];
        for (int i = 0; i <visitable.length ; i++) {
            for (int j = 0; j <visitable.length ; j++) {
                forwardVisitable[i][j]=visitable[i][j];
            }
        }
        setSaidNoTrue(x, y, forwardVisitable);




        if(y+1<needFill.length) {


            if (visitable[x][y + 1] == false && (topLevel[x][y + 1] == true || (x == row && !vertical) || (y + 1 == row && vertical))) {


                ret=needFill(x, y + 1, forwardVisitable, topLevel, row, vertical, needFill);

                if(ret!=null){
                    return ret;
                }

            }
        }else{

            needFill[x][y]=true;
            ret = getMove(needFill, topLevel, row, vertical);
            /**for (boolean n[]:
                    needFill) {
                for(boolean a:n) {
                    if(a) {
                        System.err.print(1 + " ");
                    }else{
                        System.err.print(0 + " ");

                    }
                }
                System.err.println();


             }*/
            needFill[x][y]=false;


                return ret;


        }

        if(x+1<needFill.length) {

            if (visitable[x + 1][y] == false && (topLevel[x + 1][y] == true || (x + 1 == row && !vertical) || (y == row && vertical))) {

                ret=needFill(x + 1, y, forwardVisitable, topLevel, row, vertical, needFill);
                if(ret!=null){
                    return ret;
                }

            }
        }
        if(x-1>0) {

            if (visitable[x - 1][y] == false && (topLevel[x - 1][y] == true || (x - 1 == row && !vertical) || (y == row && vertical))) {

                ret=needFill(x - 1, y, forwardVisitable, topLevel, row, vertical, needFill);
                if(ret!=null){
                    return ret;
                }

            }
        }

        if(y-1>=0) {

            if (visitable[x][y - 1] == false && (topLevel[x][y - 1] == true || (x == row && !vertical) || (y - 1 == row && vertical))) {

                ret=needFill(x, y - 1, forwardVisitable, topLevel, row, vertical, needFill);
                if(ret!=null){
                    return ret;
                }

            }
        }
        needFill[x][y] = false;

        return null;
    }

    private void setSaidNoTrue(int x, int y, boolean[][] visitable) {
        visitable[x][y]=true;
        if(x+1<visitable.length) {
            visitable[x + 1][y] = true;
        }
        if(x-1>=0) {
            visitable[x -1][y] = true;
        }
        if(y+1<visitable.length) {
            visitable[x][y+1] = true;
        }
        if(y-1>=0) {
            visitable[x][y-1] = true;
        }
    }



    public boolean[][] topLevel(){
        boolean topLevel[][]=new boolean[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(!map[i][j].isEmpty()){
                    int temp = map[i][j].get(map[i][j].size() - 1);
                    topLevel[i][j] = temp == 1 || temp == 3;
                }
            }
        }
        return topLevel;
    }
    public int[][][] getAIMap(boolean control){
        int sign=-1;

        if(control){
            sign=1;
        }
        int AIMap[][][]=new int[map.length][map.length][map.length+1];


        for (int i = 0; i <map.length ; i++) {


            for (int j = 0; j <map.length ; j++) {


                for (int k = 0; k < map.length+1; k++) {

                    if(map[i][j].size()-1>=k){

                        AIMap[i][j][k]=sign*map[i][j].get(k);
                    }


                }
            }
        }
        return AIMap;
    }

}
