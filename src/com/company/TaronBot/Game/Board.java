package com.company.TaronBot.Game;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.company.TaronBot.Game.Moves.*;


import com.company.TaronBot.NEAT.FastRoadFinder;
import com.company.TaronBot.Network.TakNetwork;
import com.company.TaronBot.Network.generateMoves;
import tech.deef.Tools.StaticGlobals;

/**
 * TakNetwork
 * Created by sarnowskit on 10/21/2016.
 */
public class Board {
    private static boolean print = false;
    private List<Integer> map[][];
    private int positivePieceRemain = 0;
    private int negativePieceRemain = 0;
    private int positiveCapRemain = 0;
    private int negativeCapRemain = 0;
    private boolean start;
    private static Move firstPlayer, SecondPlayer = null;
    public int boardNumber;
    public int filledSquares=0;
    public static List<DeStack> moves = generateMoves.getDestacks(8);
    private static PerformOnMove b=new PerformOnMove() {
        @Override
        public void performOnMove(TakNetwork n, Move m, int depth, Board b) {

            switch (m.getType()){
                case 0:
                    StaticGlobals.destackCount++;
                    break;
                case 1:
                    StaticGlobals.flatCount++;
                    break;
                case 2:
                    StaticGlobals.wallCount++;
                    break;
                case 3:
                    StaticGlobals.capCount++;
                    break;
                default:
                    break;
            }
            StaticGlobals.moveCount++;
        }
    };
    private static List<Move> emptyList=new ArrayList<>();
    public static int playGame(TakNetwork Player1, TakNetwork Player2){
        return playGame(Player1, Player2, b);
    }
    public static Integer number=1;
    public static int playGame(TakNetwork Player1, TakNetwork Player2, PerformOnMove Operator){
        Board game =new Board(Player1.getWidth(), emptyList, true);
        List<Move>  moves =Player1.calculate(game.getAIMap(false), game);
        boolean moveMade=true;
        for (int i = moves.size()-1 ; i >0  ; i--) {
            Move m=moves.get(i);
            if(m.getType()==1&&m.checkFeasible(game, true)) {
                m.performMove(game, false);

                break;
            }        }
        moves =Player2.calculate(game.getAIMap(true), game);
        for (int i = moves.size()-1 ; i >0  ; i--) {

            Move m=moves.get(i);
            if(m.getType()==1&&m.checkFeasible(game, true)) {
                m.performMove(game, true);
                break;
            }
        }
        boolean control=false;
        int i=0;
        int turnCount=0;
        Move m1 = new Placement("");
        Move m2 = new Placement("");
        Move n1 = new Placement("");
        Move n2 = new Placement("");
        game: while(!game.checkGameOver()&&moveMade&&turnCount<1000
                &&
                !(m1.looped(m2)&&n1.looped(n2))
                ){
            moveMade=false;
            moves = Player1.calculate(game.getAIMap(true), game);
            i=0;
            for (Move m:moves) {

                if(m.checkFeasible(game, true)) {
                    m.performMove(game, true);
                    Operator.performOnMove(Player1,m,i,game);
                    moveMade=true;
                    m2=m1;
                    m1=m;
                    break;
                }
                i++;
            }
            if(game.checkGameOver()||!moveMade){
                control=true;
                break game;
            }
            moveMade=false;
            moves = Player2.calculate(game.getAIMap(false), game);
            i=0;
            for (Move m:moves) {
                if(m.checkFeasible(game, false)) {
                    m.performMove(game, false);
                    Operator.performOnMove(Player2,m,i,game);
                    moveMade=true;
                    n2=n1;
                    n1=m;
                    break;
                }
                i++;
            }
            ++turnCount;
        }
        return game.victoryValue(control);

    }

    public boolean control() {
        return start;
    }

    public static Board PlayTakBoard(int sideLength, List<Move> boardState, boolean start, int boardID) {
        Board rv = new Board(sideLength, boardState, start);
        rv.boardNumber = boardID;
        return rv;
    }

    public Board(int sideLength, List<Move> boardState, boolean start) {
        this.start = start;
        switch (sideLength) {
            case 3:
                positivePieceRemain = 10;
                negativePieceRemain = 10;
                break;
            case 4:
                positivePieceRemain = 15;
                negativePieceRemain = 15;
                break;
            case 5:
                positivePieceRemain = 21;
                negativePieceRemain = 21;
                positiveCapRemain = 1;
                negativeCapRemain = 1;
                break;
            case 6:
                positivePieceRemain = 30;
                negativePieceRemain = 30;
                positiveCapRemain = 1;
                negativeCapRemain = 1;
                break;
            case 7:
                positivePieceRemain = 40;
                negativePieceRemain = 40;
                positiveCapRemain = 2;
                negativeCapRemain = 2;
                break;
            case 8:
                positivePieceRemain = 50;
                negativePieceRemain = 50;
                positiveCapRemain = 2;
                negativeCapRemain = 2;
                break;
        }

        map = new List[sideLength][sideLength];

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        boolean ready = start;
        for (Move e : boardState) {
            //System.err.println(tryMove(e, ready));
            ready = !ready;
        }
    }

    public int checkVictory(boolean cont) {
        if (FastRoadFinder.RoadChecker(topLevelBoard(!cont))) return -32;

        if (FastRoadFinder.RoadChecker(topLevelBoard(cont))) return 32;

        //todo
        //todo add check for full board
        boolean full = true;
        for (List<Integer> l[] : this.getMap()) {
            for (List<Integer> m : l) {
                if (m.isEmpty()) {
                    full = false;
                }
            }
        }
        if (positivePieceRemain <= 0 || negativePieceRemain <= 0 || full) {
            int positiveSum = 0;
            int negativeSum = 0;
            for (List<Integer> l[] : this.getMap()) {
                for (List<Integer> m : l) {
                    if (m.size() > 0) {
                        if (m.get(m.size() - 1) == 1) {
                            positiveSum++;
                        } else if (m.get(m.size() - 1) == -1) {
                            negativeSum++;
                        }
                    }
                }
            }
            if (cont) {

                return positiveSum - negativeSum;

            } else {

            }
        }

        return 500;
    }

    public boolean checkForRoad(boolean[][] topLevel) {
        for (int i = 0; i < topLevel.length; i++) {
            if (checkForRoad(i, 0, new boolean[topLevel.length][topLevel.length], new boolean[topLevel.length][topLevel.length], topLevel, true)) {
                return true;
            }
            if (checkForRoad(0, i, new boolean[topLevel.length][topLevel.length], new boolean[topLevel.length][topLevel.length], topLevel, false)) {
                return true;
            }

        }
        return false;
    }

    private boolean checkForRoad(int x, int y, boolean[][] triedPrevious, boolean[][] tried, boolean[][] topLevel, boolean vertical) {
        if (x >= tried.length || y >= tried.length || x < 0 || y < 0) {
            return false;
        }
        if (triedPrevious[x][y] || (!topLevel[x][y])) {
            return false;
        }
        if ((vertical && (y == tried.length - 1)) || (!vertical && (x == tried.length - 1))) {
            return true;
        }
        boolean sendTried[][] = new boolean[tried.length][tried.length];
        for (int i = 0; i < tried.length; i++) {
            for (int j = 0; j < tried.length; j++) {
                sendTried[i][j] = tried[i][j];
            }
        }
        if (x > 0) {
            sendTried[x - 1][y] = true;
        }
        if (x < tried.length - 1) {
            sendTried[x + 1][y] = true;
        }
        if (y > 0) {
            sendTried[x][y - 1] = true;
        }
        if (y < tried.length - 1) {
            sendTried[x][y + 1] = true;
        }
        //*/
        sendTried[x][y] = true;
        return checkForRoad(x + 1, y, tried, sendTried, topLevel, vertical) ||
                checkForRoad(x - 1, y, tried, sendTried, topLevel, vertical) ||
                checkForRoad(x, y + 1, tried, sendTried, topLevel, vertical) ||
                checkForRoad(x, y - 1, tried, sendTried, topLevel, vertical);

    }

    public int[] topLevelBoard(boolean b) {
        List<Integer> l[] = null;
        int[] ret = new int[map.length];
        int altmultiply= 1024;

        for (int i = 0; i < map.length; i++) {
            l = map[i];
            int multiply = 1;
            if (b) {
                for (int j=0;j<map.length;j++) {
                    List<Integer> ls=l[j];
                    if (!ls.isEmpty() && ls.get(ls.size() - 1) > 0 && ls.get(ls.size() - 1) != 2) {
                        ret[i]=ret[i]|multiply;
                        ret[j]=ret[j]|altmultiply;
                    }
                    multiply=multiply << 1;
                }

            } else {
                for (int j=0;j<map.length;j++) {
                    List<Integer> ls=l[j];
                    if (!ls.isEmpty() && ls.get(ls.size() - 1) < 0 && ls.get(ls.size() - 1) != -2) {
                        ret[i]=ret[i]|multiply;
                        ret[j]=ret[j]|altmultiply;

                    }
                    multiply=multiply << 1;

                }
            }
        altmultiply=altmultiply<<1;
        }
        return ret;
    }
    public List<Integer>[][] getMap() {
        return map;
    }

    public Board deepCopy(Board b) {
        Board ret = new Board(b.getMap().length, new LinkedList<>(), b.start);
        List<Integer> value[][] = b.getMap();
        List<Integer> retmap[][] = ret.getMap();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value.length; j++) {
                for (int k : value[i][j]) {
                    retmap[i][j].add(k);
                }
            }
        }
        return ret;
    }

    public boolean[][] topLevel(boolean control) {
        boolean topLevel[][] = new boolean[map.length][map.length];
        int sign = -1;
        if (control) {
            sign = 1;
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (!map[i][j].isEmpty()) {
                    int temp = map[i][j].get(map[i][j].size() - 1);
                    if (temp == sign * 3 || temp == sign * 1) {
                        topLevel[i][j] = true;
                    }

                }
            }
        }
        return topLevel;
    }

    public int[][][] getAIMap(boolean control) {
        int sign = -1;

        if (control) {
            sign = 1;
        }
        int AIMap[][][] = new int[map.length][map.length][map.length + 1];


        for (int i = 0; i < map.length; i++) {


            for (int j = 0; j < map.length; j++) {


                for (int k = 0; k <map.length +1 ; k++) {

                    if (map[i][j].size() - 1 >= k) {

                        AIMap[i][j][k] = sign * map[i][j].get(map[i][j].size()-(k+1));
                    }


                }
            }
        }
        return AIMap;
    }


    public int getNegativePieceRemain() {
        return negativePieceRemain;
    }

    public int getPositivePieceRemain() {
        return positivePieceRemain;
    }

    public int getPositiveCapRemain() {
        return positiveCapRemain;
    }

    public int getNegativeCapRemain() {
        return negativeCapRemain;
    }

    public void reducePositiveCapRemain() {
        positiveCapRemain -= 1;
    }

    public void reduceNegativeCapRemain() {
        negativeCapRemain = negativeCapRemain - 1;
    }

    public void reducePositiveFlatRemain() {
        positivePieceRemain--;
    }

    public void reduceNegativeFlatRemain() {
        negativePieceRemain--;
    }

    /**
     * this is a game over calculator that is fairly efficient the order of checks is such that the quickest is done first and slowest last
     * @return
     */
    public boolean checkGameOver(){


        if(this.positivePieceRemain<=0||this.negativePieceRemain<=0){

            return true;

        }
        /*
        boolean broke=false;
        broken: for(List<Integer>[] l:map){
            for(List<Integer> ls:l){
                if(ls.isEmpty()){
                    broke=true;
                    break broken;
                }
            }
        }
        //*/
        if(filledSquares==map.length*map.length){

            return true;
        }
        if(FastRoadFinder.RoadChecker(topLevelBoard())){

            return true;
        }
        return false;
    }

    /**
     * Tournament rules are: board size for a road flat difference for flats
     * @param b weather the first player was in control or not
     * @return the weight of the win as done by tournament style rules
     */
    public int victoryValue(boolean b){
        if(FastRoadFinder.RoadChecker(topLevelBoard(b))){
            StaticGlobals.roadCount++;
            return map.length;
        }else if(FastRoadFinder.RoadChecker(topLevelBoard(!b))){
            StaticGlobals.roadCount++;
            return -map.length;
        }
        int flatCount1=0;
        int flatCount2=0;
        int check;
       for(List<Integer>[] l:map){
            for(List<Integer> ls:l){
               if((!ls.isEmpty()&&ls.get(0)!=0)){
                   check=ls.get(ls.size()-1);
                   if(check==1){
                       flatCount1++;
                   }else if(check==-1){
                       flatCount2++;
                   }
               }
            }
        }
        if(b){
            return flatCount1-flatCount2;
        }else{
            return flatCount2-flatCount1;
        }

    }
    public long[] topLevelBoard() {
        List<Integer> l[] = null;
        long[] ret = new long[map.length];
        long altmultiply= 1024;
        long farMultiply= 1048576;
        for (int i = 0; i < map.length; i++) {
            l = map[i];
            long multiply = 1;
            long farthermultiply=1073741824;
                for (int j=0;j<map.length;j++) {
                    List<Integer> ls=l[j];
                    if(!ls.isEmpty()) {
                        int checkValue= ls.get(ls.size() - 1);
                        if ( checkValue > 0 && checkValue != 2) {
                            ret[i] = ret[i] | multiply;
                            ret[j] = ret[j] | altmultiply;
                        }else if(checkValue < 0 && checkValue != -2){
                            ret[i] = ret[i] | farMultiply;
                            ret[j] = ret[j] | farthermultiply;
                        }

                    }
                    multiply = multiply << 1;
                    farthermultiply = farthermultiply << 1;
                }
            farMultiply=farMultiply<<1;
            altmultiply=altmultiply<<1;
        }
        return ret;
    }

}
