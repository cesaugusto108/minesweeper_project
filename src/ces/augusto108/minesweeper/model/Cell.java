package ces.augusto108.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean opened;
    private boolean flagged;
    private boolean mined;
    private final int row;
    private final int column;

    List<Cell> adjacentCells = new ArrayList<>();

    Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // checks whether a cell is adjacent to current cell
    boolean isAdjacentCell(Cell adjacentCell) {
        boolean otherRow = row != adjacentCell.row;
        boolean otherColumn = column != adjacentCell.column;
        boolean diagonal = otherRow && otherColumn;

        int cellRowDistance = row - adjacentCell.row;
        int cellColumnDistance = column - adjacentCell.column;
        int cellDistance = cellRowDistance + cellColumnDistance;

        if (cellDistance == 1 && !diagonal) {
            adjacentCells.add(adjacentCell);

            return true;
        } else if (cellDistance == 2 && diagonal) {
            adjacentCells.add(adjacentCell);

            return true;
        } else {
            return false;
        }
    }
}
