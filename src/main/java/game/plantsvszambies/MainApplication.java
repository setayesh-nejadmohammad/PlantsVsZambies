package game.plantsvszambies;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        borderPane.setLeft(vbox);


        Scene scene = new Scene(borderPane, 1024, 626);
        stage.setTitle("Map");
        stage.setScene(scene);
        stage.show();

        Image sunflowerCard = new Image(getClass().getResourceAsStream("images/Cards/sunflowerCard.png"));
        ImageView sunflowerCardView = new ImageView(sunflowerCard);
        sunflowerCardView.setFitWidth(CELL_SIZE*2);
        sunflowerCardView.setFitHeight(CELL_SIZE*2/1.5);
        Image peashooterCard = new Image(getClass().getResourceAsStream("images/Cards/peashooterCard.png"));
        ImageView peashooterCardView = new ImageView(peashooterCard);
        peashooterCardView.setFitWidth(CELL_SIZE*2);
        peashooterCardView.setFitHeight(CELL_SIZE*2/1.5);

        Button sunFlowerButton = new Button();
        Button peashooterButton = new Button();

        sunFlowerButton.setGraphic(sunflowerCardView);
        peashooterButton.setGraphic(peashooterCardView);

        sunFlowerButton.setOnAction(event -> {
            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {
                    StackPane cell = createCell(1);
                    grid.add(cell, col, row);
                }
            }
        });

        peashooterButton.setOnAction(event -> {
            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {
                    StackPane cell = createCell(2);
                    grid.add(cell, col, row);
                }
            }
        });


        vbox.getChildren().add(sunFlowerButton);
        vbox.getChildren().add(peashooterButton);

    }

    private StackPane createCell(int num) {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);


        Image sunflower = new Image(getClass().getResourceAsStream("images/Plants/sunflower.gif"));
        ImageView sunflowerView = new ImageView(sunflower);

        Image peashooter = new Image(getClass().getResourceAsStream("images/Plants/peashooter.gif"));
        ImageView peashooterView = new ImageView(peashooter);


        final StackPane cell = new StackPane();
        boolean[] isClicked = {false};

        cell.setOnMouseClicked((MouseEvent e) -> {
            isClicked[0] = true;
            System.out.println("salmammmmmmm");
            if(num == 1){
                cell.getChildren().addAll(sunflowerView);
            }
            else if(num == 2){
                cell.getChildren().addAll(peashooterView);
            }

        });


        return cell;
    }


    public static void main(String[] args) {
        launch();
    }
}
