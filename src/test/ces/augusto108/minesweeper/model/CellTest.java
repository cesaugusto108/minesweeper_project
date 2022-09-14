package ces.augusto108.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private Cell cell;

    @BeforeEach
    void createCell() {
        cell = new Cell(3, 3);
    }

    @Test
    void isAdjacentCell() {
        Cell adjacentCell = new Cell(3, 2);

        boolean result = cell.isAdjacentCell(adjacentCell);

        assertTrue(result);
    }
}