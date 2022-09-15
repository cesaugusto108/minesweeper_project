package ces.augusto108.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void createBoard() {
        board = new Board(2, 2, 4);
    }

    @Test
    void getRows() {
        assertEquals(board.getRows(), 2);
    }

    @Test
    void getColumns() {
        assertEquals(board.getColumns(), 2);
    }

    @Test
    void getMines() {
        assertEquals(board.getMines(), 4);
    }

    @Test
    void testToString() {
        Board testBoard = new Board(1, 1, 0);

        String result = " " +
                testBoard.getCellList().get(0) +
                " " +
                "\n";

        assertEquals(testBoard.toString(), result);
    }

    @Test
    void gameWin() {
        Cell cell1 = board.getCellList().get(0);
        Cell cell2 = board.getCellList().get(1);
        Cell cell3 = board.getCellList().get(2);
        Cell cell4 = board.getCellList().get(3);

        cell1.toggleFlagged();
        cell2.toggleFlagged();
        cell3.toggleFlagged();
        cell4.toggleFlagged();

        assertTrue(board.gameWin());
    }

    @Test
    void resetGame() {
        board.resetGame();

        for (Cell cell : board.getCellList()) {
            assertFalse(cell.isOpened());
            assertFalse(cell.isFlagged());
            assertTrue(cell.isMined());
        }
    }

    @Test
    void openCell() {
        Cell cell = board.getCellList().get(0);

        cell.unmine();

        board.openCell(0, 0);

        assertTrue(board.getCellList().get(0).isOpened());
    }

    @Test
    void toggleFlagged() {
        Cell cell = board.getCellList().get(0);

        board.toggleFlagged(0, 0);

        assertTrue(board.getCellList().get(0).isFlagged());
    }
}