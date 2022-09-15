package ces.augusto108.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void createBoard() {
        board = new Board(1, 1, 1);
    }

    @Test
    void getRows() {
        assertEquals(board.getRows(), 1);
    }

    @Test
    void getColumns() {
        assertEquals(board.getColumns(), 1);
    }

    @Test
    void getMines() {
        assertEquals(board.getMines(), 1);
    }

    @Test
    void testToString() {
        String result = " " +
                board.getCellList().get(0) +
                " " +
                "\n";

        assertEquals(board.toString(), result);
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

    // toggles flagged state of cell
    @Test
    void toggleFlagged() {
        board.toggleFlagged(0, 0);

        assertTrue(board.getCellList().get(0).isFlagged());
    }
}