package ces.augusto108.minesweeper.model;

import ces.augusto108.minesweeper.exceptions.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows;
    private final int columns;
    private final int mines;

    private final List<Cell> cellList = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        createCells();
        associateAdjacentCells();
        spreadMines();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("  ");
        for (int column = 0; column < columns; column++) {
            stringBuilder.append(" ");
            stringBuilder.append(column);
            stringBuilder.append(" ");
        }

        stringBuilder.append("\n");

        int i = 0;
        for (int row = 0; row < rows; row++) {
            stringBuilder.append(row);
            stringBuilder.append(" ");
            for (int column = 0; column < columns; column++) {
                stringBuilder.append(" ");
                stringBuilder.append(cellList.get(i).toString());
                stringBuilder.append(" ");
                i++;
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    // creates the cells of the board
    private void createCells() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cellList.add(new Cell(row, column));
            }
        }
    }

    // associates adjacent cells
    private void associateAdjacentCells() {
        for (Cell cA : cellList) {
            for (Cell cB : cellList) {
                cA.isAdjacentCell(cB);
            }
        }
    }

    // spreads the mines in the board randomly
    private void spreadMines() {
        int liveMines = 0;

        while (liveMines < mines) {
            int randomCell = (int) (Math.random() * cellList.size());
            cellList.get(randomCell).mine();

            liveMines = (int) cellList.stream().filter(cell -> cell.isMined()).count();
        }
    }

    // checks winning
    public boolean gameWin() {
        return cellList.stream().allMatch(cell -> cell.isGoalMet());
    }

    // resets the game
    public void resetGame() {
        cellList.forEach(cell -> cell.resetCell());

        spreadMines();
    }

    // opens a cell in the board
    public void openCell(int row, int column) {
        try {
            cellList.parallelStream()
                    .filter(cell -> cell.getRow() == row && cell.getColumn() == column)
                    .findFirst()
                    .ifPresent(cell -> cell.openCell());
        } catch (ExplosionException e) {
            cellList.forEach(cell -> cell.setOpened());

            throw e;
        }
    }

    // flags the cell in the board
    public void toggleFlagged(int row, int column) {
        cellList.parallelStream()
                .filter(cell -> cell.getRow() == row && cell.getColumn() == column)
                .findFirst()
                .ifPresent(cell -> cell.toggleFlagged());
    }
}
