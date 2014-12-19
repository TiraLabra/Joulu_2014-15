package Interface;

import Logic.Game;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Teemu
 */
public class TextInterface {

    private static Scanner reader = new Scanner(System.in);

    public TextInterface() {
        int size = 4;

//        System.out.println("Board size? (number x number): ");
//        try {
//            size = Integer.parseInt(reader.next());
//        } catch (Exception ex) {
//        }

        Game game = new Game(size);
    }

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

        // SHOW MINMAX VALUES OF CHOICES:

        System.out.println("------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(evaluationBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static int getMove(ArrayList<Integer> unavailableSpots) {
        int move = 0;
        System.out.println("make your move (1-16):");
        while ((move != 1 && move != 2 && move != 3 && move != 4 && move != 5 && move != 6 && move != 7 && move != 8 && move != 9
                && move != 10 && move != 11 && move != 12 && move != 13 && move != 14 && move != 15 && move != 16)
                || unavailableSpots.contains(move)) {
            try {
                move = Integer.parseInt(reader.next());
            } catch (Exception ex) {
            }
        }
        return move;
    }
}
