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

    @Test
    void setOpened() {
        cell.setOpened();
        assertTrue(cell.isOpened());
    }

    @Test
    void getRow() {
        assertEquals(3, cell.getRow());
    }

    @Test
    void getColumn() {
        assertEquals(3, cell.getColumn());
    }

    // toString when cell is flagged
    @Test
    void flaggedCellTestToString() {
        final Cell flaggedCell = new Cell(4, 4);
        flaggedCell.toggleFlagged();
        assertEquals("x", flaggedCell.toString());
    }

    // toString when cell is opened and mined
    @Test
    void openedAndMinedCellTestToString() {
        final Cell openedAndMinedCell = new Cell(4, 3);
        openedAndMinedCell.openCell();
        openedAndMinedCell.mine();
        assertEquals("*", openedAndMinedCell.toString());
    }

    // toString to show amount of mines in adjacent cells
    @Test
    void getAmountOfMinesTestToString() {
        final Cell cellA = new Cell(5, 5);
        final Cell cellB = new Cell(5, 6);
        cellB.mine();
        cellA.adjacentCells.add(cellB);
        cellA.openCell();
        assertEquals(1, cellA.getAmountOfMines());
    }

    // toString when cell is just open
    @Test
    void openedCellTestToString() {
        final Cell openedCell = new Cell(2, 3);
        openedCell.openCell();
        assertEquals(" ", openedCell.toString());
    }

    // toString when cell is not open and not flagged
    @Test
    void testToString() {
        final Cell cell2 = new Cell(3, 1);
        assertEquals("?", cell2.toString());
    }

    // isAdjacentCell method (whether cell is adjacent to current one)
    @Test
    void isAdjacentCell() {
        final Cell adjacentCell1 = new Cell(3, 2);
        boolean result1 = cell.isAdjacentCell(adjacentCell1);
        assertTrue(result1);

        final Cell adjacentCell2 = new Cell(2, 2);
        boolean result2 = cell.isAdjacentCell(adjacentCell2);
        assertTrue(result2);

        final Cell adjacentCell3 = new Cell(1, 2);
        boolean result3 = cell.isAdjacentCell(adjacentCell3);
        assertFalse(result3);
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
        final Cell cell1 = new Cell(1, 1);
        final Cell cell2 = new Cell(2, 2);
        cell2.adjacentCells.add(cell1);
        cell.adjacentCells.add(cell2);
        cell.openCell();
        assertTrue(cell1.isOpened() && cell2.isOpened());
    }

    /*  whether adjacent cells to current cell's adjacent cells will not open
      when current cell is opened, if they're not safe */
    @Test
    void openAdjacentMinedCells() {
        final Cell cell1 = new Cell(1, 1);
        final Cell cell2 = new Cell(2, 2);
        cell1.mine();
        cell2.adjacentCells.add(cell1);
        cell.adjacentCells.add(cell2);
        cell.openCell();
        assertTrue(cell2.isOpened() && !cell1.isOpened());
    }

    // whether cell is mined when method is used
    @Test
    void mine() {
        final Cell cell1 = new Cell(2, 2);
        cell1.mine();
        assertTrue(cell1.isMined());
    }

    // whether a cell is clear or protected when flagged
    @Test
    void isGoalMet() {
        final Cell cell1 = new Cell(4, 4);
        cell1.mine();
        cell1.toggleFlagged();
        assertTrue(cell1.isGoalMet());
    }

    // whether the right sum of mines in adjacent cells is returned
    @Test
    void getAmountOfMines() {
        final Cell cell1 = new Cell(3, 4);
        final Cell cell2 = new Cell(3, 2);
        final Cell cell3 = new Cell(2, 3);
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
        final Cell cell1 = new Cell(2, 3);
        cell1.resetCell();
        assertFalse(cell1.isOpened() && cell1.isFlagged() && cell.isMined());
    }
}