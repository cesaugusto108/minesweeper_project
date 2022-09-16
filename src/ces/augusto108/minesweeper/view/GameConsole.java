package ces.augusto108.minesweeper.view;

import ces.augusto108.minesweeper.exceptions.ExplosionException;
import ces.augusto108.minesweeper.exceptions.QuitGameException;
import ces.augusto108.minesweeper.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class GameConsole {
    private final Board board;

    public GameConsole(Board board) {
        this.board = board;

        gameStart();
    }

    private void gameStart() {
        try (Scanner scanner = new Scanner(System.in)) {
            play(scanner);

            boolean continueGame = true;

            while (continueGame) {
                System.out.print("\nStart another match? (Y/n) ");
                String reply = scanner.nextLine();

                if (reply.equalsIgnoreCase("n")) continueGame = false;
                else board.resetGame();
            }
        } catch (QuitGameException e) {
            throw new QuitGameException();
        }
    }

    // prints the board and an instruction to enter coordinates or to quit
    private void play(Scanner scanner) {
        try {
            while (!board.gameWin()) {
                System.out.println(board);

                String typedCmd = getTypedCmd(scanner, "\nEnter (x, y) or 'quit': ");

                Iterator<Integer> coordinates = Arrays
                        .stream(typedCmd.split(","))
                        .map(character -> Integer.parseInt(character.trim()))
                        .iterator();

                typedCmd = getTypedCmd(scanner, "\n1 - Open cell; 2 - (Un)flag cell: ");

                if (typedCmd.equals("1")) board.openCell(coordinates.next(), coordinates.next());
                else if (typedCmd.equals("2")) board.toggleFlagged(coordinates.next(), coordinates.next());
            }

            System.out.println("You win!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You lose.");
        }
    }

    private String getTypedCmd(Scanner scanner, String text) {
        System.out.print(text);

        String typedIn = scanner.nextLine();

        if (typedIn.equalsIgnoreCase("quit")) throw new QuitGameException();

        return typedIn;
    }
}
