package Logic;

import Interface.TextInterface;
import java.util.ArrayList;

/**
 * The logic class of the program that operates both the moves and the  move evaluations
 *
 * @author Teemu Salminen <teemujsalminen@gmail.com>
 */
public class Game {
    
    /**
     * Creates the game board and initiates the first move
     *
     * @param size the size of the game board as given by the user (3x3, 5x5, 7x7 or 9x9)
     */
    public Game(int size) {
        int[][] originalBoard = new int[size][size];
        makeMove(originalBoard, 1);
    }
    
    /**
     * Makes a move on the game board (either given by the user or evaluated as the best possible move by the algorithm)
     *
     * @param board the current game board
     * @param whosTurn which player's turn it is currently (1 or 2)
     */
    public static void makeMove(int[][] board, int whosTurn) {
        boolean gameOver = false;
        boolean containsSpace = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    containsSpace = true;
                }
                if (j == 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (j == 1 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 1 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 3 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 4 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 1 && j == 1 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
            }
        }

        if (containsSpace == false) {
            // the result is a TIE
            return;
        }

        int[][] newBoard = board;
        int[][] evaluationBoard = new int[board.length][board.length];
        int bestValue = Integer.MIN_VALUE;
        int besti = 0;
        int bestj = 0;
        ArrayList<String> unavailableSpots = new ArrayList<String>();

        if (whosTurn == 2 || whosTurn == 1) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (newBoard[i][j] == 0) {
                        newBoard[i][j] = whosTurn;
                        if (whosTurn == 1) {
                            evaluationBoard[i][j] = evaluateMove(newBoard, 1, 0);
                        } else {
                            evaluationBoard[i][j] = evaluateMove(newBoard, 2, 1);
                        }
                        newBoard[i][j] = 0;
                    } else {
                        if (newBoard[i][j] == 1 || newBoard[i][j] == 2) {
                            unavailableSpots.add(i + " " + j);
                        }
                    }
                }
            }
        }

        if (whosTurn == 2) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (evaluationBoard[i][j] >= bestValue && board[i][j] == 0) {
                        bestValue = evaluationBoard[i][j];
                        besti = i;
                        bestj = j;
                    }
                }
            }
        }

        TextInterface.showBoardState(board, evaluationBoard);

        if (whosTurn == 2) {
            newBoard[besti][bestj] = whosTurn;
            makeMove(newBoard, 1);
        } else {
            String move = TextInterface.getMove(unavailableSpots);
            newBoard[Integer.parseInt("" + move.charAt(0)) - 1][Integer.parseInt("" + move.charAt(2)) - 1] = 1;
            makeMove(newBoard, 2);
        }

    }
    
    /**
     * Evaluates the minmax value of a potential move in the current board state
     *
     * @param board the current game board
     * @param whosTurn the player whose next possible moves are being evaluated (1 or 2)
     * @param count how many turns ahead of the current board state the evaluation currently is
     * 
     * @return minMaxSum the minmax value of this potential move in the current board state
     */
    private static int evaluateMove(int[][] board, int whosTurn, int count) {

        boolean gameOver = false;
        boolean containsSpace = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    containsSpace = true;
                }
                if (j == 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (j == 1 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 1 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 3 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 4 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
                if (i == 1 && j == 1 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
                    if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                        // the result is a LOSS
                        return -1;
                    } else {
                        // the result is a WIN
                        return 1;
                    }
                }
            }
        }

        if (containsSpace == false) {
            // the result is a TIE
            return 0;
        }

        if (count == 5) {
            // heuristics... depending on the sum of evaluation board give 1, 0 or -1?
            // perhaps only evaluate middle/border locations at start and then locations next to already occupied ones..?
            return 0;
        }

        int[][] newBoard = board;
        int[][] evaluationBoard = new int[board.length][board.length];
        int minMaxSum = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (newBoard[i][j] == 0) {
                    if (count % 2 == 0) {
                        newBoard[i][j] = 1;
                    } else {
                        newBoard[i][j] = 2;
                    }
                    if (whosTurn == 1) {
                        minMaxSum = minMaxSum + evaluateMove(newBoard, 1, count + 1);
                    } else {
                        minMaxSum = minMaxSum + evaluateMove(newBoard, 2, count + 1);
                    }
                    evaluationBoard[i][j] = minMaxSum;
                    newBoard[i][j] = 0;
                }
            }
        }

        return minMaxSum;
    }
}
