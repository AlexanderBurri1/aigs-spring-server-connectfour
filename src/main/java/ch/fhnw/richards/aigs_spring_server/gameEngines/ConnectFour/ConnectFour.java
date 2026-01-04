package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.HashMap;

import ch.fhnw.richards.aigs_spring_server.game.Game;
import ch.fhnw.richards.aigs_spring_server.gameEngines.GameEngine;

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

                if (Boolean.TRUE.equals(game.getResult())) return game;

               long[][] board = game.getBoard();
        if (board == null || board.length != ROWS || board[0].length != COLS) {
            board = new long[ROWS][COLS];
            game.setBoard(board);
        }

               Move move = new Move(moveMap);
        if (!move.isValid()) return game;

                if (!ConnectFourLogic.drop(board, move.getCol(), HUMAN)) return game;

               if (ConnectFourLogic.hasFour(board, HUMAN) || ConnectFourLogic.isDraw(board)) {
            game.setResult(true);
            return game;
        }

                Player aiPlayer = (game.getDifficulty() <= 1) ? new RandomPlayer() : new SmartPlayer(5);
        int aiCol = aiPlayer.chooseColumn(board, AI, HUMAN);

        if (aiCol == -1) {
            game.setResult(true);
            return game;
        }

        ConnectFourLogic.drop(board, aiCol, AI);

        if (ConnectFourLogic.hasFour(board, AI) || ConnectFourLogic.isDraw(board)) {
            game.setResult(true);
        }

        return game;
    }
}
