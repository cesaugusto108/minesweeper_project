package ces.augusto108.minesweeper.model;

import ces.augusto108.minesweeper.exceptions.ExplosionException;

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

    public boolean isOpened() {
        return opened;
    }

    void setOpened() {
        this.opened = true;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isMined() {
        return mined;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        if (flagged) return "x";
        else if (opened && mined) return "*";
        else if (opened && getAmountOfMines() > 0) return Integer.toString(getAmountOfMines());
        else if (opened) return " ";
        else return "?";
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
        } else return false;
    }

    // toggles opened and not opened states
    void toggleFlagged() {
        if (!opened) flagged = !flagged;
    }

    // checks whether the surroundings of a cell are safe
    boolean areSurroundingsSafe() {
        return adjacentCells.stream().noneMatch(adjacentCell -> adjacentCell.mined);
    }

    // opens cells: if a cell is mined, an exception is called and the game is over
    boolean openCell() {
        if (!opened && !flagged) {
            opened = true;

            if (areSurroundingsSafe()) adjacentCells.forEach(Cell::openCell);

            if (mined) throw new ExplosionException();

            return true;
        } else return false;
    }

    // set a cell as mined
    void mine() {
        mined = true;
    }

    void unmine() {
        mined = false;
    }

    // whether the goal of the game is met for a certain cell
    boolean isGoalMet() {
        boolean safe = opened && !mined;
        boolean cellProtected = mined && flagged;

        return safe || cellProtected;
    }

    // gets the amount of mines in adjacent cells
    int getAmountOfMines() {
        return (int) adjacentCells.stream().filter(Cell::isMined).count();
    }

    // resets cell states
    void resetCell() {
        opened = false;
        flagged = false;
        mined = false;
    }
}
