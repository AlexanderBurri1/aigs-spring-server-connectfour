package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.ArrayList;
import java.util.List;

// Author: YOUR NAME
public class SmartPlayer implements Player {

    @Override
    public int chooseColumn(long[][] board, long myPiece, long otherPiece) {

        List<Integer> valid = new ArrayList<>();
        for (int c = 0; c < 7; c++) {
            if (ConnectFourLogic.canDrop(board, c)) valid.add(c);
        }
        if (valid.isEmpty()) return -1;

        // 1) Win if possible
        for (int c : valid) {
            long[][] copy = ConnectFourLogic.copy(board);
            ConnectFourLogic.drop(copy, c, myPiece);
            if (ConnectFourLogic.hasFour(copy, myPiece)) return c;
        }

        // 2) Block opponent win
        for (int c : valid) {
            long[][] copy = ConnectFourLogic.copy(board);
            ConnectFourLogic.drop(copy, c, otherPiece);
            if (ConnectFourLogic.hasFour(copy, otherPiece)) return c;
        }

        // 3) Prefer center columns
        int[] pref = {3, 2, 4, 1, 5, 0, 6};
        for (int c : pref) {
            if (valid.contains(c)) return c;
        }

        return valid.get(0);
    }
}
