package ces.augusto108.minesweeper.view;

import ces.augusto108.minesweeper.exceptions.ExplosionException;
import ces.augusto108.minesweeper.exceptions.QuitGameException;
import ces.augusto108.minesweeper.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class GameConsole {
    private final Board board;
    private final Scanner scanner = new Scanner(System.in);

    public GameConsole(Board board) {
        this.board = board;

        gameStart();
    }

    private void gameStart() {
        try {
            boolean continueGame = true;

            while (continueGame) {
                play();

                System.out.print("\nStart another match? (Y/n) : ");
                String reply = scanner.nextLine();

                if (reply.equalsIgnoreCase("n")) continueGame = false;
                else board.resetGame();
            }
        } catch (QuitGameException e) {
//            throw new QuitGameException();
            System.out.println("Bye.");
        } finally {
            scanner.close();
        }
    }

    // prints the board and an instruction to enter coordinates or to quit
    private void play() {
        try {
            while (!board.gameWin()) {
                System.out.println("\n" + board);

                String typedCmd1 = getTypedCmd("\nEnter (x, y) or 'quit': ");

                Iterator<Integer> coordinates = Arrays
                        .stream(typedCmd1.split(","))
                        .map(character -> Integer.parseInt(character.trim()))
                        .iterator();

                String typedCmd2 = getTypedCmd("\n1 - Open cell or 2 - (Un)flag cell: ");

                if (typedCmd2.equals("1")) board.openCell(coordinates.next(), coordinates.next());
                else if (typedCmd2.equals("2")) board.toggleFlagged(coordinates.next(), coordinates.next());
            }

            System.out.println(board);
            System.out.println("You win!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You lose.");
        }
    }

    private String getTypedCmd(String text) {
        System.out.print(text);

        String typedIn = scanner.nextLine();

        if (typedIn.equalsIgnoreCase("quit")) throw new QuitGameException();

        return typedIn;
    }
}
