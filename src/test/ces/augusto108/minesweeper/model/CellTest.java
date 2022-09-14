package ces.augusto108.minesweeper.model;

import ces.augusto108.minesweeper.exceptions.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private Cell cell;

    @BeforeEach
    void createCell() {
        cell = new Cell(3, 3);
    }

    // isAdjacentCell method (whether cell is adjacent to current one)
    @Test
    void isAdjacentCell() {
        Cell adjacentCell = new Cell(3, 2);

        boolean result = cell.isAdjacentCell(adjacentCell);

        assertTrue(result);
    }

    // whether the default state of a cell is not flagged
    @Test
    void isDefaultFlaggedFalse() {
        assertFalse(cell.isFlagged());
    }

    // toggleFlagged method
    @Test
    void toggleFlagged() {
        cell.toggleFlagged();
        assertTrue(cell.isFlagged());
    }

    // whether method won't open a cell that is flagged
    @Test
    void openFlaggedCell() {
        cell.toggleFlagged();
        assertFalse(cell.openCell());
    }

    // whether method opens an unmined and unflagged cell
    @Test
    void openUnminedAndUnflaggedCell() {
        assertTrue(cell.openCell());
    }

    // whether method won't open a mined but flagged cell
    @Test
    void openMinedAndFlaggedCell() {
        cell.mine();
        cell.toggleFlagged();

        assertFalse(cell.openCell());
    }

    // whether method will cause the explosion exception
    @Test
    void openMinedAndUnflaggedCell() {
        cell.mine();

        assertThrows(ExplosionException.class, () -> cell.openCell());
    }


    // whether adjacent cells will open when current cell is opened, if they're safe
    @Test
    void openAdjacentCells() {
        Cell cell1 = new Cell(1, 1);
        Cell cell2 = new Cell(2, 2);

        cell2.adjacentCells.add(cell1);
        cell.adjacentCells.add(cell2);

        cell.openCell();

        assertTrue(cell1.isOpened() && cell2.isOpened());
    }

   /*  whether adjacent cells to current cell's adjacent cells will not open
     when current cell is opened, if they're not safe */
    @Test
    void openAdjacentMinedCells() {
        Cell cell1 = new Cell(1, 1);
        Cell cell2 = new Cell(2, 2);

        cell1.mine();

        cell2.adjacentCells.add(cell1);
        cell.adjacentCells.add(cell2);

        cell.openCell();

        assertTrue(cell2.isOpened() && !cell1.isOpened());
    }

    // whether cell is mined when method is used
    @Test
    void mine() {
        Cell cell1 = new Cell(2, 2);

        cell1.mine();

        assertTrue(cell1.isMined());
    }

    // whether a cell is clear or protected when flagged
    @Test
    void isGoalMet() {
        Cell cell1 = new Cell(4, 4);

        cell1.mine();
        cell1.toggleFlagged();

        assertTrue(cell1.isGoalMet());
    }

    // whether the right sum of mines in adjacent cells is returned
    @Test
    void getAmountOfMines() {
        Cell cell1 = new Cell(3, 4);
        Cell cell2 = new Cell(3, 2);
        Cell cell3 = new Cell(2, 3);

        cell1.mine();
        cell2.mine();
        cell3.mine();

        cell.adjacentCells.add(cell1);
        cell.adjacentCells.add(cell2);
        cell.adjacentCells.add(cell3);

        assertEquals(3, cell.getAmountOfMines());
    }

    // whether cell's states are reset to default values
    @Test
    void resetCell() {
        Cell cell1 = new Cell(2, 3);

        cell1.resetCell();

        assertFalse(cell1.isOpened() && cell1.isFlagged() && cell.isMined());
    }
}