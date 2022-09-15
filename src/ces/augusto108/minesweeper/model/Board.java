package ces.augusto108.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Board   {
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

        int row;
        int column;
        for (row = 0; row < rows; row++) {
            for (column = 0; column < columns; column++) {
                stringBuilder
                        .append(" ")
                        .append(cellList.get(row))
                        .append(" ");
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
            liveMines = (int) cellList.stream().filter(cell -> cell.isMined()).count();
            int randomCell = (int) (Math.random() * cellList.size());

            cellList.get(randomCell).mine();
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
        cellList.parallelStream()
                .filter(cell -> cell.getRow() == row && cell.getColumn() == column)
                .findFirst()
                .ifPresent(cell -> cell.openCell());
    }

    // flags the cell in the board
    public void toggleFlagged(int row, int column) {
        cellList.parallelStream()
                .filter(cell -> cell.getRow() == row && cell.getColumn() == column)
                .findFirst()
                .ifPresent(cell -> cell.toggleFlagged());
    }
}
