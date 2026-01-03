package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Author: YOUR NAME
public class RandomPlayer implements Player {

    private final Random rng = new Random();

    @Override
    public int chooseColumn(long[][] board, long myPiece, long otherPiece) {
        List<Integer> valid = new ArrayList<>();
        for (int c = 0; c < 7; c++) {
            if (ConnectFourLogic.canDrop(board, c)) valid.add(c);
        }
        if (valid.isEmpty()) return -1;
        return valid.get(rng.nextInt(valid.size()));
    }
}
