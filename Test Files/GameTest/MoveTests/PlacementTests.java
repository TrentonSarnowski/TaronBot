package GameTest.MoveTests;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Moves.DeStack;
import com.company.TaronBot.Game.Moves.Placement;
import junit.framework.*;
import java.util.LinkedList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by sarnowskit on 11/1/2016.
 */
public class PlacementTests extends TestCase{

    public void testUnEncumberedPlacementWall(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,2,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),true);
        assertTrue(testBoard.getMap()[0][0].get(0)==2);
    }
    public void testUnEncumberedPlacementFlat(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,1,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),true);
        assertTrue(testBoard.getMap()[0][0].get(0)==1);
    }
    public void testUnEncumberedPlacementCap(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,3,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),true);
        assertTrue(testBoard.getMap()[0][0].get(0)==3);
    }
    public void testUnEncumberedPlacementEnemyWall(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,2,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),false);
        assertTrue(testBoard.getMap()[0][0].get(0)==-2);
    }
    public void testUnEncumberedPlacementEnemyFlat(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,1,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),false);
        assertTrue(testBoard.getMap()[0][0].get(0)==-1);
    }
    public void testUnEncumberedPlacementEnemyCap(){
        Integer[] array={1,1,3,2,0};
        Placement test = new Placement(0,0,3,0);
        Board testBoard=new Board(8,new LinkedList<>(),true);
        test.performMove(testBoard.getMap(),false);
        assertTrue(testBoard.getMap()[0][0].get(0)==-3);
    }
}
