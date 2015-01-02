package Logic;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Teemu
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
    }
}