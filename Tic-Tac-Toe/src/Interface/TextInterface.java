package Interface;

import Logic.Game;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The text interface class for the program
 *
 * @author Teemu Salminen <teemujsalminen@gmail.com>
 */
public class TextInterface {

    private static Scanner reader = new Scanner(System.in);
    private static int size;
    
    /**
     * Creates the game and sets the size of the board
     */
    public TextInterface() {
        size = 5;
        System.out.println("The program runs quite slowly right now - patience, it gets faster after the first turns!");

//        System.out.println("Board size? (number x number): ");
//        try {
//            size = Integer.parseInt(reader.next());
//        } catch (Exception ex) {
//        }

        Game game = new Game(size);
    }
    
    /**
     * Shows the current board state and the evaluated minmax values of each possible move
     *
     * @param board the current game board
     * @param evaluationBoard the evaluated minmax values of each possible move
     */
    public static void showBoardState(int[][] board, int[][] evaluationBoard) {

        // SHOW BOARD STATE:

        System.out.println("------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    System.out.print("X ");
                } else if (board[i][j] == 2) {
                    System.out.print("O ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println("");
        }

        // SHOW MINMAX VALUES OF POTENTIAL MOVES:

        System.out.println("------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(evaluationBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Fetches the next move from the user
     *
     * @param unavailableSpots areas of the board that are already occupied
     *
     * @return move the next move as given by the user
     */
    public static String getMove(ArrayList<String> unavailableSpots) {
        int moveX = 0;
        int moveY = 0;

        System.out.println("make your move (XY position such as '1 3'):");
        while (moveX > size || moveY > size || moveX < 1 || moveY < 1 || unavailableSpots.contains((moveX - 1) + " " + (moveY - 1)))  {
            try {
                moveX = Integer.parseInt(reader.next());
                moveY = Integer.parseInt(reader.next());
            } catch (Exception ex) {
            }
        }
        String move = moveX + " " + moveY;
        return move;
    }
    
//    public static void announceVictory(int player) {
//        System.out.println("Player " + player + " won!!!");
//    }
}
