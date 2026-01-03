package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

// Author: YOUR NAME
public interface Player {
    // Return chosen column 0..6, or -1 if no valid move
    int chooseColumn(long[][] board, long myPiece, long otherPiece);
}
