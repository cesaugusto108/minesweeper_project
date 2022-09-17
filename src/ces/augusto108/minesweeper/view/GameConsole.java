package ces.augusto108.minesweeper.view;

import ces.augusto108.minesweeper.exceptions.ExplosionException;
import ces.augusto108.minesweeper.exceptions.QuitGameException;
import ces.augusto108.minesweeper.model.Board;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class GameConsole {
    private final Scanner scanner = new Scanner(System.in);

    public GameConsole() {
        gameStart();
    }

    // starts and restarts game
    private void gameStart() {
        try {
            boolean continueGame = true;

            while (continueGame) {
                Board board = play();

                System.out.print("\nStart another match? (Y/n) : ");
                String reply = scanner.nextLine();

                if (reply.equalsIgnoreCase("n")) continueGame = false;
                else board.resetGame();
            }
        } catch (QuitGameException e) {
            System.out.println("Bye.");
        } finally {
            scanner.close();
        }
    }

    // prints the board and an instruction to enter coordinates or to quit
    private Board play() {
        Integer[] gameOptions = chooseGameOptions();

        Board board = new Board(gameOptions[0], gameOptions[1], gameOptions[2]);

        try {
            while (!board.gameWin()) {
                System.out.println("\nDifficulty level: " + gameOptions[3] + "\n\n" + board);

                String typedCmd1 = getTypedCmd("\nEnter 'row number, column number' or 'quit': ");

                Iterator<Integer> coordinates = Arrays
                        .stream(typedCmd1.split(","))
                        .map(character -> Integer.parseInt(character.trim()))
                        .iterator();

                String typedCmd2 = getTypedCmd("\n1 - Open cell or 2 - (Un)flag cell: ");

                if (typedCmd2.equals("1")) {
                    board.openCell(coordinates.next() - 1, coordinates.next() - 1);
                } else if (typedCmd2.equals("2")) {
                    board.toggleFlagged(coordinates.next() - 1, coordinates.next() - 1);
                }
            }

            System.out.println(board);
            System.out.println("You win!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You lose.");
        }

        return board;
    }

    // choice of game options such as number of rows and columns
    private Integer[] chooseGameOptions() {
        System.out.println("\nChoose the size of the game board:");

        Integer[] options;
        try {
            System.out.print("Enter the number of rows (minimum of 4 / max of 9): ");
            int rows = scanner.nextInt();
            if (rows < 4) rows = 4;
            else if (rows > 9) rows = 9;

            System.out.print("Enter the number of columns (minimum of 4 / max of 9): ");
            int columns = scanner.nextInt();
            if (columns < 4) columns = 4;
            else if (columns > 9) columns = 9;

            int mines = 0;

            int difficultyLevel = chooseDifficultyLevel();

            if (difficultyLevel == 1) mines = (int) Math.floor((rows * columns) * 0.1);
            else if (difficultyLevel == 2) mines = (int) Math.floor((rows * columns) * 0.2);
            else if (difficultyLevel == 3) mines = (int) Math.floor((rows * columns) * 0.4);

            options = new Integer[4];
            options[0] = rows;
            options[1] = columns;
            options[2] = mines;
            options[3] = difficultyLevel;
        } catch (InputMismatchException e) {
            System.out.println(
                    "\nYour input is invalid. Default values were chosen for board size and game difficulty level."
            );

            options = new Integer[4];
            options[0] = 6;
            options[1] = 6;
            options[2] = (int) Math.floor((options[0] * options[1]) * 0.4);
            options[3] = 1;

            return options;
        }

        return options;
    }

    // player chooses the difficulty level
    private int chooseDifficultyLevel() {
        System.out.print("\nChoose the game difficulty level: 1 - Easy, 2 - Medium, 3 - Hard: ");
        int difficultyLevel = scanner.nextInt();
        scanner.nextLine();
        if (difficultyLevel < 1 || difficultyLevel > 3) difficultyLevel = 3;

        return difficultyLevel;
    }

    // gets commands from user, including a command to quit
    private String getTypedCmd(String text) {
        System.out.print(text);

        String typedIn = scanner.nextLine();

        if (typedIn.equalsIgnoreCase("quit")) throw new QuitGameException();

        return typedIn;
    }
}
