package Logic;

import Interface.TextInterface;
import java.util.ArrayList;

/**
 *
 * @author Teemu
 */
public class Game {

    public Game(int size) {
        int[][] originalBoard = new int[size][size];
        makeMove(originalBoard, 1);
    }

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
                if (i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 3 && j == 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] && board[i][j] != 0) {
                    // the result is a WIN
                    return;
                }
                if (i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
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
        ArrayList<Integer> unavailableSpots = new ArrayList<Integer>();

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
                        if (i == 0 && j == 0) {
                            unavailableSpots.add(1);
                        } else if (i == 0 && j == 1) {
                            unavailableSpots.add(2);
                        } else if (i == 0 && j == 2) {
                            unavailableSpots.add(3);
                        } else if (i == 0 && j == 3) {
                            unavailableSpots.add(4);
                        } else if (i == 1 && j == 0) {
                            unavailableSpots.add(5);
                        } else if (i == 1 && j == 1) {
                            unavailableSpots.add(6);
                        } else if (i == 1 && j == 2) {
                            unavailableSpots.add(7);
                        } else if (i == 1 && j == 3) {
                            unavailableSpots.add(8);
                        } else if (i == 2 && j == 0) {
                            unavailableSpots.add(9);
                        } else if (i == 2 && j == 1) {
                            unavailableSpots.add(10);
                        } else if (i == 2 && j == 2) {
                            unavailableSpots.add(11);
                        } else if (i == 2 && j == 3) {
                            unavailableSpots.add(12);
                        } else if (i == 3 && j == 0) {
                            unavailableSpots.add(13);
                        } else if (i == 3 && j == 1) {
                            unavailableSpots.add(14);
                        } else if (i == 3 && j == 2) {
                            unavailableSpots.add(15);
                        } else if (i == 3 && j == 3) {
                            unavailableSpots.add(16);
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
            int move = TextInterface.getMove(unavailableSpots);
            if (move == 1) {
                newBoard[0][0] = 1;
            } else if (move == 2) {
                newBoard[0][1] = 1;
            } else if (move == 3) {
                newBoard[0][2] = 1;
            } else if (move == 4) {
                newBoard[0][3] = 1;
            } else if (move == 5) {
                newBoard[1][0] = 1;
            } else if (move == 6) {
                newBoard[1][1] = 1;
            } else if (move == 7) {
                newBoard[1][2] = 1;
            } else if (move == 8) {
                newBoard[1][3] = 1;
            } else if (move == 9) {
                newBoard[2][0] = 1;
            } else if (move == 10) {
                newBoard[2][1] = 1;
            } else if (move == 11) {
                newBoard[2][2] = 1;
            } else if (move == 12) {
                newBoard[2][3] = 1;
            } else if (move == 13) {
                newBoard[3][0] = 1;
            } else if (move == 14) {
                newBoard[3][1] = 1;
            } else if (move == 15) {
                newBoard[3][2] = 1;
            } else if (move == 16) {
                newBoard[3][3] = 1;
            }
            makeMove(newBoard, 2);
        }

    }

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
                if (i == 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
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
                if (i == 0 && j == 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0) {
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
