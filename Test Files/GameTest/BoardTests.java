package GameTest;
import com.company.TaronBot.Game.Board;
import junit.framework.*;


import java.util.LinkedList;

/**
 * Created by sarnowskit on 11/1/2016.
 */
public class BoardTests extends TestCase{
    protected Board board;
    public void setUp(){
        board=null;
    }
    public void testCreate(){
        board =new Board(5,new LinkedList<>(),true);
        for (int i = 0; i <5 ; i++) {
            for (int j = 0; j <5 ; j++) {
                for (int k = 0; k <6 ; k++) {
                    assertTrue(board.getAIMap()[i][j][k]==0);

                }
            }
        }
    }

}
