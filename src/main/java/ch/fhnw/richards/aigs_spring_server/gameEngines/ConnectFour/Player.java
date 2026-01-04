package ch.fhnw.richards.aigs_spring_server.gameEngines.ConnectFour;

public interface Player {
    int chooseColumn(long[][] board, long myPiece, long otherPiece);
}
