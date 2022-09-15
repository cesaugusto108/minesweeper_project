package ces.augusto108.minesweeper.exceptions;

public class QuitGameException extends RuntimeException {
    public QuitGameException() {
        System.out.println("Bye.");
    }
}
