package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

import java.util.HashMap;

// Author: YOUR NAME
public class Move {
    private final int col;
    private final boolean valid;

    public Move(HashMap<String, String> map) {
        int tmpCol = -1;
        boolean ok = false;

        try {
            tmpCol = Integer.parseInt(map.get("col"));
            ok = (tmpCol >= 0 && tmpCol <= 6);
        } catch (Exception e) {
            ok = false;
        }

        this.col = tmpCol;
        this.valid = ok;
    }

    public int getCol() {
        return col;
    }

    public boolean isValid() {
        return valid;
    }
}
