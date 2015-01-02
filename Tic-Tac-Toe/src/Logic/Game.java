package Logic;

import Interface.TextInterface;
import java.util.ArrayList;

/**
 * The logic class of the program that operates both the moves and the move
 * evaluations
 *
 * @author Teemu Salminen <teemujsalminen@gmail.com>
 */
public class Game {
    /**
     * Creates the game board and initiates the first move
     *
     * @param size the size of the game board as given by the user (3x3, 5x5 or
     * 7x7)
     */
    public Game(int size) {
        int[][] originalBoard = new int[size][size];
        makeMove(originalBoard, 1);
    }

    /**
     * Makes a move on the game board (either given by the user or evaluated as
     * the best possible move by the algorithm)
     *
     * @param board the current game board
     * @param whosTurn which player's turn it is currently (1 or 2)
     */
    public static void makeMove(int[][] board, int whosTurn) {
        boolean gameOver = false;
        boolean containsSpace = false;

        // check if there's available space to make a move

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    containsSpace = true;
                }
            }
        }

        // check if someone won the game during this turn

        if (checkForVictoryOrLoss(board) == true) {
            // the result is a WIN
            if (whosTurn == 1) {
                TextInterface.announceWinner(2);
            } else {
                TextInterface.announceWinner(1);
            }
            return;
        }

        // if there's no more space left to make a move, the game ends in a draw

        if (containsSpace == false) {
            // the result is a DRAW
            TextInterface.announceWinner(0);
            return;
        }

        int[][] newBoard = board;
        int[][] evaluationBoard = new int[board.length][board.length];
        int bestValue = Integer.MIN_VALUE;
        int besti = 0;
        int bestj = 0;
        ArrayList<String> unavailableSpots = new ArrayList<String>();
        
        // check if the center position is empty - if yes, choose that as the next move
        // otherwise check if there's any move that would immediately win the game - if yes, choose that as the next move
        // otherwise go through all possible moves and calculate the best next move (preferences: 1. loss avoidance 2. victory)

        if (board[board.length / 2][board.length / 2] != 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (newBoard[i][j] == 0) {
                        newBoard[i][j] = whosTurn;
                        if (whosTurn == 1) {
                            // non-AI player's move evaluation disabled for now
                            // evaluationBoard[i][j] = evaluateMove(newBoard, 2, 0);
                        } else {
                            // alpha-beta pruning implementation somewhere here?????!!
                            if (checkForVictoryOrLoss(newBoard) == true) {
                                evaluationBoard[i][j] = Integer.MAX_VALUE;
                            } else {
                                evaluationBoard[i][j] = evaluateMove(newBoard, 1, 1);
                            }
                        }
                        newBoard[i][j] = 0;
                    } else {
                        if (newBoard[i][j] == 1 || newBoard[i][j] == 2) {
                            unavailableSpots.add(j + " " + i);
                        }
                    }
                }
            }
        } else {
            evaluationBoard[board.length / 2][board.length / 2] = Integer.MAX_VALUE;
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

        // show the game board (also the evaluation board if it's the AI's turn)

        if (whosTurn == 2) {
            TextInterface.showBoardState(board, evaluationBoard, true);
        } else {
            TextInterface.showBoardState(board, evaluationBoard, false);
        }

        if (whosTurn == 2) {
            newBoard[besti][bestj] = whosTurn;
            makeMove(newBoard, 1);
        } else {
            String move = TextInterface.getMove(unavailableSpots);
            newBoard[Integer.parseInt("" + move.charAt(2)) - 1][Integer.parseInt("" + move.charAt(0)) - 1] = 1;
            makeMove(newBoard, 2);
        }

    }

    /**
     * Evaluates the minmax value of a potential move in the current board state
     *
     * @param board the current game board
     * @param whosTurn the player whose next possible moves are being evaluated
     * (1 or 2)
     * @param count how many turns ahead of the current board state the
     * evaluation currently is
     *
     * @return minMaxSum the minmax value of this potential move in the current
     * board state
     */
    private static int evaluateMove(int[][] board, int whosTurn, int count) {

        boolean gameOver = false;
        boolean containsSpace = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    containsSpace = true;
                }
            }
        }

        if (checkForVictoryOrLoss(board) == true) {
            if ((whosTurn == 1 && count % 2 == 0) || (whosTurn == 2 && count % 2 == 1)) {
                // the result is a WIN
                return 1;
            } else {
                // the result is a LOSS
                return -2;
            }
        }

        if (containsSpace == false) {
            // the result is a DRAW
            return 0;
        }

        if (board.length == 5 && count == 5) {
            // heuristics... alpha-beta pruning in makeMove?????
            return 0;
        } else if (board.length == 7 && count == 4) {
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

    /**
     * Checks if the given board state means that one of the players has already
     * won
     *
     * @param board the temporary game board to be checked
     *
     * @return true if one of the players has already won, otherwise false
     */
    private static boolean checkForVictoryOrLoss(int[][] board) {
        if (board.length == 3) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (j == 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] != 0) {
                        return true;
                    }
                    if (i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] != 0) {
                        return true;
                    }
                    if (i == 2 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] != 0) {
                        return true;
                    }
                    if (i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] != 0) {
                        return true;
                    }
                }
            }
        } else if (board.length == 5) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if ((j == 0 || j == 1) && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                        return true;
                    }
                    if ((i == 0 || i == 1) && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                        return true;
                    }
                    if ((i == 3 || i == 4) && (j == 0 || j == 1) && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                        return true;
                    }
                    if (((i == 0 || i == 1) && (j == 0 || j == 1)) && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
                        return true;
                    }
                }
            }
        } else if (board.length == 7) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if ((j == 0 || j == 1 || j == 2) && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] == board[i][j + 4] && board[i][j] != 0) {
                        return true;
                    }
                    if ((i == 0 || i == 1 || i == 2) && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] == board[i + 4][j] && board[i][j] != 0) {
                        return true;
                    }
                    if ((i == 4 || i == 5 || i == 6) && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] == board[i - 4][j + 4] && board[i][j] != 0) {
                        return true;
                    }
                    if (((i == 0 || i == 1 || i == 2) && (j == 0 || j == 1 || j == 2) || (i == 2 && j == 2)) && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] == board[i + 4][j + 4] && board[i][j] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
