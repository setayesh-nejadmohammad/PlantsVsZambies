package game.plantsvszambies;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static int rowNum = 5;
    public static int colNum = 9;
    public static int CELL_SIZE = 81;
    @Override
    public void start(Stage stage) throws IOException {

        Image frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
        BorderPane borderPane = new BorderPane();
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        borderPane.setBackground(new Background(bgImage));

        GridPane grid = new GridPane();
        grid.setHgap(0); // Remove horizontal gap
        grid.setVgap(0); // Remove vertical gap

        grid.setLayoutX(250);
        grid.setLayoutY(85);

        borderPane.getChildren().add(grid);

        // Create 5x9 grid of squares
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE+10);
                rect.setFill(null);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(0.5);
                grid.add(rect, col, row);
            }
        }

        VBox vbox = new VBox();


        Scene scene = new Scene(borderPane, 1024, 626);
        stage.setTitle("Map");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}