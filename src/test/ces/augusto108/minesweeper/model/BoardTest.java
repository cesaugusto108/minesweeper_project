package ces.augusto108.minesweeper.model;

import ces.augusto108.minesweeper.exceptions.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void createBoard() {
        board = new Board(1, 1, 1);
        System.out.println(board);
    }

    @Test
    void getRows() {
        assertEquals(1, board.getRows());
    }

    @Test
    void getColumns() {
        assertEquals(1, board.getColumns());
    }

    @Test
    void getMines() {
        assertEquals(1, board.getMines());
    }

    @Test
    void testToString() {
        StringBuilder result = new StringBuilder();

        result.append("  ");
        for (int column = 0; column < board.getColumns(); column++) {
            result.append(" ");
            result.append(column + 1);
            result.append(" ");
        }

        result.append("\n");

        int i = 0;
        for (int row = 0; row < board.getRows(); row++) {
            result.append(row + 1);
            result.append(" ");
            for (int column = 0; column < board.getColumns(); column++) {
                result.append(" ");
                result.append(board.getCellList().get(i).toString());
                result.append(" ");
                i++;
            }
            result.append("\n");
        }

        assertEquals(result.toString(), board.toString());
    }

    // whether the player has won the game
    @Test
    void gameWin() {
        board.getCellList().get(0).toggleFlagged();
        assertTrue(board.gameWin());
    }

    // resets the game
    @Test
    void resetGame() {
        board.resetGame();
        assertFalse(board.getCellList().get(0).isOpened());
        assertTrue(board.getCellList().get(0).isMined());
    }

    // opens cell
    @Test
    void openCell() {
        board.getCellList().get(0).unmine();
        board.openCell(0, 0);
        assertTrue(board.getCellList().get(0).isOpened());
    }

    // tests the ExplosionException within the openCell method
    @Test
    void openCellException() {
        final Cell cell = board.getCellList().get(0);
        assertThrows(ExplosionException.class, cell::openCell);
        assertTrue(cell.isOpened());
    }

    // toggles flagged state of cell
    @Test
    void toggleFlagged() {
        board.toggleFlagged(0, 0);
        assertTrue(board.getCellList().get(0).isFlagged());
    }
}