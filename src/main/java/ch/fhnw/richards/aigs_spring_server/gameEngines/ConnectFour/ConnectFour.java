package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.HashMap;

import ch.fhnw.richards.aigs_spring_server.game.Game;
import ch.fhnw.richards.aigs_spring_server.gameEngines.GameEngine;

// Author: YOUR NAME (required by project rules) :contentReference[oaicite:2]{index=2}
public class ConnectFour implements GameEngine {

    private static final int ROWS = 6;
    private static final int COLS = 7;

    private static final long HUMAN = 1;
    private static final long AI = -1;

    @Override
    public Game newGame(Game game) {
        game.setBoard(new long[ROWS][COLS]);
        game.setResult(false);
        return game;
    }

    @Override
    public Game move(Game game, HashMap<String, String> moveMap) {

        // If already finished, ignore
        if (Boolean.TRUE.equals(game.getResult())) return game;

        // Ensure board exists
        long[][] board = game.getBoard();
        if (board == null || board.length != ROWS || board[0].length != COLS) {
            board = new long[ROWS][COLS];
            game.setBoard(board);
        }

        // Parse move (column)
        Move move = new Move(moveMap);
        if (!move.isValid()) return game; // illegal move ignored :contentReference[oaicite:3]{index=3}

        // Human drop
        if (!ConnectFourLogic.drop(board, move.getCol(), HUMAN)) return game; // column full -> ignore

        // Check end after human
        if (ConnectFourLogic.hasFour(board, HUMAN) || ConnectFourLogic.isDraw(board)) {
            game.setResult(true);
            return game;
        }

        // AI chooses column based on difficulty (>=2 is "smart")
        Player aiPlayer = (game.getDifficulty() <= 1) ? new RandomPlayer() : new SmartPlayer();
        int aiCol = aiPlayer.chooseColumn(board, AI, HUMAN);

        if (aiCol == -1) { // no valid moves
            game.setResult(true);
            return game;
        }

        ConnectFourLogic.drop(board, aiCol, AI);

        // Check end after AI
        if (ConnectFourLogic.hasFour(board, AI) || ConnectFourLogic.isDraw(board)) {
            game.setResult(true);
        }

        return game;
    }
}
