package ces.augusto108.minesweeper.exceptions;

public class ExplosionException extends RuntimeException {
    public ExplosionException() {
        System.out.println("Boom! You lose.");
    }
}
