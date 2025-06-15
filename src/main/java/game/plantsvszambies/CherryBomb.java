package game.plantsvszambies;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class CherryBomb extends Plant{
    public CherryBomb(int row, int col, Map map) {
        super(row, col, 7, 150, 15);
    }

    public void checkForZombies(GridPane grid) {
        //Cell cell1 = getCellFromGridPane(grid, )
    }

    public Cell getCellFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col &&
                    GridPane.getRowIndex(node) == row &&
                    node instanceof Cell) {
                return (Cell) node;
            }
        }
        return null;
    }

}
