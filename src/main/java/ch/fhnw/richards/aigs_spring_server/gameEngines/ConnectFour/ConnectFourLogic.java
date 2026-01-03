package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

// Author: YOUR NAME
public class ConnectFourLogic {

    private static final int ROWS = 6;
    private static final int COLS = 7;

    public static boolean canDrop(long[][] board, int col) {
        return col >= 0 && col < COLS && board[0][col] == 0;
    }

    // Drop piece in column. Returns true if placed, false if column full/invalid.
    public static boolean drop(long[][] board, int col, long piece) {
        if (col < 0 || col >= COLS) return false;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (board[r][col] == 0) {
                board[r][col] = piece;
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw(long[][] board) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c] == 0) return false;
        }
        return true;
    }

    public static long[][] copy(long[][] board) {
        long[][] copy = new long[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, COLS);
        }
        return copy;
    }

    public static boolean hasFour(long[][] b, long p) {
        // Horizontal
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (b[r][c] == p && b[r][c+1] == p && b[r][c+2] == p && b[r][c+3] == p) return true;
            }
        }
        // Vertical
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r <= ROWS - 4; r++) {
                if (b[r][c] == p && b[r+1][c] == p && b[r+2][c] == p && b[r+3][c] == p) return true;
            }
        }
        // Diagonal down-right
        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (b[r][c] == p && b[r+1][c+1] == p && b[r+2][c+2] == p && b[r+3][c+3] == p) return true;
            }
        }
        // Diagonal up-right
        for (int r = 3; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (b[r][c] == p && b[r-1][c+1] == p && b[r-2][c+2] == p && b[r-3][c+3] == p) return true;
            }
        }
        return false;
    }
}
