package game.plantsvszambies;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class Map {
    private Button sunFlowerButton = new Button();
    private Button peashooterButton = new Button();
    private StackPane sunView = new StackPane(sunFlowerButton);
    private StackPane flowerView = new StackPane(peashooterButton);
    public static final int ROWS = 5;
    public static final int COLS = 9;
    public static final int CELL_SIZE = 80;
    private static AtomicInteger num = new AtomicInteger(0);
    Stage stage;

    private final Cell[][] gridCells;
    public GridPane grid;
    public BorderPane borderPane = new BorderPane();
    Image sunflower = new Image(getClass().getResourceAsStream("images/Plants/sunflower.gif"));
    Image peashooter = new Image(getClass().getResourceAsStream("images/Plants/peashooter.gif"));
    Image frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
    Image sunflowerCard = new Image(getClass().getResourceAsStream("images/Cards/sunflowerCard.png"));
    Image peashooterCard = new Image(getClass().getResourceAsStream("images/Cards/peashooterCard.png"));

    public Map(Stage stage) {
        this.stage = stage;
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
    }

    public void drawMap() {
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        borderPane.setBackground(new Background(bgImage));

        ImageView sunflowerCardView = new ImageView(sunflowerCard);
        sunflowerCardView.setFitWidth(CELL_SIZE * 1.5);
        sunflowerCardView.setFitHeight(CELL_SIZE);

        ImageView peashooterCardView = new ImageView(peashooterCard);
        peashooterCardView.setFitWidth(CELL_SIZE * 1.5);
        peashooterCardView.setFitHeight(CELL_SIZE);



        sunFlowerButton.setGraphic(sunflowerCardView);
        peashooterButton.setGraphic(peashooterCardView);

        sunFlowerButton.setOnAction(event -> {
            num.set(1);

        });

        peashooterButton.setOnAction(event -> {
            num.set(2);
        });

        VBox vbox = new VBox();

        borderPane.setLeft(vbox);
        vbox.getChildren().add(sunView);
        vbox.getChildren().add(flowerView);

        vbox.setLayoutX(250);
        vbox.setLayoutY(85);
        vbox.setPadding(new Insets(10));

        borderPane.getChildren().add(grid);

        // Create 5x9 grid of squares
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE + 10);
                rect.setFill(null);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(0.5);
                grid.add(rect, col, row);
                StackPane cell = createCell();
                grid.add(cell, col, row);
            }
        }

        NormalZombie zombie1 = new NormalZombie(0, 9, this);
        NormalZombie zombie2 = new NormalZombie(2, 9, this);

        //grid.add(zombie2.getImageView(), 9, 2);
        zombie2.start();

        //grid.add(zombie1.getImageView(), 9, 0);
        zombie1.start();

        stage.setTitle("plant vs zambies");
        Scene scene = new Scene(borderPane, 1024, 626);
        stage.setScene(scene);
        stage.show();

    }

    public StackPane getStackPane(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            return gridCells[row][col].getStackPane();
        }
        return null;
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

    public void moveZombieForward(Zombie zombie) {
        int row = zombie.getRow();
        int col = zombie.getCol();
        Cell cell = getCell(row, col);
        zombie.setCol(col + 1);
    }

    private StackPane createCell() {
        ImageView sunflowerView = new ImageView(sunflower);
        ImageView peashooterView = new ImageView(peashooter);
        StackPane cell = new StackPane();
        cell.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("salmammmmmmm");
            if (num.intValue() == 1 && cell.getChildren().size() == 0) {
                createCardWithCooldown(sunView,3);
                num.set(0);
                cell.getChildren().addAll(sunflowerView);

            } else if (num.intValue() == 2 && cell.getChildren().size() == 0) {
                createCardWithCooldown(flowerView,3);
                num.set(0);
                cell.getChildren().addAll(peashooterView);
            }
        });

        return cell;
    }
    private void createCardWithCooldown(StackPane cardButton,double cooldownSeconds) {
        Rectangle overlay = new Rectangle(2.2 * CELL_SIZE, CELL_SIZE); // Adjust size to match button
        overlay.setFill(Color.color(0, 0, 0, 0.5)); // semi-transparent black
        overlay.setTranslateY(0);
        overlay.setViewOrder(-1); // Make sure it's above the button

        overlay.setVisible(false); // Hide until used
        cardButton.getChildren().addAll(overlay);

        sunFlowerButton.setDisable(true);
        overlay.setVisible(true);
        overlay.setHeight(cardButton.getHeight());
        overlay.setTranslateY(0);
        overlay.setOpacity(1.0);
        num.set(1);

        // Animation: overlay shrinks upwards
        Timeline shrink = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(overlay.heightProperty(), overlay.getHeight()),
                        new KeyValue(overlay.translateYProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(cooldownSeconds),
                        new KeyValue(overlay.heightProperty(), 0),
                        new KeyValue(overlay.translateYProperty(), -overlay.getHeight())
                )
        );

        // Fade out opacity (optional)
        FadeTransition fade = new FadeTransition(Duration.seconds(cooldownSeconds), overlay);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        // After both animations: enable button
        shrink.setOnFinished(ev -> {
            sunFlowerButton.setDisable(false);
            overlay.setVisible(false);
        });

        shrink.play();
        fade.play();
    }

}