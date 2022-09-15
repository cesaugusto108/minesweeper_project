package ces.augusto108.minesweeper.view;

import ces.augusto108.minesweeper.exceptions.QuitGameException;
import ces.augusto108.minesweeper.model.Board;

import java.util.Scanner;

public class GameConsole {
    private final Board board;

    public GameConsole(Board board) {
        this.board = board;

        gameStart();
    }

    private void gameStart() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueGame = true;

            while (continueGame) {
                System.out.print("Start another match? (Y/n) ");
                String reply = scanner.nextLine();

                if (reply.equalsIgnoreCase("n")) continueGame = false;
                else board.resetGame();
            }
        } catch (QuitGameException e) {
            throw new QuitGameException();
        }
    }
}
