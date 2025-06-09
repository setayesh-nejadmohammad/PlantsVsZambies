package game.plantsvszambies;

import javafx.stage.Stage;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map {
    public static final int ROWS = 5;
    public static final int COLS = 9;
    public static final int CELL_SIZE = 80;

    private final Cell[][] gridCells;
    public GridPane grid;

    public Map(BorderPane rootPane) {
        this.grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setLayoutX(250);
        grid.setLayoutY(85);

        this.gridCells = new Cell[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                StackPane cellPane = new StackPane();

                Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE + 10);
                background.setFill(null);
                background.setStroke(Color.BLACK);
                background.setStrokeWidth(0.5);

                cellPane.getChildren().add(background);
                grid.add(cellPane, col, row);

                Cell cell = new Cell(row, col, cellPane);
                gridCells[row][col] = cell;
            }
        }

        rootPane.getChildren().add(grid);
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            return gridCells[row][col];
        }
        return null;
    }

    public GridPane getGridPane() {
        return grid;
    }

    public void moveZombieForward(Zombie zambie) {
        //.... ?!
    }
}


