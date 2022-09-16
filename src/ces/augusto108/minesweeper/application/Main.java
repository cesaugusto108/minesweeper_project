package ces.augusto108.minesweeper.application;

import ces.augusto108.minesweeper.model.Board;
import ces.augusto108.minesweeper.view.GameConsole;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(4, 8, 16);
        new GameConsole(board);
    }
}
