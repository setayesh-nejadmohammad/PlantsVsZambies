package game.plantsvszambies;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Mapp {
    public static final int ROWS = 5;
    Button sunflowerButton = new Button();
    Button peashooterButton = new Button();
    Button snowpeaButton = new Button();
    Button tallnutButton = new Button();
    Button cherrybombButton = new Button();
    Button repeaterButton = new Button();
    Button jalapenoButton = new Button();
    Button wallnutButton = new Button();
    List<Plant> plants = new ArrayList<>();
    StackPane sunFlowerPane = new StackPane(sunflowerButton);
    StackPane peashooterPane = new StackPane(peashooterButton);
    StackPane snowpeaPane = new StackPane(snowpeaButton);
    StackPane tallnutPane = new StackPane(tallnutButton);
    StackPane wallnutPane = new StackPane(wallnutButton);
    StackPane repeaterPane = new StackPane(repeaterButton);
    StackPane jalapenoPane = new StackPane(jalapenoButton);
    StackPane cherrybombPane = new StackPane(cherrybombButton);
    Plant plantss[][] = new Plant[5][9];
    public static final int COLS = 9;
    public static final int CELL_SIZE = 80;
    private Scene scene;
    private static AtomicInteger num = new AtomicInteger(0);
    Stage stage;

    private final Cell[][] gridCells;
    public GridPane grid;
    public BorderPane borderPane = new BorderPane();
    ArrayList chosenCards;
    // GameController gameController;
    GameController gameController = new GameController(borderPane, grid);

    Image sunflower = new Image(getClass().getResourceAsStream("images/Plants/sunflower.gif"));
    Image peashooter = new Image(getClass().getResourceAsStream("images/Plants/peashooter.gif"));
    Image snowpea = new Image(getClass().getResourceAsStream("images/Plants/SnowPea.gif"));
    Image tallnut = new Image(getClass().getResourceAsStream("images/Plants/TallNut.gif"));
    Image wallnut = new Image(getClass().getResourceAsStream("images/Plants/wallnut.gif"));
    Image repeater = new Image(getClass().getResourceAsStream("images/Plants/repeater.gif"));
    Image jalapeno = new Image(getClass().getResourceAsStream("images/Plants/jalapeno.gif"));
    Image cherrybomb = new Image(getClass().getResourceAsStream("images/Plants/cherrybomb.gif"));
    Image frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
    Image sunflowerCard = new Image(getClass().getResourceAsStream("images/Cards/sunflowerCard.png"));
    Image peashooterCard = new Image(getClass().getResourceAsStream("images/Cards/peashooterCard.png"));
    Image snowpeaCard = new Image(getClass().getResourceAsStream("images/Cards/snowpeaCard.jpg"));
    Image tallnutCard = new Image(getClass().getResourceAsStream("images/Cards/tallnutCard.jpg"));
    Image wallnutCard = new Image(getClass().getResourceAsStream("images/Cards/wallnutCard.png"));
    Image repeaterCard = new Image(getClass().getResourceAsStream("images/Cards/repeaterCard.png"));
    Image jalapenoCard = new Image(getClass().getResourceAsStream("images/Cards/jalapenoCard.png"));
    Image cherrybombCard = new Image(getClass().getResourceAsStream("images/Cards/cherrybombCard.png"));
    ImageView sunflowerCardImageView = new ImageView(sunflowerCard);
    ImageView peashooterCardImageView = new ImageView(peashooterCard);
    ImageView snowpeaCardImageView = new ImageView(snowpeaCard);
    ImageView tallnutCardImageView = new ImageView(tallnutCard);
    ImageView wallnutCardImageView = new ImageView(wallnutCard);
    ImageView repeaterCardImageView = new ImageView(repeaterCard);
    ImageView jalapenoCardImageView = new ImageView(jalapenoCard);
    ImageView cherrybombCardImageView = new ImageView(cherrybombCard);
    Image shovelCursor = new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/Shovel.png"), 50, 50, true, true);
    Cursor shovelCurs;
    ImageView sunflowerView = new ImageView(sunflower);
    ImageView peashooterView = new ImageView(peashooter);
    ImageView snowpeaView = new ImageView(snowpea);
    ImageView tallnutImageView = new ImageView(tallnut);
    ImageView wallnutImageView = new ImageView(wallnut);
    ImageView repeaterView = new ImageView(repeater);
    ImageView jalapenoView = new ImageView(jalapeno);
    ImageView cherrybombView = new ImageView(cherrybomb);

    public Mapp(Stage stage, ArrayList<String> chosenCards, List<Plant> plants) {

        this.chosenCards = chosenCards;
        this.stage = stage;
        this.grid = new GridPane();
        grid.setViewOrder(77);
        grid.setHgap(0);
        grid.setVgap(0);
        this.plants = plants;
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
        ImageCursor imageCursor = new ImageCursor(shovelCursor, shovelCursor.getWidth() / 2, shovelCursor.getHeight() / 2);
        shovelCurs = (Cursor) imageCursor;
    }
    public void setChosenCards(ArrayList<String> chosenCards) {
        this.chosenCards = chosenCards;
    }
    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public void drawMap() {
        addSaveLoadButton();
        sunFalling();
        // Sun sun = new Sun(borderPane, gameController, 400);

        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        borderPane.setBackground(new Background(bgImage));

        // ImageView sunflowerCardView = new ImageView(sunflowerCard);
        // sunflowerCardView.setFitWidth(CELL_SIZE * 1.5);
        // sunflowerCardView.setFitHeight(CELL_SIZE);

        // sunflowerButton.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        // peashooterButton.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        // ImageView peashooterCardView = new ImageView(peashooterCard);


        VBox vbox = new VBox();
        borderPane.setLeft(vbox);
        //vbox.getChildren().add(sunFlowerPane);
        //vbox.getChildren().add(peashooterPane);
        buttonsHandler(vbox);
        vbox.getChildren().add(gameController.getShovelStack());

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
                StackPane cell = createCell(row, col);
                grid.add(cell, col, row);
                if (plantss[row][col] != null) {
                    cell.getChildren().add(plantss[row][col].view);
                }
            }
        }
        StackPane cell = new StackPane();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/plants/HypnoShroom.gif")));
        HypnoShroom hypnoShroom = new HypnoShroom(3, 3, imageView);
        cell.getChildren().add(imageView);
        Game.getInstance().getPlants().add(hypnoShroom);
        grid.add(cell, 3, 3);


        stage.setTitle("plant vs zambies");
        scene = new Scene(borderPane, 1024, 626);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        gameController.getShovelStack().setOnMouseClicked(event -> {
            scene.setCursor(shovelCurs);
            gameController.getShovelStack().getChildren().remove(gameController.getShovelImage());
            num.set(9);
        });
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



    public StackPane createCell(int row, int col) {
        ImageView sunflowerView = new ImageView(sunflower);
        ImageView peashooterView = new ImageView(peashooter);
        ImageView snowpeaView = new ImageView(snowpea);
        ImageView tallnutImageView = new ImageView(tallnut);
        ImageView wallnutImageView = new ImageView(wallnut);
        ImageView repeaterView = new ImageView(repeater);
        ImageView jalapenoView = new ImageView(jalapeno);
        ImageView cherrybombView = new ImageView(cherrybomb);
        sunflowerView.setFitHeight(CELL_SIZE); sunflowerView.setFitWidth(CELL_SIZE);
        peashooterView.setFitHeight(CELL_SIZE); peashooterView.setFitWidth(CELL_SIZE);
        snowpeaView.setFitHeight(CELL_SIZE); snowpeaView.setFitWidth(CELL_SIZE);
        tallnutImageView.setFitHeight(CELL_SIZE); tallnutImageView.setFitWidth(CELL_SIZE);
        wallnutImageView.setFitWidth(CELL_SIZE); wallnutImageView.setFitWidth(CELL_SIZE);
        repeaterView.setFitHeight(CELL_SIZE); repeaterView.setFitWidth(CELL_SIZE);
        jalapenoView.setFitHeight(CELL_SIZE); jalapenoView.setFitWidth(CELL_SIZE);
        cherrybombView.setFitHeight(CELL_SIZE); cherrybombView.setFitWidth(CELL_SIZE);

        StackPane cell = new StackPane();
        cell.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("salmammmmmmm");
            if (num.intValue() == 1 && cell.getChildren().size() == 0 && gameController.totalScore >= 50) {
                num.set(0);
                cell.getChildren().addAll(sunflowerView);
                Game.getInstance().getPlants().add(new Sunflower(row, col , cell, sunflowerView));
                createCardWithCooldown(sunFlowerPane, sunflowerButton, 5);
                gameController.reduceScore(50);
                // get the exact position on sunflower to put sun
                Bounds boundsInScene = cell.localToScene(cell.getBoundsInLocal());
                double x = boundsInScene.getMinX();
                double y = boundsInScene.getMinY();

            } else if (num.intValue() == 2 && cell.getChildren().size() == 0 && gameController.totalScore >= 100) {
                num.set(0);
                //plants.add(new Peashooter(row, col , peashooterView));
                Game.getInstance().getPlants().add(new Peashooter(row, col , peashooterView));
                createCardWithCooldown(peashooterPane, peashooterButton, 10);
                cell.getChildren().addAll(peashooterView);
                gameController.reduceScore(100);
            }
            else if(num.intValue() == 3 && cell.getChildren().size() == 0 && gameController.totalScore >= 50) {
                num.set(0);
                Game.getInstance().getPlants().add(new SnowPea(row, col , snowpeaView));
                createCardWithCooldown(snowpeaPane, snowpeaButton, 17.5);
                cell.getChildren().addAll(snowpeaView);
                gameController.reduceScore(175);
            }
            else if(num.intValue() == 4 && cell.getChildren().size() == 0 && gameController.totalScore >= 125) {
                num.set(0);
                TallNut tallNut = new TallNut(row, col, tallnutImageView, cell);
                plants.add(tallNut);
                createCardWithCooldown(tallnutPane, tallnutButton, 12.5);
                //cell.getChildren().addAll(tallnutImageView);
                gameController.reduceScore(125);
            }
            else if(num.intValue() == 5 && cell.getChildren().size() == 0 && gameController.totalScore >= 50) {
                num.set(0);
                WallNut wallNut = new WallNut(row, col, wallnutImageView, cell);
                plants.add(wallNut);
                createCardWithCooldown(wallnutPane, wallnutButton, 5);
                //cell.getChildren().addAll(wallnutImageView);
                gameController.reduceScore(50);
            }
            else if(num.intValue() == 6 && cell.getChildren().size() == 0 && gameController.totalScore >= 200) {
                num.set(0);
                Game.getInstance().getPlants().add(new RepeaterPeaShooter(row, col , repeaterView));
                createCardWithCooldown(repeaterPane, repeaterButton, 20);
                cell.getChildren().addAll(repeaterView);
                gameController.reduceScore(200);
            }
            else if(num.intValue() == 7 && cell.getChildren().size() == 0 && gameController.totalScore >= 125) {
                num.set(0);
                Jalapeno jala = new Jalapeno(row, col, cell, jalapenoView);
                plants.add(jala);
                createCardWithCooldown(jalapenoPane, jalapenoButton, 3);
                //cell.getChildren().addAll(jalapenoView);
                gameController.reduceScore(125);
            }
            else if(num.intValue() == 8 && cell.getChildren().size() == 0 && gameController.totalScore >= 150) {
                num.set(0);
                CherryBomb cherry = new CherryBomb(row, col, cell, cherrybombView);
                gridCells[row][col].setPlant(cherry);
                plants.add(cherry);
                createCardWithCooldown(cherrybombPane, cherrybombButton,3 );
                //cell.getChildren().addAll(cherrybombView);
                gameController.reduceScore(150);
            }
            else if(num.intValue() == 9 && cell.getChildren().size() != 0) {
                scene.setCursor(Cursor.DEFAULT);
                Game.getInstance().removePlant(findPlantAt(row, col));
                cell.getChildren().clear();
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                num.set(0);
            }


        });

        return cell;

    }
    private Plant findPlantAt(int row, int col) {
        for (Plant plant : plants) {
            if (plant.row == row && plant.col == col) {
                return plant;
            }
        }
        return null;
    }

    private void createCardWithCooldown(StackPane cardButton, Button b1, double cooldownSeconds) {
        Rectangle overlay = new Rectangle(b1.getWidth(), CELL_SIZE);
        overlay.setFill(Color.color(0, 0, 0, 0.5));
        overlay.setTranslateY(0);
        overlay.setViewOrder(-1);
        overlay.setVisible(false);
        cardButton.getChildren().addAll(overlay);
        cardButton.setAlignment(overlay, Pos.TOP_CENTER);
        b1.setDisable(true);
        overlay.setVisible(true);
        overlay.setHeight(cardButton.getHeight());
        overlay.setTranslateY(0);
        overlay.setOpacity(1.0);

        Timeline shrink = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(overlay.heightProperty(), cardButton.getHeight())),
                new KeyFrame(Duration.seconds(cooldownSeconds),
                        new KeyValue(overlay.heightProperty(), 0)));
        // Fade out opacity (optional)
        FadeTransition fade = new FadeTransition(Duration.seconds(cooldownSeconds), overlay);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        // After both animations: enable button
        shrink.setOnFinished(ev -> {
            b1.setDisable(false);
            overlay.setVisible(false);
        });

        shrink.play();
        fade.play();
    }
    public void addSaveLoadButton(){
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        VBox vbox = new VBox();
        vbox.getChildren().add(saveButton);
        //vbox.getChildren().add(loadButton);
        borderPane.setRight(vbox);
        saveButton.setOnAction(e -> {
            SaveLoadManager.saveGame("savedData.txt", gameController.totalScore);
        });
        loadButton.setOnAction(e -> {
            //SaveLoadManager.loadGame("savedData.txt"); // ?????!?!?!?
        });
    }

    public void sunFalling() {
        Timeline sunSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            double x = Math.random() * (borderPane.getWidth() - 50);
            new Sun(borderPane, x);
        }));
        sunSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        sunSpawnTimeline.play();
    }


    public void buttonsHandler(VBox vbox) {
        sunflowerCardImageView.setFitWidth(CELL_SIZE * 1.5);
        sunflowerCardImageView.setFitHeight(CELL_SIZE);
        peashooterCardImageView.setFitWidth(CELL_SIZE * 1.5);
        peashooterCardImageView.setFitHeight(CELL_SIZE);
        snowpeaCardImageView.setFitWidth(CELL_SIZE * 1.5);
        snowpeaCardImageView.setFitHeight(CELL_SIZE);
        tallnutCardImageView.setFitWidth(CELL_SIZE * 1.5);
        tallnutCardImageView.setFitHeight(CELL_SIZE);
        wallnutCardImageView.setFitWidth(CELL_SIZE * 1.5);
        wallnutCardImageView.setFitHeight(CELL_SIZE);
        repeaterCardImageView.setFitWidth(CELL_SIZE * 1.5);
        repeaterCardImageView.setFitHeight(CELL_SIZE);
        jalapenoCardImageView.setFitWidth(CELL_SIZE * 1.5);
        jalapenoCardImageView.setFitHeight(CELL_SIZE);
        cherrybombCardImageView.setFitWidth(CELL_SIZE * 1.5);
        cherrybombCardImageView.setFitHeight(CELL_SIZE);

        sunflowerButton.getStyleClass().add("button");
        peashooterButton.getStyleClass().add("button");
        snowpeaButton.getStyleClass().add("button");
        tallnutButton.getStyleClass().add("button");
        cherrybombButton.getStyleClass().add("button");
        repeaterButton.getStyleClass().add("button");
        jalapenoButton.getStyleClass().add("button");
        wallnutButton.getStyleClass().add("button");

        sunflowerButton.setGraphic(sunflowerCardImageView);
        peashooterButton.setGraphic(peashooterCardImageView);
        snowpeaButton.setGraphic(snowpeaCardImageView);
        tallnutButton.setGraphic(tallnutCardImageView);
        cherrybombButton.setGraphic(cherrybombCardImageView);
        repeaterButton.setGraphic(repeaterCardImageView);
        jalapenoButton.setGraphic(jalapenoCardImageView);
        wallnutButton.setGraphic(wallnutCardImageView);

        for (int i = 0; i < chosenCards.size(); i++) {
            if (chosenCards.get(i).equals("Sunflower")) {
                vbox.getChildren().add(sunFlowerPane);
            }
            else if (chosenCards.get(i).equals("Peashooter")) {
                vbox.getChildren().add(peashooterPane);
            }
            else if (chosenCards.get(i).equals("SnowPea")) {
                vbox.getChildren().add(snowpeaPane);
            }
            else if (chosenCards.get(i).equals("TallNut")) {
                vbox.getChildren().add(tallnutPane);
            }
            else if (chosenCards.get(i).equals("WallNut")) {
                vbox.getChildren().add(wallnutPane);
            }
            else if (chosenCards.get(i).equals("RepeaterPeaShooter")) {
                vbox.getChildren().add(repeaterPane);
            }
            else if (chosenCards.get(i).equals("Jalapeno")) {
                vbox.getChildren().add(jalapenoPane);
            }
            else if (chosenCards.get(i).equals("CherryBomb")) {
                vbox.getChildren().add(cherrybombPane);
            }

        }

        sunflowerButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
            scene.setCursor(Cursor.DEFAULT);
            }
            num.set(1);
        });
        peashooterButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(2);

        });
        snowpeaButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(3);
        });
        tallnutButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(4);
        });
        wallnutButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(5);
        });
        repeaterButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(6);
        });
        jalapenoButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(7);
        });
        cherrybombButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(8);
        });
    }
}