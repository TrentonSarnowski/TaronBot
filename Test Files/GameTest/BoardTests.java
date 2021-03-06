package GameTest;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.Placement;
import com.company.TaronBot.Game.FastRoadFinder;
import com.company.TaronBot.Network.TakNetwork;
import com.company.TaronBot.Network.generateMoves;
import junit.framework.*;
import org.junit.Assert;
import tech.deef.Tools.StaticGlobals;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by sarnowskit on 11/1/2016.
 */
public class BoardTests extends TestCase {
    protected Board board;

    public void setUp() {
        board = null;
    }

    public void testRoadFinder() {
        int b[] = {
                1,
                1,
                2,
                2,
                2,
        };
        Assert.assertTrue(FastRoadFinder.RoadChecker(b));

    }

    public void testPlayGame() {
        TakNetwork testNetwork;
        int size = 6;
        testNetwork = new TakNetwork(size + 1, size, size, StaticGlobals.DEPTH, 0, 0);
        Random rand = new Random(0);
        testNetwork.randomize(rand);
        long time;
        Board.playGame(testNetwork, testNetwork);

        List<TakNetwork> n = new ArrayList<>();

        time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Board.playGame(testNetwork, testNetwork);
        }

        System.err.println((System.currentTimeMillis() - time) / 100);
        time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Board.playGame(testNetwork, testNetwork);
        }
        System.err.println((System.currentTimeMillis() - time) / 100);


    }

    @org.junit.Test
    public void testCreate() {
        board = new Board(5, new LinkedList<>(), true);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 6; k++) {
                    assertTrue(board.getAIMap(true)[i][j][k] == 0);

                }
            }
        }
    }

    public void testArrayGenerate() {
        for (boolean[] b : generateMoves.getMoves(5)) {
            for (boolean bs : b) {
                System.out.print(bs + "  ");
            }
            System.out.println();
        }
    }

    public void testCreatePlace() {
        List<Move> moves = new LinkedList<>();
        moves.add(new Placement(0, 0, 1, 0));
        moves.add(new Placement(0, 1, 1, 0));
        board = new Board(5, moves, true);
        assertTrue(board.getAIMap(true)[0][0][0] == 1);
        assertTrue(board.getAIMap(true)[0][1][0] == -1);


    }

    public void testPlacementVictory() {
        board = new Board(5, new LinkedList<>(), true);
        for (int i = 1; i < 5; i++) {
            board.getMap()[0][i].add(1);

        }
        //assertTrue(board.checkForVictory()!=null);
    }

    public void testPlacementFalseVictory() {
        board = new Board(5, new LinkedList<>(), true);
        for (int i = 1; i < 5; i++) {
            board.getMap()[0][i].add(1);

        }
        board.getMap()[0][0].add(-1);
        //assertTrue(board.checkForVictory()==null);

    }

    public void testMovementVictory() {
        board = new Board(5, new LinkedList<>(), true);
        for (int i = 1; i < 5; i++) {
            board.getMap()[i][0].add(1);

        }
        board.getMap()[0][0].add(-1);
        board.getMap()[0][1].add(1);
        board.getMap()[1][1].add(-1);

        //assertTrue(board.checkForVictory()!=null);

    }

    public void testVictory() {
        board = new Board(8, new LinkedList<>(), true);

        board.getMap()[0][7].add(-1);
        board.getMap()[1][7].add(-1);
        board.getMap()[2][7].add(-1);
        board.getMap()[2][6].add(-1);
        board.getMap()[3][6].add(-1);
        board.getMap()[4][6].add(-1);
        board.getMap()[4][5].add(-1);
        board.getMap()[4][4].add(-1);
        board.getMap()[5][4].add(-1);
        board.getMap()[6][4].add(-1);
        board.getMap()[6][5].add(-1);
        board.getMap()[7][5].add(-1);
        board.getMap()[0][7].add(-1);


        //*/


        //board.checkForVictory();
        //int i=board.checkRoadWin(board,true,board.topLevel(true));
        //assertTrue(==32);

    }

    public void testVictoryAltDirection() {
        board = new Board(8, new LinkedList<>(), true);

        board.getMap()[7][0].add(1);
        board.getMap()[7][1].add(1);
        board.getMap()[7][2].add(1);
        board.getMap()[6][2].add(1);
        board.getMap()[6][3].add(1);
        board.getMap()[6][4].add(1);
        board.getMap()[5][4].add(1);
        board.getMap()[4][4].add(3);
        board.getMap()[4][5].add(1);
        board.getMap()[4][6].add(1);
        board.getMap()[5][6].add(1);
        board.getMap()[5][7].add(1);


        //*/


        //board.checkForVictory();
        //int i=board.checkRoadWin(board,true,board.topLevel(true));
        //System.err.println(i);
        //assertTrue(i==32);

    }

}
