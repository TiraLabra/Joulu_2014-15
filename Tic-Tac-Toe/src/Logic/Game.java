package Logic;

import Interface.TextInterface;

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
     * @param gameMode the chosen game mode (1 = human vs ai, 2 = ai vs human, 3
     * = ai vs ai, 4 = human vs human)
     */
    public Game(int size, int gameMode) {
        int[][] originalBoard = new int[size][size];
        makeMove(originalBoard, 1, gameMode);
    }

    /**
     * Makes a move on the game board (either given by the user or evaluated as
     * the best possible move by the algorithm)
     *
     * @param board the current game board
     * @param whosTurn which player's turn it is currently (1 or 2)
     * @param gameMode the chosen game mode (1 = human vs ai, 2 = ai vs human, 3
     * = ai vs ai, 4 = human vs human)
     */
    public static void makeMove(int[][] board, int whosTurn, int gameMode) {
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
        int[][] newBoard2 = board;
        int[][] evaluationBoard = new int[board.length][board.length];
        int bestValue = Integer.MIN_VALUE;
        int besti = 0;
        int bestj = 0;
        int[][] unavailableSpots = new int[board.length][board.length];
        boolean winThisTurn = false;
        boolean lossNextTurn = false;

        // check if the center position is empty - if yes, choose that as the next move
        // otherwise check if there's any move that would immediately win the game - if yes, choose that as the next move
        // otherwise check if there's any move that would block a winning move by the opponent during the following turn 
        // - if yes, choose that as the next move
        // otherwise go through all possible moves and calculate the best next move

        if (board[board.length / 2][board.length / 2] != 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (newBoard[i][j] == 0) {
                        newBoard[i][j] = whosTurn;
                        if (checkForVictoryOrLoss(newBoard) == true) {
                            evaluationBoard[i][j] = Integer.MAX_VALUE;
                            winThisTurn = true;
                        }
                        newBoard2 = newBoard;
                        for (int k = 0; k < board.length; k++) {
                            for (int l = 0; l < board.length; l++) {
                                if (newBoard2[k][l] == 0) {
                                    if (whosTurn == 1) {
                                        newBoard2[k][l] = 2;
                                    } else {
                                        newBoard2[k][l] = 1;
                                    }
                                    if (checkForVictoryOrLoss(newBoard2) == true && winThisTurn == false) {
                                        evaluationBoard[i][j] = Integer.MIN_VALUE;
                                        lossNextTurn = true;
                                    }
                                    newBoard2[k][l] = 0;
                                }
                            }
                        }
                        if (whosTurn == 1 && (gameMode == 1 || gameMode == 4)) {
                            // non-AI player's move evaluation disabled for now
                            // evaluationBoard[i][j] = evaluateMove(newBoard, 2, 0);
                        } else if (whosTurn == 2 && (gameMode == 2 || gameMode == 4)) {
                            // non-AI player's move evaluation disabled for now
                            // evaluationBoard[i][j] = evaluateMove(newBoard, 1, 1);
                        } else if (whosTurn == 1 && (gameMode == 2 || gameMode == 3)) {
                            if (winThisTurn == false && lossNextTurn == false) {
                                evaluationBoard[i][j] = evaluateMove(newBoard, 2, 0);
                            }
                        } else if (whosTurn == 2 && (gameMode == 1 || gameMode == 3)) {
                            if (winThisTurn == false && lossNextTurn == false) {
                                evaluationBoard[i][j] = evaluateMove(newBoard, 1, 1);
                            }
                        }
//                        if (evaluationBoard[i][j] == 0) {
//                            evaluationBoard[i][j] = - Math.max(Math.abs(i - board.length / 2), Math.abs(j - board.length / 2));
//                        }
                        newBoard[i][j] = 0;
                    } else {
                        if (newBoard[i][j] == 1 || newBoard[i][j] == 2) {
                            unavailableSpots[i][j] = 1;
                        }
                    }
                }
            }
        } else {
            evaluationBoard[board.length / 2][board.length / 2] = Integer.MAX_VALUE;
        }

        if ((whosTurn == 1 && (gameMode == 2 || gameMode == 3)) || (whosTurn == 2 && (gameMode == 1 || gameMode == 3))) {
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

        if ((whosTurn == 1 && (gameMode == 2 || gameMode == 3)) || (whosTurn == 2 && (gameMode == 1 || gameMode == 3))) {
            TextInterface.showBoardState(board, evaluationBoard, true);
        } else {
            TextInterface.showBoardState(board, evaluationBoard, false);
        }

        // makes the next move (depending on whose turn it is and if the player is a human or AI)

        if ((whosTurn == 1 && (gameMode == 2 || gameMode == 3)) || (whosTurn == 2 && (gameMode == 1 || gameMode == 3))) {
            newBoard[besti][bestj] = whosTurn;
            if (whosTurn == 1) {
                makeMove(newBoard, 2, gameMode);
            } else {
                makeMove(newBoard, 1, gameMode);
            }
        } else {
            String move = TextInterface.getMove(unavailableSpots);
            if (whosTurn == 1) {
                newBoard[Integer.parseInt("" + move.charAt(2)) - 1][Integer.parseInt("" + move.charAt(0)) - 1] = 1;
                makeMove(newBoard, 2, gameMode);
            } else {
                newBoard[Integer.parseInt("" + move.charAt(2)) - 1][Integer.parseInt("" + move.charAt(0)) - 1] = 2;
                makeMove(newBoard, 1, gameMode);
            }
        }
    }

    /**
     * Evaluates the minmax value of a potential move in the current board state
     *
     * @param board the current game board
     * @param whosTurn the player whose next possible moves are being evaluated
     * (1 or 2)
     * @param turnCount how many turns ahead of the current board state the
     * evaluation currently is
     *
     * @return minMaxSum the minmax value of this potential move in the current
     * board state
     */
    public static int evaluateMove(int[][] board, int whosTurn, int turnCount) {

        boolean containsSpace = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    containsSpace = true;
                }
            }
        }

        if (checkForVictoryOrLoss(board) == true) {
            if ((whosTurn == 1 && turnCount % 2 == 0) || (whosTurn == 2 && turnCount % 2 == 1)) {
                // the result is a WIN
                return 10;
            } else {
                // the result is a LOSS
                return -10;
            }
        }

        if (containsSpace == false) {
            // the result is a DRAW
            return 0;
        }

        int count = 0;

        // limits the amount of turns being looked ahead and evaluates the value of the cutoff position

        if (board.length == 5 && turnCount == 5) {
            if ((whosTurn == 1 && turnCount % 2 == 0) || (whosTurn == 2 && turnCount % 2 == 1)) {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] != 0 && (j == 0 || j == 1 || j == 2) && board[i][j] == board[i][j + 1]
                                && board[i][j] == board[i][j + 2]) {
                            count++;
                        }
                        if (board[i][j] != 0 && (i == 0 || i == 1 || i == 2) && board[i][j] == board[i + 1][j]
                                && board[i][j] == board[i + 2][j]) {
                            count++;
                        }
                        if (board[i][j] != 0 && (i == 2 || i == 3 || i == 4) && (j == 0 || j == 1 || j == 2)
                                && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2]) {
                            count++;
                        }
                        if (board[i][j] != 0 && ((i == 0 || i == 1 || i == 2) && (j == 0 || j == 1 || j == 2))
                                && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]) {
                            count++;
                        }
                    }
                }
            } else {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] != 0 && (j == 0 || j == 1 || j == 2) && board[i][j] == board[i][j + 1]
                                && board[i][j] == board[i][j + 2]) {
                            count--;
                        }
                        if (board[i][j] != 0 && (i == 0 || i == 1 || i == 2) && board[i][j] == board[i + 1][j]
                                && board[i][j] == board[i + 2][j]) {
                            count--;
                        }
                        if (board[i][j] != 0 && (i == 2 || i == 3 || i == 4) && (j == 0 || j == 1 || j == 2)
                                && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2]) {
                            count--;
                        }
                        if (board[i][j] != 0 && ((i == 0 || i == 1 || i == 2) && (j == 0 || j == 1 || j == 2))
                                && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]) {
                            count--;
                        }
                    }
                }
            }
            return count;
        } else if (board.length == 7 && turnCount == 3) {
            if ((whosTurn == 1 && turnCount % 2 == 0) || (whosTurn == 2 && turnCount % 2 == 1)) {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] != 0 && (j == 0 || j == 1 || j == 2 || j == 3) && board[i][j] == board[i][j + 1]
                                && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                            count++;
                        }
                        if (board[i][j] != 0 && (i == 0 || i == 1 || i == 2 || i == 3) && board[i][j] == board[i + 1][j]
                                && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                            count++;
                        }
                        if (board[i][j] != 0 && (i == 3 || i == 4 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 2 || j == 3)
                                && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                            count++;
                        }
                        if (board[i][j] != 0 && ((i == 0 || i == 1 || i == 2 || i == 3) && (j == 0 || j == 1 || j == 2) || (i == 2 && j == 2))
                                && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                            count++;
                        }
                    }
                }
            } else {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] != 0 && (j == 0 || j == 1 || j == 2 || j == 3) && board[i][j] == board[i][j + 1]
                                && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                            count--;
                        }
                        if (board[i][j] != 0 && (i == 0 || i == 1 || i == 2 || i == 3) && board[i][j] == board[i + 1][j]
                                && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                            count--;
                        }
                        if (board[i][j] != 0 && (i == 3 || i == 4 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 2 || j == 3)
                                && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                            count--;
                        }
                        if (board[i][j] != 0 && ((i == 0 || i == 1 || i == 2 || i == 3) && (j == 0 || j == 1 || j == 2) || (i == 2 && j == 2))
                                && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                            count--;
                        }
                    }
                }
            }
            return count;
        }

        int[][] newBoard = board;
        int[][] evaluationBoard = new int[board.length][board.length];
        int minMaxSum = 0;

        outerloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (newBoard[i][j] == 0) {
                    if (turnCount % 2 == 0) {
                        newBoard[i][j] = 1;
                    } else {
                        newBoard[i][j] = 2;
                    }
                    if (whosTurn == 1) {
                        minMaxSum = minMaxSum + evaluateMove(newBoard, 1, turnCount + 1);
                    } else {
                        minMaxSum = minMaxSum + evaluateMove(newBoard, 2, turnCount + 1);
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
    public static boolean checkForVictoryOrLoss(int[][] board) {
        if (board.length == 3) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] != 0 && j == 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]) {
                        return true;
                    }
                    if (board[i][j] != 0 && i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]) {
                        return true;
                    }
                    if (board[i][j] != 0 && i == 2 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2]) {
                        return true;
                    }
                    if (board[i][j] != 0 && i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]) {
                        return true;
                    }
                }
            }
        } else if (board.length == 5) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] != 0 && (j == 0 || j == 1) && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]
                            && board[i][j] == board[i][j + 3]) {
                        return true;
                    }
                    if (board[i][j] != 0 && (i == 0 || i == 1) && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]
                            && board[i][j] == board[i + 3][j]) {
                        return true;
                    }
                    if (board[i][j] != 0 && (i == 3 || i == 4) && (j == 0 || j == 1) && board[i][j] == board[i - 1][j + 1]
                            && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                        return true;
                    }
                    if (board[i][j] != 0 && ((i == 0 || i == 1) && (j == 0 || j == 1)) && board[i][j] == board[i + 1][j + 1]
                            && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                        return true;
                    }
                }
            }
        } else if (board.length == 7) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] != 0 && (j == 0 || j == 1 || j == 2) && board[i][j] == board[i][j + 1]
                            && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3] && board[i][j] == board[i][j + 4]) {
                        return true;
                    }
                    if (board[i][j] != 0 && (i == 0 || i == 1 || i == 2) && board[i][j] == board[i + 1][j]
                            && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] == board[i + 4][j]) {
                        return true;
                    }
                    if (board[i][j] != 0 && (i == 4 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 2) && board[i][j] == board[i - 1][j + 1]
                            && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] == board[i - 4][j + 4]) {
                        return true;
                    }
                    if (board[i][j] != 0 && ((i == 0 || i == 1 || i == 2) && (j == 0 || j == 1 || j == 2) || (i == 2 && j == 2)) && board[i][j] == board[i + 1][j + 1]
                            && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] == board[i + 4][j + 4]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
