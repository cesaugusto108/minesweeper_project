package ces.augusto108.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows;
    private final int columns;
    private final int mines;

    private List<Cell> cellList = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        createCells();
        associateAdjacentCells();
        spreadMines();
    }

    private void createCells() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cellList.add(new Cell(row, column));
            }
        }
    }

    private void associateAdjacentCells() {
        for (Cell cA : cellList) {
            for (Cell cB : cellList) {
                cA.isAdjacentCell(cB);
            }
        }
    }

    private void spreadMines() {
        int liveMines = 0;

        while (liveMines < mines) {
            liveMines = (int) cellList.stream().filter(cell -> cell.isMined()).count();
            int randomCell = (int) (Math.random() * cellList.size());

            cellList.get(randomCell).mine();
        }
    }

    public boolean wonGame() {
        return cellList.stream().allMatch(cell -> cell.isGoalMet());
    }

    public void resetGame() {
        cellList.forEach(cell -> cell.resetCell());

        spreadMines();
    }
}
