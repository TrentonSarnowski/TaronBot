package com.company.TaronBot.Game;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.company.TaronBot.Game.Moves.*;
import com.company.TaronBot.Network.TakNetwork;

/**TakNetwork
 * Created by sarnowskit on 10/21/2016.
 */
public class Board {
    private static boolean print=false;
    private List<Integer> map[][];
    int positivePieceRemain=0;
    int negativePieceRemain=0;
    int positiveCapRemain=0;
    int negativeCapRemain=0;
    private static Move firstPlayer,SecondPlayer=null;
    public static int playGame(TakNetwork Player1, TakNetwork Player2, int sideLength){

        Board game=new Board(sideLength, new LinkedList<>(), true);
        boolean check1=false;
        boolean check2=false;
        List<Move> moves;
        moves = Player1.calculate(game.getAIMap(false));
        for (Move m: moves) {
            if(m.checkFeasible(game,false)&&m.getType()==1){

                m.performMove(game,false);
                firstPlayer=m;
                //System.out.println(m.toString());
                break;
            }
        }
        moves = Player2.calculate(game.getAIMap(false));
        for (Move m: moves) {
            if(m.checkFeasible(game,true)&&m.getType()==1){
                m.performMove(game,true);
                SecondPlayer=m;
                //System.out.println(m.toString());
                break;
            }
        }
        int i=1;
        do{
        	//input data needs to be 9x8x8
            System.out.println(i+": "+firstPlayer+" "+SecondPlayer);
            i++;
            moves =Player1.calculate(game.getAIMap(true));
            for (Move m: moves) {
                if(m.checkFeasible(game,true)){
                    if(print) {
                        for (List<Integer> l[] : game.getMap()) {
                            for (List<Integer> li : l) {
                                if (li.isEmpty()) {
                                    System.out.print(0 + " ");
                                } else {
                                    System.out.print(li.get(li.size() - 1) + " ");

                                }
                            }
                            System.out.println();
                        }
                    }

                    if(firstPlayer!=null&&firstPlayer.toString().equals(m.toString())){

                        System.out.println("loop");

                    }
                    firstPlayer=m;
                    m.performMove(game,true);
                    break;
                }
            }
            check1=game.checkVictory(game,true);
            check2 = game.checkVictory(game, false);
            
            //System.out.println("Passed Checks");
            //try {Thread.sleep(20);
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
            if(check1){
                System.out.println(i+": "+firstPlayer);
                return 1;
            }else if(check2){
                System.out.println(i+": "+firstPlayer);
                return -1;
            }
            moves =Player2.calculate(game.getAIMap(true));
            for (Move m: moves) {
                if(m.checkFeasible(game,false)){
                    if(print) {
                        for (List<Integer> l[] : game.getMap()) {
                            for (List<Integer> li : l) {
                                if (li.isEmpty()) {
                                    System.out.print(0 + " ");
                                } else {
                                    System.out.print(li.get(li.size() - 1) + " ");

                                }
                            }
                            System.out.println();
                        }
                    }
                    if(SecondPlayer!=null&&SecondPlayer.toString().equals(m.toString())){

                        System.out.println("loop");

                    }
                    SecondPlayer=m;
                    m.performMove(game,false);
                    break;
                }
            }
            check2= game.checkVictory(game, true);
            check1= game.checkVictory(game, false);
            if(check1){
                System.out.println(i+": "+firstPlayer+" "+SecondPlayer);
                return -1;
            }else if(check2){
                System.out.println(i+": "+firstPlayer+" "+SecondPlayer);

                return 1;
            }
        }while(true);


    }

    public Board(int sideLength, List<Move> boardState, boolean start){
        switch (sideLength){
            case 3:
                positivePieceRemain=10;
                negativePieceRemain=10;
                break;
            case 4:
                positivePieceRemain=15;
                negativePieceRemain=15;
                break;
            case 5:
                positivePieceRemain=21;
                negativePieceRemain=21;
                positiveCapRemain=1;
                negativeCapRemain=1;
                break;
            case 6:
                positivePieceRemain=30;
                negativePieceRemain=30;
                positiveCapRemain=1;
                negativeCapRemain=1;
                break;
            case 7:
                positivePieceRemain=40;
                negativePieceRemain=40;
                positiveCapRemain=2;
                negativeCapRemain=2;
                break;
            case 8:
                positivePieceRemain=50;
                negativePieceRemain=50;
                positiveCapRemain=2;
                negativeCapRemain=2;
                break;
        }
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
            //System.err.println(tryMove(e, ready));
            ready=!ready;
        }
    }
    public boolean checkVictory(Board b, boolean cont){
        boolean topLevel[][]=b.topLevel(cont);
        for (int i = 0; i <topLevel.length ; i++) {
            if(checkVictory(0,i,topLevel,new boolean[topLevel.length][topLevel.length], false)){
                return true;
            };
            if(checkVictory(i,0,topLevel,new boolean[topLevel.length][topLevel.length], true)){
                return true;
            };
        }
        int sign=1;
        if(cont==false){
            sign*=-1;
        }
        //todo
        //todo add check for full board
        boolean full=true;
        for(List<Integer> l[]:b.getMap()){
            for(List<Integer> m:l){
                if(m.isEmpty()){
                    full=false;
                }
            }
        }
        if(positivePieceRemain<=0||negativePieceRemain<=0||full){
            int positiveSum=0;
            int negativeSum=0;
            for(List<Integer> l[]:b.getMap()){
                for(List<Integer> m:l){
                    if(m.size()>0) {
                        if (m.get(m.size() - 1) == 1) {
                            positiveSum++;
                        } else if (m.get(m.size() - 1) == -1) {
                            negativeSum++;
                        }
                    }
                }
            }
            if(cont){
                return positiveSum>negativeSum;
            }else{
                return negativeSum>=positiveSum;
            }
        }

        return false;
    }

    private boolean checkVictory( int x, int y, boolean topLevel[][], boolean saidNo[][], boolean vertical ){
    	/**System.err.println(x+" "+y);
    	for( boolean[] b:saidNo){
    		for(boolean b2:b){
    	    	System.err.print(b2+" ");
    		}
    		System.err.println();
    	}
    	System.err.println();
         **/
        int xActive=0;
        int yActive=0;
        if(vertical){
            yActive=1;
        }else{
            xActive=1;
        }
        if(x<topLevel.length&&y<topLevel.length&&x>=0&&y>=0) {
            if (!topLevel[x][y]||saidNo[x][y]) {
                return false;
            } else if (topLevel.length == x * xActive + y * yActive) {
                return true;
            } else {
                boolean saidNoSendDown[][] = new boolean[saidNo.length][saidNo.length];
                for (int i = 0; i < saidNo.length; i++) {
                    for (int j = 0; j < saidNo.length; j++) {
                        saidNoSendDown[i][j]=saidNo[i][j];

                    }
                }
                if (x > 0) {
                    saidNoSendDown[x - 1][y] = true;
                }
                if (x < saidNo.length-1) {
                    saidNoSendDown[x + 1][y] = true;
                }
                if (y > 0) {
                    saidNoSendDown[x][y - 1] = true;
                }
                if (y < saidNo.length-1) {
                    saidNoSendDown[x][y + 1] = true;
                }

                if (checkVictory(x+1,y,topLevel, saidNoSendDown,vertical)){
                    return true;
                }
                if (checkVictory(x,y+1,topLevel, saidNoSendDown,vertical)){
                    return true;
                }
                if (checkVictory(x-1,y,topLevel, saidNoSendDown,vertical)){
                    return true;
                }
                if (checkVictory(x,y-1,topLevel, saidNoSendDown,vertical)){
                    return true;
                }



            }
        }

        return false;
    }
    public List<Integer>[][] getMap() {
        return map;
    }

    public boolean tryMove(Move e, boolean positive){
        e.performMove(this, positive);

        return false;
    }
    public Move checkForVictory(){
        boolean topLevel[][]=topLevel(true);
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
    private Move getMoves(boolean[][] needFill, boolean[][] topLevel) {
        for (int i=0;i<map.length;i++) {
            for (int j=0;i<map.length;j++) {
                int pickup=0;
                if(topLevel[i][j]) {
                    if (needFill[i][j]) {//gets the depth to pick up
                        for (int k = 0; k < map[i][j].size(); k++) {
                            if (map[i][j].get(k) > 0) {
                                pickup = k;
                            }
                        }

                    }
                }

            }
        }
        return null;
    }
    private Move getDirectionalMove(int x, int y,boolean[][] needFill,boolean vertical, boolean positive, int pickup){
        int xActive;
        int dropcount;
        int dropSum=0;
        int yActive;
        int sign;
        int distance=1;
        if(vertical){
            xActive=0;
            yActive=1;
        }else{
            xActive=1;
            yActive=0;
        }
        if(positive){
            sign=1;
        }else{
            sign=-1;
        }
        List<Integer> leftOnStack=new LinkedList<Integer>();
        for (int i = 1; i <=pickup ; i++) {
            leftOnStack.add(map[x][y].get(map[x][y].size()-i));
        }
        ArrayList<Integer> leftBehind=new ArrayList<>();


            for (int i = 1; i < ((1+sign)*2)*(map.length-(x*xActive+y*yActive)*(1+sign)/2)&&!leftOnStack.isEmpty() ; i++) {
                if(needFill[x+i*xActive*(1+sign)/2][y+i*yActive*(1+sign)/2]){
                    int topLevel=map[x+i*xActive*(1+sign)/2][y+i*yActive*(1+sign)/2].get(map[x+i*xActive*(1+sign)/2][y+i*yActive*(1+sign)/2].size()-1);
                    if(Math.abs(topLevel)==2){
                        //todo check if top of drop=3 {
                        //todo check for need control previous and relevant other conditions}
                        //else{
                        //todo drop all at previous break out conditions met}
                    }if(Math.abs(topLevel)==3){
                        //todo drop all at previous break out condition met

                    }else{
                        //todo leave behind enough to control
                    }
                }else{
                    int topLevel=map[x+i*xActive*(1+sign)/2][y+i*yActive*(1+sign)/2].get(map[x+i*xActive*(1+sign)/2][y+i*yActive*(1+sign)/2].size()-1);
                    if(Math.abs(topLevel)==2){
                        //todo drop at all on previous break out conditions met
                    }if(Math.abs(topLevel)==3) {
                        //todo drop all at previous break out condition met
                    }else{
                        leftBehind.add(1);
                    }
                }

        }
        return new DeStack(x,y,leftBehind,pickup,vertical,positive,0);
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



    public boolean[][] topLevel(boolean control){
        boolean topLevel[][]=new boolean[map.length][map.length];
        int sign=-1;
        if(control){
            sign=1;
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(!map[i][j].isEmpty()){
                    int temp = map[i][j].get(map[i][j].size() - 1);
                    topLevel[i][j] = sign*temp == sign*1 || sign*temp == sign*3;
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

    public int getNegativePieceRemain() {
        return negativePieceRemain;
    }
    public int getPositivePieceRemain(){
        return positivePieceRemain;
    }

    public int getPositiveCapRemain(){
        return positiveCapRemain;
    }
    public int getNegativeCapRemain(){
        return positiveCapRemain;
    }
    public void reducePositiveCapRemain(){
        positiveCapRemain--;
    }
    public void reduceNegativeCapRemain(){
        negativeCapRemain--;
    }
    public void reducePositiveFlatRemain(){
        positivePieceRemain--;
    }
    public void reduceNegativeFlatRemain(){
        negativePieceRemain--;
    }

}
