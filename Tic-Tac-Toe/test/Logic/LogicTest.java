package Logic;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class for testing the logic methods of the program
 *
 * @author Teemu Salminen <teemujsalminen@gmail.com>
 */
public class LogicTest {

    public LogicTest() {
    }

    @Test
    public void checkForVictoryOrLossWorks() {
        int[][] board = new int[3][3];
        board[0][0] = 1;
        board[0][1] = 1;
        board[0][2] = 1;
        assertEquals(true, Game.checkForVictoryOrLoss(board));

        int[][] board2 = new int[5][5];
        board2[0][0] = 1;
        board2[0][1] = 1;
        board2[0][2] = 1;
        board2[0][3] = 2;
        assertEquals(false, Game.checkForVictoryOrLoss(board2));

        int[][] board3 = new int[7][7];
        board3[0][0] = 2;
        board3[1][1] = 2;
        board3[2][2] = 2;
        board3[3][3] = 2;
        board3[4][4] = 2;
        assertEquals(true, Game.checkForVictoryOrLoss(board3));
        
        int[][] board4 = new int[5][5];
        board4[1][0] = 1;
        board4[2][1] = 1;
        board4[3][2] = 1;
        board4[4][3] = 1;
        assertEquals(true, Game.checkForVictoryOrLoss(board4));
        
        int[][] board5 = new int[5][5];
        board5[0][1] = 1;
        board5[2][1] = 1;
        board5[3][2] = 1;
        board5[4][3] = 1;
        assertEquals(false, Game.checkForVictoryOrLoss(board5));
    }
}