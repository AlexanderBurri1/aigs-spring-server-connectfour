package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmartPlayer implements Player {

    private static final int ROWS = 6;
    private static final int COLS = 7;

    private static final int WIN_SCORE = 1_000_000;

    private final int maxDepth;
    private final Random rng = new Random();

    public SmartPlayer(int maxDepth) {
        this.maxDepth = Math.max(1, maxDepth);
    }

    @Override
    public int chooseColumn(long[][] board, long myPiece, long otherPiece) {
        List<Integer> valid = validColumns(board);
        if (valid.isEmpty()) return -1;


        int[] pref = {3, 2, 4, 1, 5, 0, 6};

        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestCols = new ArrayList<>();

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int col : pref) {
            if (!valid.contains(col)) continue;

            long[][] next = ConnectFourLogic.copy(board);
            ConnectFourLogic.drop(next, col, myPiece);

            int score = minimax(next, maxDepth - 1, false, myPiece, otherPiece, alpha, beta);

            if (score > bestScore) {
                bestScore = score;
                bestCols.clear();
                bestCols.add(col);
            } else if (score == bestScore) {
                bestCols.add(col);
            }

            alpha = Math.max(alpha, bestScore);
        }

        return bestCols.get(rng.nextInt(bestCols.size()));
    }

    private int minimax(long[][] board, int depth, boolean maximizing,
                        long myPiece, long otherPiece,
                        int alpha, int beta) {

        if (ConnectFourLogic.hasFour(board, myPiece)) return WIN_SCORE + depth;
        if (ConnectFourLogic.hasFour(board, otherPiece)) return -WIN_SCORE - depth;

        List<Integer> valid = validColumns(board);
        if (valid.isEmpty()) return 0; // draw
        if (depth == 0) return evaluate(board, myPiece, otherPiece);

        int[] pref = {3, 2, 4, 1, 5, 0, 6};

        if (maximizing) {
            int best = Integer.MIN_VALUE;

            for (int col : pref) {
                if (!valid.contains(col)) continue;

                long[][] next = ConnectFourLogic.copy(board);
                ConnectFourLogic.drop(next, col, myPiece);

                int score = minimax(next, depth - 1, false, myPiece, otherPiece, alpha, beta);
                best = Math.max(best, score);

                alpha = Math.max(alpha, best);
                if (alpha >= beta) break; // prune
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int col : pref) {
                if (!valid.contains(col)) continue;

                long[][] next = ConnectFourLogic.copy(board);
                ConnectFourLogic.drop(next, col, otherPiece);

                int score = minimax(next, depth - 1, true, myPiece, otherPiece, alpha, beta);
                best = Math.min(best, score);

                beta = Math.min(beta, best);
                if (alpha >= beta) break; // prune
            }
            return best;
        }
    }


    private int evaluate(long[][] b, long my, long opp) {
        int score = 0;

        int centerCol = 3;
        int myCenter = 0;
        int oppCenter = 0;
        for (int r = 0; r < ROWS; r++) {
            if (b[r][centerCol] == my) myCenter++;
            else if (b[r][centerCol] == opp) oppCenter++;
        }
        score += (myCenter - oppCenter) * 6;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                score += windowScore(b[r][c], b[r][c+1], b[r][c+2], b[r][c+3], my, opp);
            }
        }

        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r <= ROWS - 4; r++) {
                score += windowScore(b[r][c], b[r+1][c], b[r+2][c], b[r+3][c], my, opp);
            }
        }

        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                score += windowScore(b[r][c], b[r+1][c+1], b[r+2][c+2], b[r+3][c+3], my, opp);
            }
        }

        for (int r = 3; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                score += windowScore(b[r][c], b[r-1][c+1], b[r-2][c+2], b[r-3][c+3], my, opp);
            }
        }

        return score;
    }

    private int windowScore(long a, long b, long c, long d, long my, long opp) {
        long[] w = {a, b, c, d};
        int myCount = 0, oppCount = 0, empty = 0;

        for (long v : w) {
            if (v == my) myCount++;
            else if (v == opp) oppCount++;
            else empty++;
        }


        if (myCount > 0 && oppCount > 0) return 0;


        if (myCount == 4) return 100000;
        if (oppCount == 4) return -100000;

        if (myCount == 3 && empty == 1) return 140;
        if (myCount == 2 && empty == 2) return 25;
        if (myCount == 1 && empty == 3) return 4;

        if (oppCount == 3 && empty == 1) return -170;
        if (oppCount == 2 && empty == 2) return -30;
        if (oppCount == 1 && empty == 3) return -4;

        return 0;
    }


    private List<Integer> validColumns(long[][] board) {
        List<Integer> valid = new ArrayList<>();
        for (int c = 0; c < COLS; c++) {
            if (ConnectFourLogic.canDrop(board, c)) valid.add(c);
        }
        return valid;
    }
}
