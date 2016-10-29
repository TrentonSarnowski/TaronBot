package com.company.TaronBot.Game;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.company.TaronBot.Game.Moves.*;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class Board {
    List<Integer> map[][];

    public Board(int sideLength, List<Move> boardState, boolean start){
        map=new List[sideLength][sideLength];
        for (List[] e:map) {
            for (List here:e) {
                here=new ArrayList();
            }

        }
        boolean ready=start;
        for (Move e:boardState) {
            tryMove(e, ready);
        }
    }
    public boolean tryMove(Move e, boolean positive){
        List<Integer> check[][]=e.performMove(map, positive);
        if(null!=check){
            map=check;
            return true;
        }
        return false;
    }
    public Move checkForVictory(){
        boolean topLevel[][]=topLevel();
        for (int i = 0; i < topLevel.length ; i++) {

        }
        for (int i = 0; i <topLevel.length ; i++) {

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
            if(sum==1){
                if(map[value][row].isEmpty()){
                    Placement n=new Placement(value,row,1);
                    return n;
                }else if(map[value][row].get(map[value][row].size())==-3){

                    //todo
                    //look for movement in row and column

                    for (int i = 0; i < map.length; i++) {
                        if(map[value][i].get(map[value][i].size())>0){
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
                        };
                        if(map[i][row].get(map[i][row].size())>0){
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
                        };

                    }

                }else if(map[value][row].get(map[value][row].size())==-3){
                        return null;
                }
                if(map[value][row].get(map[value][row].size())==2){
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
                if(Math.abs(map[value][row].get(map[value][row].size()))==2){
                    for (int i = 0; i < map.length; i++) {
                        if(map[value][i].get(map[value][i].size())==3){
                            //// TODO: 10/28/2016 check for movemnt
                        };
                        if(map[i][row].get(map[i][row].size())==3){
                            //// TODO: 10/28/2016 check for movement
                        };

                    }
                }
            }
            //check for controlled stacks with enough controlled flats(capstone) in the mask
            for (int i = 0; i <needFill.length ; i++) {
                int inStack=0;
                for (int piece:map[i][row]) {
                    if(piece==1||piece==3){
                        inStack++;
                    }
                }
                if(inStack>sum) {
                    //// TODO: 10/28/2016
                    //check for movement
                }
            }
        }else{

        }
        return null;

    }

    /**
     *
     * @param x current x cord
     * @param y current y cord
     * @param visitable can not visit things marked as 1
     * @param topLevel can not visit things marked as zero
     * @param row allowed empty row
     * @param veritical vertical/horizontal
     * @param needFill returns the positions in a row that need to be filled to get a road
     * @return
     */
    private Move needFill(int x, int y,
                                 boolean[][] visitable, boolean[][] topLevel,
                                 int row, boolean veritical, boolean[][] needFill){
        Move ret;
        if(y+1<topLevel.length) {
            if (visitable[x][y + 1] == false && (topLevel[x][y + 1] == true || (x == row && !veritical) || (y + 1 == row && veritical))) {
                visitable[x + 1][y] = true;
                visitable[x - 1][y] = true;
                visitable[x][y + 1] = true;
                visitable[x][y - 1] = true;
                needFill[x][y + 1] = true;
                ret=needFill(x, y + 1, visitable, topLevel, row, veritical, needFill);
                if(ret!=null){
                    return ret;
                }
                visitable[x + 1][y] = false;
                visitable[x - 1][y] = false;
                visitable[x][y + 1] = false;
                visitable[x][y - 1] = false;
                needFill[x][y + 1] = false;
            }
        }else{
            ret = getMove(needFill, topLevel, row, veritical);
        }
        if(x+1>needFill.length) {
            if (visitable[x + 1][y] == false && (topLevel[x + 1][y] == true || (x + 1 == row && !veritical) || (y == row && veritical))) {
                visitable[x + 1][y] = true;
                visitable[x - 1][y] = true;
                visitable[x][y + 1] = true;
                visitable[x][y - 1] = true;
                needFill[x + 1][y] = true;
                ret=needFill(x + 1, y, visitable, topLevel, row, veritical, needFill);
                if(ret!=null){
                    return ret;
                }
                visitable[x + 1][y] = false;
                visitable[x - 1][y] = false;
                visitable[x][y + 1] = false;
                visitable[x][y - 1] = false;
                needFill[x + 1][y] = false;
            }
        }
        if(x-1>0) {
            if (visitable[x - 1][y] == false && (topLevel[x - 1][y] == true || (x - 1 == row && !veritical) || (y == row && veritical))) {
                visitable[x + 1][y] = true;
                visitable[x - 1][y] = true;
                visitable[x][y + 1] = true;
                visitable[x][y - 1] = true;
                needFill[x - 1][y] = true;
                ret=needFill(x - 1, y, visitable, topLevel, row, veritical, needFill);
                if(ret!=null){
                    return ret;
                }
                visitable[x + 1][y] = false;
                visitable[x - 1][y] = false;
                visitable[x][y + 1] = false;
                visitable[x][y - 1] = false;
                needFill[x - 1][y] = false;
            }
        }

        if(y-1>0) {
            if (visitable[x][y - 1] == false && (topLevel[x][y - 1] == true || (x == row && !veritical) || (y - 1 == row && veritical))) {
                visitable[x + 1][y] = true;
                visitable[x - 1][y] = true;
                visitable[x][y + 1] = true;
                visitable[x][y - 1] = true;
                needFill[x][y - 1] = true;
                ret=needFill(x, y - 1, visitable, topLevel, row, veritical, needFill);
                if(ret!=null){
                    return ret;
                }
                visitable[x + 1][y] = false;
                visitable[x - 1][y] = false;
                visitable[x][y + 1] = false;
                visitable[x][y - 1] = false;
                needFill[x][y - 1] = false;
            }
        }
        return null;
    }
    public boolean[][] topLevel(){
        boolean topLevel[][]=new boolean[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int temp=map[i][j].get(map[i][j].size());
                if(temp==1 ||temp==3){
                    topLevel[i][j]=true;
                }else{
                    topLevel[i][j]=false;
                }
            }
        }
        return topLevel;
    }
    public int[][][] getAIMap(){
        int AIMap[][][]=new int[map.length][map.length][map.length+1];
        for (int i = 0; i <map.length ; i++) {
            for (int j = 0; j <map.length ; j++) {
                for (int k = 0; k < map.length+1; k++) {
                    if(map[i][j].size()-(map.length+1)+k>0){
                        AIMap[i][j][map.length+1-k]=map[i][j].get(map[i][j].size()-(map.length+1)+k);
                    }else{
                        AIMap[i][j][map.length+1-k]=0;
                    }


                }
            }
        }
        return AIMap;
    }

}
