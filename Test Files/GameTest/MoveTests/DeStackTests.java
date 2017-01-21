package GameTest.MoveTests;
import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Moves.DeStack;
import junit.framework.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sarnowskit on 11/1/2016.
 */
public class DeStackTests extends TestCase {


    public void testCreate()throws Exception{
        Integer[] array={1,1,1,1,0};
        DeStack test = DeStack.DeStack(0,0,array, 4,true,true,1);
        Board testBoard=new Board(5,new LinkedList<>(),true);
        Integer[] array2={1,1,1,1};

        assertFalse(test.checkFeasible(testBoard,true));

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        assertTrue(test.checkFeasible(testBoard,true));

    }

    public void testCreateBoolean() throws Exception {
        boolean[] array = {true, true, false, true, false};
        DeStack test = new DeStack(array, true, true, 1, 0, 0);


    }
    public void testUnEncumberedMoveNorth()throws Exception{
        Integer[] array={1,1,3,2,0};
        DeStack test = DeStack.DeStack(0,0,array, 7,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,-1,1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }

        if(null==test.performMove(testBoard,true)){
            assertTrue(false);
        }
        assertTrue(testBoard.getMap()[0][0].isEmpty());
        assertTrue(testBoard.getMap()[0][1].get(0)==1);
        assertTrue(testBoard.getMap()[0][2].get(0)==-1);
        assertTrue(testBoard.getMap()[0][3].get(0)==1);
        assertTrue(testBoard.getMap()[0][3].get(1)==-1);
        assertTrue(testBoard.getMap()[0][3].get(2)==1);
        assertTrue(testBoard.getMap()[0][4].get(0)==1);
        assertTrue(testBoard.getMap()[0][4].get(1)==3);



    }
    public void testUnEncumberedMoveEast()throws Exception{
        Integer[] array={1,1,3,2,0};
        DeStack test = DeStack.DeStack(0,0,array, 7,false,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,-1,1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }

        if(null==test.performMove(testBoard,true)){
            assertTrue(false);
        }
        assertTrue(testBoard.getMap()[0][0].isEmpty());

        assertTrue(testBoard.getMap()[1][0].get(0)==1);
        assertTrue(testBoard.getMap()[2][0].get(0)==-1);
        assertTrue(testBoard.getMap()[3][0].get(0)==1);
        assertTrue(testBoard.getMap()[3][0].get(1)==-1);
        assertTrue(testBoard.getMap()[3][0].get(2)==1);
        assertTrue(testBoard.getMap()[4][0].get(0)==1);
        assertTrue(testBoard.getMap()[4][0].get(1)==3);



    }
    public void testUnEncumberedMoveWest()throws Exception{
        Integer[] array={1,1,3,2,0};
        DeStack test = DeStack.DeStack(7,0,array, 7,false,false,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,-1,1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[7][0].add(e);

        }

        if(null==test.performMove(testBoard,true)){
            assertTrue(false);
        }
        assertTrue(testBoard.getMap()[7][0].isEmpty());
        assertTrue(testBoard.getMap()[6][0].get(0)==1);
        assertTrue(testBoard.getMap()[5][0].get(0)==-1);
        assertTrue(testBoard.getMap()[4][0].get(0)==1);
        assertTrue(testBoard.getMap()[4][0].get(1)==-1);
        assertTrue(testBoard.getMap()[4][0].get(2)==1);
        assertTrue(testBoard.getMap()[3][0].get(0)==1);
        assertTrue(testBoard.getMap()[3][0].get(1)==3);



    }
    public void testUnEncumberedSouth()throws Exception{
        Integer[] array={1,1,3,2,0};
        DeStack test = DeStack.DeStack(0,7,array, 7,true,false,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,-1,1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][7].add(e);

        }

        if(null==test.performMove(testBoard,true)){
            assertTrue(false);
        }
        //theroeticly should add testing to other areas as well but that is alot of asserts
        assertTrue(testBoard.getMap()[0][7].isEmpty());
        assertTrue(testBoard.getMap()[0][6].get(0)==1);
        assertTrue(testBoard.getMap()[0][5].get(0)==-1);
        assertTrue(testBoard.getMap()[0][4].get(0)==1);
        assertTrue(testBoard.getMap()[0][4].get(1)==-1);
        assertTrue(testBoard.getMap()[0][4].get(2)==1);
        assertTrue(testBoard.getMap()[0][3].get(0)==1);
        assertTrue(testBoard.getMap()[0][3].get(1)==3);



    }
    public void testWallOnEndCapMoveNorth()throws Exception{
        Integer[] array={1,2,1};
        DeStack test = DeStack.DeStack(0,0,array, 4,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        testBoard.getMap()[0][3].add(2);



    }
    public void testWallOnEndMoveNorth()throws Exception{
        Integer[] array={1,2,1};
        DeStack test = DeStack.DeStack(0,0,array, 4,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,1};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        testBoard.getMap()[0][3].add(2);



        if(null==test.performMove(testBoard,true)){
            assertTrue(true);
        }


    }
    public void testWallOnNotEndCapMoveNorth()throws Exception{
        Integer[] array={1,2,1};
        DeStack test = DeStack.DeStack(0,0,array, 4,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        testBoard.getMap()[0][2].add(2);


        if(null==test.performMove(testBoard,true)){
            assertTrue(true);
        }


    }
    public void testCapOnCapMoveNorth()throws Exception{
        Integer[] array={1,2,1};
        DeStack test = DeStack.DeStack(0,0,array, 4,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        testBoard.getMap()[0][3].add(3);


        if(null==test.performMove(testBoard,true)){
            assertTrue(true);
        }


    }
    public void testMoveOntoCap()throws Exception{
        Integer[] array={1};
        DeStack test = DeStack.DeStack(0,0,array, 1,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        testBoard.getMap()[0][1].add(3);


        assertFalse(test.checkFeasible(testBoard,true));





    }
    public void testMoveOccur()throws Exception{
        Integer[] array={1,2,1,2};
        DeStack test = DeStack.DeStack(0,0,array, 6,true,true,1);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        Integer[] array2={1,-1,1,1,1,3};

        for (Integer e: array2) {
            testBoard.getMap()[0][0].add(e);

        }
        //testBoard.getMap()[0][1].add(3);

        test.performMove(testBoard,true);
        System.out.println();

        for (List<Integer> l[]:testBoard.getMap()) {
            for (List<Integer> list:l) {
                if(list.isEmpty()){
                    System.err.print("0 ");
                }else{
                    System.err.print(list.get(list.size()-1)+" ");

                }
            }
            System.err.println();

        }
        assertEquals(testBoard.getMap()[0][1].get(0).intValue(),1);
        assertEquals(testBoard.getMap()[0][2].get(0).intValue(),-1);
        assertEquals(testBoard.getMap()[0][2].get(1).intValue(),1);
        assertEquals(testBoard.getMap()[0][3].get(0).intValue(),1);
        assertEquals(testBoard.getMap()[0][4].get(0).intValue(),1);
        assertEquals(testBoard.getMap()[0][4].get(1).intValue(),3);









    }
}
