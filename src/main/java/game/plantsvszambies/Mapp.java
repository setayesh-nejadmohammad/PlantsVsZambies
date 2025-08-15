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
import javafx.scene.Node;
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

import java.util.*;
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
    Button graveBusterButton = new Button();
    Button doomShroomButton = new Button();
    Button hypnoShroomButton = new Button();
    Button iceShroomButton = new Button();
    Button planternButton = new Button();
    Button puffShroomButton = new Button();
    Button scaredyShroomButton = new Button();
    Button bloverButton = new Button();
    Button coffeeBeanButton = new Button();
    List<Plant> plants = new ArrayList<>();
    StackPane sunFlowerPane = new StackPane(sunflowerButton);
    StackPane peashooterPane = new StackPane(peashooterButton);
    StackPane snowpeaPane = new StackPane(snowpeaButton);
    StackPane tallnutPane = new StackPane(tallnutButton);
    StackPane wallnutPane = new StackPane(wallnutButton);
    StackPane repeaterPane = new StackPane(repeaterButton);
    StackPane jalapenoPane = new StackPane(jalapenoButton);
    StackPane cherrybombPane = new StackPane(cherrybombButton);
    StackPane graveBusterPane = new StackPane(graveBusterButton);
    StackPane doomShroomPane = new StackPane(doomShroomButton);
    StackPane hypnoShroomPane = new StackPane(hypnoShroomButton);
    StackPane iceShroomPane = new StackPane(iceShroomButton);
    StackPane planternPane = new StackPane(planternButton);
    StackPane puffShroomPane = new StackPane(puffShroomButton);
    StackPane scaredyShroomPane = new StackPane(scaredyShroomButton);
    StackPane bloverPane = new StackPane(bloverButton);
    StackPane coffeeBeanPane = new StackPane(coffeeBeanButton);

    ArrayList<Fog> fogs = new ArrayList<>();
    Plant plantss[][] = new Plant[5][9];
    public static final int COLS = 9;
    public static final int CELL_SIZE = 80;
    private Scene scene;
    private static AtomicInteger num = new AtomicInteger(0);
    Stage stage;

    public int[][] gravePosPairs = new int[5][2];  // 5 pair pos for graves
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
    Image hypnoShroom = new Image(getClass().getResourceAsStream("images/Plants/HypnoShroom.gif"));
    Image iceShroom = new Image(getClass().getResourceAsStream("images/Plants/IceShroom.gif"));
    Image doomShroom = new Image(getClass().getResourceAsStream("images/Plants/DoomShroom.gif"));
    Image graveBuster = new Image(getClass().getResourceAsStream("images/Plants/GraveBuster.gif"));
    Image puffShroom = new Image(getClass().getResourceAsStream("images/Plants/puffShroom.gif"));
    Image scaredyShroom = new Image(getClass().getResourceAsStream("images/Plants/ScaredyShroom.gif"));
    Image blover = new Image(getClass().getResourceAsStream("images/Plants/Bloverpagedoll.gif"));
    Image plantern = new Image(getClass().getResourceAsStream("images/Plants/Plantern.gif"));
    Image coffeeBean = new Image(getClass().getResourceAsStream("images/Plants/coffeebean.gif"));
    Image frontYard = new Image(getClass().getResourceAsStream("images/Frontyard.png"));
    Image sunflowerCard = new Image(getClass().getResourceAsStream("images/Cards/sunflowerCard.png"));
    Image peashooterCard = new Image(getClass().getResourceAsStream("images/Cards/peashooterCard.png"));
    Image snowpeaCard = new Image(getClass().getResourceAsStream("images/Cards/snowpeaCard.jpg"));
    Image tallnutCard = new Image(getClass().getResourceAsStream("images/Cards/tallnutCard.jpg"));
    Image wallnutCard = new Image(getClass().getResourceAsStream("images/Cards/wallnutCard.png"));
    Image repeaterCard = new Image(getClass().getResourceAsStream("images/Cards/repeaterCard.png"));
    Image jalapenoCard = new Image(getClass().getResourceAsStream("images/Cards/jalapenoCard.png"));
    Image cherrybombCard = new Image(getClass().getResourceAsStream("images/Cards/cherrybombCard.png"));
    Image graveBusterCard = new Image(getClass().getResourceAsStream("images/Cards/graveBusterCard.png"));
    Image doomShroomCard = new Image(getClass().getResourceAsStream("images/Cards/DoomShroomCard.png"));
    Image hypnoShroomCard = new Image(getClass().getResourceAsStream("images/Cards/HypnoShroomCard.png"));
    Image iceShroomCard = new Image(getClass().getResourceAsStream("images/Cards/IceShroomCard.jpg"));
    Image planternCard = new Image(getClass().getResourceAsStream("images/Cards/PlanternCard.png"));
    Image puffShroomCard = new Image(getClass().getResourceAsStream("images/Cards/PuffShroomCard.png"));
    Image scaredyShroomCard = new Image(getClass().getResourceAsStream("images/Cards/ScaredyShroomCard.png"));
    Image bloverCard = new Image(getClass().getResourceAsStream("images/Cards/BloverCard.png"));
    Image coffeeBeanCard = new Image(getClass().getResourceAsStream("images/Cards/CoffeeBeanCard.png"));
    Image grave = new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/grave.png"));
    ImageView sunflowerCardImageView = new ImageView(sunflowerCard);
    ImageView peashooterCardImageView = new ImageView(peashooterCard);
    ImageView snowpeaCardImageView = new ImageView(snowpeaCard);
    ImageView tallnutCardImageView = new ImageView(tallnutCard);
    ImageView wallnutCardImageView = new ImageView(wallnutCard);
    ImageView repeaterCardImageView = new ImageView(repeaterCard);
    ImageView jalapenoCardImageView = new ImageView(jalapenoCard);
    ImageView cherrybombCardImageView = new ImageView(cherrybombCard);
    ImageView graveBusterImageView = new ImageView(graveBusterCard);
    ImageView doomShroomImageView = new ImageView(doomShroomCard);
    ImageView hypnoShroomImageView = new ImageView(hypnoShroomCard);
    ImageView iceShroomImageView = new ImageView(iceShroomCard);
    ImageView planternImageView = new ImageView(planternCard);
    ImageView puffShroomImageView = new ImageView(puffShroomCard);
    ImageView scaredyShroomImageView = new ImageView(scaredyShroomCard);
    ImageView bloverCardImageView = new ImageView(bloverCard);
    ImageView coffeeBeanImageView = new ImageView(coffeeBeanCard);
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

        for(int i = 0;i < 5; i++){
            gravePosPairs[i][0] = -1;
            gravePosPairs[i][1] = -1;
        }


        /*for (int row = 0; row < ROWS; row++) {
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
       }*/
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
        if(!Game.isNight) {
            sunFalling();
        }
        else if(Game.getInstance().getTime() < 300 && !Game.getInstance().isClient()) {
            findPosForGraves();
        }

        if(Game.isNight){
            frontYard = Game.getInstance().night;
        }
        else{
            frontYard = Game.getInstance().day;
        }

        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        borderPane.setBackground(new Background(bgImage));

        VBox vbox = new VBox();
        borderPane.setLeft(vbox);
        buttonsHandler(vbox);
        vbox.getChildren().add(gameController.getShovelStack());

        vbox.setLayoutX(250);
        vbox.setLayoutY(85);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(6);

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

        if(Game.isFog) addFog();

        stage.setTitle("plant vs zambies");
        scene = new Scene(borderPane, 1024, 626);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        checkCards();
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
        ImageView hypnoShroomView = new ImageView(hypnoShroom);
        ImageView iceShroomView = new ImageView(iceShroom);
        ImageView doomShroomView = new ImageView(doomShroom);
        ImageView graveView = new ImageView(grave);
        ImageView graveBusterView = new ImageView(graveBuster);
        ImageView puffShroomView = new ImageView(puffShroom);
        ImageView scaredyShroomView = new ImageView(scaredyShroom);
        sunflowerView.setFitHeight(CELL_SIZE); sunflowerView.setFitWidth(CELL_SIZE);
        peashooterView.setFitHeight(CELL_SIZE); peashooterView.setFitWidth(CELL_SIZE);
        snowpeaView.setFitHeight(CELL_SIZE); snowpeaView.setFitWidth(CELL_SIZE);
        tallnutImageView.setFitHeight(CELL_SIZE); tallnutImageView.setFitWidth(CELL_SIZE);
        wallnutImageView.setFitWidth(CELL_SIZE); wallnutImageView.setFitWidth(CELL_SIZE);
        repeaterView.setFitHeight(CELL_SIZE); repeaterView.setFitWidth(CELL_SIZE);
        jalapenoView.setFitHeight(CELL_SIZE); jalapenoView.setFitWidth(CELL_SIZE);
        cherrybombView.setFitHeight(CELL_SIZE); cherrybombView.setFitWidth(CELL_SIZE);
        hypnoShroomView.setFitHeight(CELL_SIZE);
        iceShroomView.setFitHeight(CELL_SIZE); iceShroomView.setFitWidth(CELL_SIZE);
        doomShroomView.setFitHeight(CELL_SIZE); doomShroomView.setFitWidth(CELL_SIZE);
        graveView.setFitHeight(CELL_SIZE-15); graveView.setFitWidth(CELL_SIZE-25);
        graveBusterView.setFitHeight(CELL_SIZE); graveBusterView.setFitWidth(CELL_SIZE);
        puffShroomView.setFitHeight(CELL_SIZE); puffShroomView.setFitWidth(50);
        //scaredyShroomView.setFitWidth(CELL_SIZE); scaredyShroomView.setFitWidth(50);

        StackPane cell = new StackPane();
        boolean isGrave = checkForGravePos(row,col);
        if(isGrave && Game.isNight) {
            cell.getChildren().add(graveView);
        }
        cell.setOnMouseClicked((MouseEvent e) -> {
            click(cell, row, col);
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

    private void createCardWithCooldown(int i, StackPane cardButton, Button b1, double cooldownSeconds) {

        Rectangle overlay = new Rectangle(b1.getWidth(), CELL_SIZE); // Adjust size to match button
        overlay.setFill(Color.color(0, 0, 0, 0.5)); // semi-transparent black
        overlay.setTranslateY(0);
        overlay.setViewOrder(-4); // Make sure it's above the button

        overlay.setVisible(false); // Hide until used
        cardButton.getChildren().addAll(overlay);
        cardButton.setAlignment(overlay, Pos.TOP_CENTER);
        b1.setDisable(true);
        overlay.setVisible(true);
        overlay.setHeight(cardButton.getHeight());
        overlay.setTranslateY(0);
        overlay.setOpacity(1.0);
        Game.getInstance().getClicked()[i] = true;
        Timeline shrink = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(overlay.heightProperty(), cardButton.getHeight())),
                new KeyFrame(Duration.millis(cooldownSeconds),
                        new KeyValue(overlay.heightProperty(), 0)));
        // Fade out opacity (optional)
        FadeTransition fade = new FadeTransition(Duration.millis(cooldownSeconds), overlay);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        shrink.setOnFinished(ev -> {
            b1.setDisable(false);
            overlay.setVisible(false);
            Game.getInstance().getClicked()[i] = false;
            Game.getInstance().getCT().set(i, 0);
        });
        shrink.jumpTo(Duration.millis(Game.getInstance().getCT().get(i)));
        fade.jumpTo(Duration.millis(Game.getInstance().getCT().get(i)));
        shrink.play();

        fade.play();
    }
    public void addSaveLoadButton(){
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/saveButton.png")));
        imageView.setFitWidth(150);
        imageView.setFitHeight(40);
        if(!Game.isNight){
            imageView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/saveGameBtton.png")));
            imageView.setFitHeight(60);
            imageView.setFitWidth(170);
        }

        Button saveButton = new Button();
        saveButton.setGraphic(imageView);
        saveButton.getStyleClass().add("button");

        VBox vbox = new VBox();
        vbox.getChildren().add(saveButton);
        vbox.setMaxSize(100, 100);

        //vbox.getChildren().add(loadButton);
        borderPane.setRight(vbox);
        saveButton.setOnAction(e -> {
            SaveLoadManager.saveGame("savedData.txt", gameController.totalScore);
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
        vbox.getChildren().add(Game.scoreStack);

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
        graveBusterImageView.setFitWidth(CELL_SIZE*1.5); graveBusterImageView.setFitHeight(CELL_SIZE);
        doomShroomImageView.setFitWidth(CELL_SIZE*1.5); doomShroomImageView.setFitHeight(CELL_SIZE);
        hypnoShroomImageView.setFitWidth(CELL_SIZE*1.5); hypnoShroomImageView.setFitHeight(CELL_SIZE);
        iceShroomImageView.setFitWidth(CELL_SIZE*1.5); iceShroomImageView.setFitHeight(CELL_SIZE);
        planternImageView.setFitWidth(CELL_SIZE*1.5); planternImageView.setFitHeight(CELL_SIZE);
        puffShroomImageView.setFitWidth(CELL_SIZE*1.5); puffShroomImageView.setFitHeight(CELL_SIZE);
        scaredyShroomImageView.setFitWidth(CELL_SIZE*1.5); scaredyShroomImageView.setFitHeight(CELL_SIZE);
        bloverCardImageView.setFitWidth(CELL_SIZE*1.5); bloverCardImageView.setFitHeight(CELL_SIZE);
        coffeeBeanImageView.setFitWidth(CELL_SIZE*1.5); coffeeBeanImageView.setFitHeight(CELL_SIZE);

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

        graveBusterButton.getStyleClass().add("button");
        graveBusterButton.setGraphic(graveBusterImageView);
        doomShroomButton.getStyleClass().add("button");
        doomShroomButton.setGraphic(doomShroomImageView);
        hypnoShroomButton.getStyleClass().add("button");
        hypnoShroomButton.setGraphic(hypnoShroomImageView);
        iceShroomButton.getStyleClass().add("button");
        iceShroomButton.setGraphic(iceShroomImageView);
        planternButton.getStyleClass().add("button");
        planternButton.setGraphic(planternImageView);
        puffShroomButton.getStyleClass().add("button");
        puffShroomButton.setGraphic(puffShroomImageView);
        scaredyShroomButton.getStyleClass().add("button");
        scaredyShroomButton.setGraphic(scaredyShroomImageView);
        bloverButton.getStyleClass().add("button");
        bloverButton.setGraphic(bloverCardImageView);
        coffeeBeanButton.getStyleClass().add("button");
        coffeeBeanButton.setGraphic(coffeeBeanImageView);

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
            else if (chosenCards.get(i).equals("Plantern")) {
                vbox.getChildren().add(planternPane);
            }
            else if(chosenCards.get(i).equals("PuffShroom")) {
                vbox.getChildren().add(puffShroomPane);
            }
            else if(chosenCards.get(i).equals("ScaredyShroom")) {
                vbox.getChildren().add(scaredyShroomPane);
            }
            else if(chosenCards.get(i).equals("IceShroom")) {
                vbox.getChildren().add(iceShroomPane);
            }
            else if(chosenCards.get(i).equals("GraveBuster")){
                vbox.getChildren().add(graveBusterPane);
            }
            else if(chosenCards.get(i).equals("DoomShroom")){
                vbox.getChildren().add(doomShroomPane);
            }
            else if(chosenCards.get(i).equals("CoffeeBean")){
                vbox.getChildren().add(coffeeBeanPane);
            }
            else if(chosenCards.get(i).equals("HypnoShroom")){
                vbox.getChildren().add(hypnoShroomPane);
            }
            else if(chosenCards.get(i).equals("Blover")){
                vbox.getChildren().add(bloverPane);
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
        hypnoShroomButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(10);
        });
        doomShroomButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(11);
        });
        iceShroomButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(12);
        });
        graveBusterButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(13);
        });
        puffShroomButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(14);
        });
        scaredyShroomButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(15);
        });
        bloverButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(16);
        });
        coffeeBeanButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(17);
        });
        planternButton.setOnAction(event -> {
            if (num.get() == 9) {
                gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
                scene.setCursor(Cursor.DEFAULT);
            }
            num.set(18);
        });

    }

    public void addFog(){
        for(int row = 0; row < 5; row++){
            for(int col = 4; col < 9; col++){
                ImageView fogView = new ImageView(new Image(getClass().getResourceAsStream("images/Plants/fog0.png")));
                fogView.setFitWidth(CELL_SIZE*2); fogView.setFitHeight(CELL_SIZE*2.2);
                fogView.setViewOrder(-1000);

                Fog fog = new Fog(row, col, fogView);
                fogs.add(fog);

                final int r = row;
                final int c = col;

                fogView.setOnMouseClicked(event -> {
                    click((StackPane) getNodeFromGrid(grid, c, r), r, c);
                });
                //StackPane fogStack = new StackPane();
                //fogStack.getChildren().add(fogView);
                //fogView.toFront(); fogStack.toFront();
                //grid.add(fogStack, col, row);
                borderPane.getChildren().add(fogView);
                fogView.setLayoutX(grid.getLayoutX()+col*80-10);
                fogView.setLayoutY(grid.getLayoutY()+row*90-50);
            }
        }
    }

    public void click(StackPane cell, int row, int col){

        ImageView sunflowerView = new ImageView(sunflower);
        ImageView peashooterView = new ImageView(peashooter);
        ImageView snowpeaView = new ImageView(snowpea);
        ImageView tallnutImageView = new ImageView(tallnut);
        ImageView wallnutImageView = new ImageView(wallnut);
        ImageView repeaterView = new ImageView(repeater);
        ImageView jalapenoView = new ImageView(jalapeno);
        ImageView cherrybombView = new ImageView(cherrybomb);
        ImageView hypnoShroomView = new ImageView(hypnoShroom);
        ImageView iceShroomView = new ImageView(iceShroom);
        ImageView doomShroomView = new ImageView(doomShroom);
        ImageView graveView = new ImageView(grave);
        ImageView graveBusterView = new ImageView(graveBuster);
        ImageView puffShroomView = new ImageView(puffShroom);
        ImageView scaredyShroomView = new ImageView(scaredyShroom);
        ImageView bloverView = new ImageView(blover);
        ImageView planternView = new ImageView(plantern);
        ImageView coffeeBeanView = new ImageView(coffeeBean);
        sunflowerView.setFitHeight(CELL_SIZE); sunflowerView.setFitWidth(CELL_SIZE);
        peashooterView.setFitHeight(CELL_SIZE); peashooterView.setFitWidth(CELL_SIZE);
        snowpeaView.setFitHeight(CELL_SIZE); snowpeaView.setFitWidth(CELL_SIZE);
        tallnutImageView.setFitHeight(CELL_SIZE); tallnutImageView.setFitWidth(CELL_SIZE);
        wallnutImageView.setFitWidth(CELL_SIZE); wallnutImageView.setFitWidth(CELL_SIZE);
        repeaterView.setFitHeight(CELL_SIZE); repeaterView.setFitWidth(CELL_SIZE);
        jalapenoView.setFitHeight(CELL_SIZE); jalapenoView.setFitWidth(CELL_SIZE);
        cherrybombView.setFitHeight(CELL_SIZE); cherrybombView.setFitWidth(CELL_SIZE);
        hypnoShroomView.setFitHeight(CELL_SIZE);
        iceShroomView.setFitHeight(CELL_SIZE); iceShroomView.setFitWidth(CELL_SIZE);
        doomShroomView.setFitHeight(CELL_SIZE); doomShroomView.setFitWidth(CELL_SIZE);
        graveView.setFitHeight(CELL_SIZE-15); graveView.setFitWidth(CELL_SIZE-25);
        graveBusterView.setFitHeight(CELL_SIZE); graveBusterView.setFitWidth(CELL_SIZE);
        puffShroomView.setFitHeight(CELL_SIZE); puffShroomView.setFitWidth(50);
        bloverView.setFitHeight(CELL_SIZE); bloverView.setFitWidth(CELL_SIZE);
        planternView.setFitWidth(CELL_SIZE); planternView.setFitWidth(CELL_SIZE);
        coffeeBeanView.setFitWidth(30); coffeeBeanView.setFitHeight(80);
        boolean isGrave = checkForGravePos(row,col);
//        if(isGrave){
//            cell.getChildren().add(graveView);
//        }
        if(num.intValue() == 13 && isGrave && gameController.totalScore >= 75){
            num.set(0);
            GraveBuster graveBuster = new GraveBuster(row, col, cell, graveBusterView, gravePosPairs);
            //plants.add(graveBuster); // don't need to bee add to plant (Zombies shouldn't eat it!)
            Game.getInstance().getCT().set(chosenCards.indexOf("GraveBuster"), 0);
            int i = chosenCards.indexOf("GraveBuster");
            createCardWithCooldown(i, graveBusterPane, graveBusterButton, 7500 - Game.getInstance().getCT().get(chosenCards.indexOf("GraveBuster")));
            gameController.reduceScore(75);
            return;
        }
        if (num.intValue() == 1 && cell.getChildren().size() == 0 && gameController.totalScore >= 50) {
            num.set(0);
            cell.getChildren().addAll(sunflowerView);
            Game.getInstance().getPlants().add(new Sunflower(row, col , cell, sunflowerView));
            Game.getInstance().getCT().set(chosenCards.indexOf("Sunflower"), 0);
            int i = chosenCards.indexOf("Sunflower");
            createCardWithCooldown(i,sunFlowerPane, sunflowerButton, 5000 - Game.getInstance().getCT().get(chosenCards.indexOf("Sunflower")));
            gameController.reduceScore(50);
            // get the exact position on sunflower to put sun
            Bounds boundsInScene = cell.localToScene(cell.getBoundsInLocal());
            double x = boundsInScene.getMinX();
            double y = boundsInScene.getMinY();

        } else if (num.intValue() == 2 && cell.getChildren().size() == 0 && gameController.totalScore >= 100) {
            num.set(0);
            //plants.add(new Peashooter(row, col , peashooterView));
            Game.getInstance().getPlants().add(new Peashooter(row, col , peashooterView));
            Game.getInstance().getCT().set(chosenCards.indexOf("Peashooter"), 0);
            int i = chosenCards.indexOf("Peashooter");
            createCardWithCooldown(i, peashooterPane, peashooterButton, 10000 - Game.getInstance().getCT().get(chosenCards.indexOf("Peashooter")));
            cell.getChildren().addAll(peashooterView);
            gameController.reduceScore(100);
        }
        else if(num.intValue() == 3 && cell.getChildren().size() == 0 && gameController.totalScore >= 175) {
            num.set(0);
            Game.getInstance().getPlants().add(new SnowPea(row, col , snowpeaView));
            Game.getInstance().getCT().set(chosenCards.indexOf("SnowPea"), 0);
            int i = chosenCards.indexOf("SnowPea");
            createCardWithCooldown(i, snowpeaPane, snowpeaButton, 17500);
            cell.getChildren().addAll(snowpeaView);
            gameController.reduceScore(175);
        }
        else if(num.intValue() == 4 && cell.getChildren().size() == 0 && gameController.totalScore >= 125) {
            num.set(0);
            TallNut tallNut = new TallNut(row, col, tallnutImageView, cell);
            plants.add(tallNut);
            Game.getInstance().getCT().set(chosenCards.indexOf("TallNut"), 0);
            int i = chosenCards.indexOf("TallNut");
            createCardWithCooldown(i, tallnutPane, tallnutButton, 12500);
            //cell.getChildren().addAll(tallnutImageView);
            gameController.reduceScore(125);
        }
        else if(num.intValue() == 5 && cell.getChildren().size() == 0 && gameController.totalScore >= 50) {
            num.set(0);
            WallNut wallNut = new WallNut(row, col, wallnutImageView, cell);
            plants.add(wallNut);
            Game.getInstance().getCT().set(chosenCards.indexOf("WallNut"), 0);
            int i = chosenCards.indexOf("WallNut");
            createCardWithCooldown(i, wallnutPane, wallnutButton, 5000);
            //cell.getChildren().addAll(wallnutImageView);
            gameController.reduceScore(50);
        }
        else if(num.intValue() == 6 && cell.getChildren().size() == 0 && gameController.totalScore >= 200) {
            num.set(0);
            Game.getInstance().getPlants().add(new RepeaterPeaShooter(row, col , repeaterView));
            Game.getInstance().getCT().set(chosenCards.indexOf("RepeaterPeaShooter"), 0);
            int i = chosenCards.indexOf("RepeaterPeaShooter");
            createCardWithCooldown(i, repeaterPane, repeaterButton, 10000);
            cell.getChildren().addAll(repeaterView);
            gameController.reduceScore(200);
        }
        else if(num.intValue() == 7 && cell.getChildren().size() == 0 && gameController.totalScore >= 125) {
            num.set(0);
            Jalapeno jala = new Jalapeno(row, col, cell, jalapenoView);
            plants.add(jala);
            Game.getInstance().getCT().set(chosenCards.indexOf("Jalapeno"), 0);
            int i = chosenCards.indexOf("Jalapeno");
            createCardWithCooldown(i, jalapenoPane, jalapenoButton, 5000);
            //cell.getChildren().addAll(jalapenoView);
            gameController.reduceScore(125);
        }
        else if(num.intValue() == 8 && cell.getChildren().size() == 0 && gameController.totalScore >= 150) {
            num.set(0);
            CherryBomb cherry = new CherryBomb(row, col,this,  cell, cherrybombView);
            //gridCells[row][col].setPlant(cherry);
            plants.add(cherry);
            Game.getInstance().getCT().set(chosenCards.indexOf("CherryBomb"), 0);
            int i = chosenCards.indexOf("CherryBomb");
            createCardWithCooldown(i, cherrybombPane, cherrybombButton,5000);
            //cell.getChildren().addAll(cherrybombView);
            gameController.reduceScore(150);
        }
        else if(num.intValue() == 9 && cell.getChildren().size() != 0 && !isGrave) {
            scene.setCursor(Cursor.DEFAULT);
            Game.getInstance().removePlant(findPlantAt(row, col));
            cell.getChildren().clear();
            gameController.getShovelStack().getChildren().add(gameController.getShovelImage());
            num.set(0);
        }
        else if(num.intValue() == 10 && cell.getChildren().size() == 0 && gameController.totalScore >= 75) {
            num.set(0);
            HypnoShroom h = new HypnoShroom(row, col, hypnoShroomView);
            plants.add(h);
            //gridCells[row][col].setPlant(h);
            Game.getInstance().getCT().set(chosenCards.indexOf("HypnoShroom"), 0);
            int i = chosenCards.indexOf("HypnoShroom");
            createCardWithCooldown(i, hypnoShroomPane, hypnoShroomButton,5000 );
            gameController.reduceScore(75);
            cell.getChildren().add(hypnoShroomView);
        }
        else if(num.intValue() == 11 && cell.getChildren().size() == 0 && gameController.totalScore >= 125) {
            num.set(0);
            DoomShroom doom = new DoomShroom(row, col, cell, doomShroomView);
            plants.add(doom);
            gameController.reduceScore(125);
            Game.getInstance().getCT().set(chosenCards.indexOf("DoomShroom"), 0);
            int i = chosenCards.indexOf("DoomShroom");
            createCardWithCooldown(i, doomShroomPane, doomShroomButton, 15000);
        }
        else if(num.intValue() == 12 && cell.getChildren().size() == 0 && gameController.totalScore >= 75) {
            num.set(0);
            IceShroom ice = new IceShroom(row, col, cell, iceShroomView);
            plants.add(ice);
            gameController.reduceScore(75);
            Game.getInstance().getCT().set(chosenCards.indexOf("IceShroom"), 0);
            int i = chosenCards.indexOf("IceShroom");
            createCardWithCooldown(i, iceShroomPane, iceShroomButton,7500);
        }
        else if(num.intValue() == 14 && cell.getChildren().size() == 0) {
            num.set(0);
            PuffShroom puffShroom = new PuffShroom(row, col, puffShroomView);
            cell.getChildren().add(puffShroomView);
            plants.add(puffShroom);
            Game.getInstance().getCT().set(chosenCards.indexOf("PuffShroom"), 0);
            int i = chosenCards.indexOf("PuffShroom");
            createCardWithCooldown(i, puffShroomPane, puffShroomButton,puffShroom.rechargeTime * 1000);
        }
        else if(num.intValue() == 15 && cell.getChildren().size() == 0 && gameController.totalScore >= 25){
            num.set(0);
            ScaredyShroom scaredy = new ScaredyShroom(row, col, scaredyShroomView);
            plants.add(scaredy);
            cell.getChildren().add(scaredy.view);
            gameController.reduceScore(25);
            Game.getInstance().getCT().set(chosenCards.indexOf("ScaredyShroom"), 0);
            int i = chosenCards.indexOf("ScaredyShroom");
            createCardWithCooldown(i, scaredyShroomPane, scaredyShroomButton, scaredy.rechargeTime * 1000);
        }
        else if(num.intValue() == 16 && cell.getChildren().size() == 0 && gameController.totalScore >= 100){
            num.set(0);
            Blover b = new Blover(row, col, cell, bloverView);
            cell.getChildren().add(b.view);
            Game.getInstance().getCT().set(chosenCards.indexOf("Blover"), 0);
            gameController.reduceScore(100);
            int i = chosenCards.indexOf("Blover");
            createCardWithCooldown(i, bloverPane, bloverButton, b.rechargeTime * 1000);
        }
        else if(num.intValue() == 18 && cell.getChildren().size() == 0 && gameController.totalScore >= 25){
            num.set(0);
            Plantern p = new Plantern(row, col, planternView);
            plants.add(p);
            cell.getChildren().add(p.view);
            Game.getInstance().getCT().set(chosenCards.indexOf("Plantern"), 0);
            gameController.reduceScore(25);
            int i = chosenCards.indexOf("Plantern");
            createCardWithCooldown(i, planternPane, planternButton, p.rechargeTime * 1000);
        }
        else if(num.intValue() == 17 && cell.getChildren().size() != 0 && gameController.totalScore >= 75
                && findPlantAt(row, col).isNightPlant && findPlantAt(row, col).isSleeping){
            num.set(0);
            CoffeeBean coffeeBean = new CoffeeBean(row, col, cell, coffeeBeanView, findPlantAt(row, col));
            cell.getChildren().add(coffeeBean.view);
            gameController.reduceScore(75);
            int i = chosenCards.indexOf("CoffeeBean");
            createCardWithCooldown(i, coffeeBeanPane, coffeeBeanButton, coffeeBean.rechargeTime * 1000);
        }
    }

    public Node getNodeFromGrid(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            // اگر null بود یعنی پیش‌فرضش صفره
            if (columnIndex == null) columnIndex = 0;
            if (rowIndex == null) rowIndex = 0;

            if (columnIndex == col && rowIndex == row && node.getClass().getSimpleName().equals("StackPane")) {
                //System.out.println("HEHEHEHEHHEHEHEHEHHEHEH!!!");
                return node;
            }
        }

        return null; // اگه چیزی پیدا نشد
    }

    private void checkCards(){
        for (int i = 0; i < chosenCards.size(); i++) {
            if (Game.getInstance().getCT().get(i) != 0) {
                System.out.println("Hahahaha" + Game.getInstance().getCT().get(i));
                if (chosenCards.get(i).equals("Sunflower")) {
                    createCardWithCooldown(i, sunFlowerPane, sunflowerButton, 5000);
                } else if (chosenCards.get(i).equals("Peashooter")) {
                    createCardWithCooldown(i, peashooterPane, peashooterButton, 5000);
                } else if (chosenCards.get(i).equals("SnowPea")) {
                    createCardWithCooldown(i, snowpeaPane, snowpeaButton, 17500);
                } else if (chosenCards.get(i).equals("TallNut")) {
                    createCardWithCooldown(i, tallnutPane, tallnutButton, 12500);
                } else if (chosenCards.get(i).equals("WallNut")) {
                    createCardWithCooldown(i, wallnutPane, wallnutButton, 5000);

                } else if (chosenCards.get(i).equals("RepeaterPeaShooter")) {
                    createCardWithCooldown(i, repeaterPane, repeaterButton, 5000);
                } else if (chosenCards.get(i).equals("Jalapeno")) {
                    createCardWithCooldown(i, jalapenoPane, jalapenoButton, 5000);
                } else if (chosenCards.get(i).equals("CherryBomb")) {
                    createCardWithCooldown(i, cherrybombPane, cherrybombButton, 5000);
                } else if (chosenCards.get(i).equals("Plantern")) {
                    createCardWithCooldown(i, planternPane, planternButton, 5000);
                } else if (chosenCards.get(i).equals("PuffShroom")) {
                    createCardWithCooldown(i, puffShroomPane, puffShroomButton, 5000);

                } else if (chosenCards.get(i).equals("ScaredyShroom")) {
                    createCardWithCooldown(i, puffShroomPane, puffShroomButton, 5000);
                } else if (chosenCards.get(i).equals("IceShroom")) {
                    createCardWithCooldown(i, iceShroomPane, iceShroomButton, 5000);

                } else if (chosenCards.get(i).equals("GraveBuster")) {
                    createCardWithCooldown(i, graveBusterPane, graveBusterButton, 5000);
                } else if (chosenCards.get(i).equals("DoomShroom")) {
                    createCardWithCooldown(i, doomShroomPane, doomShroomButton, 5000);
                } else if (chosenCards.get(i).equals("CoffeeBean")) {
                    createCardWithCooldown(i, coffeeBeanPane, coffeeBeanButton, 5000);

                } else if (chosenCards.get(i).equals("HypnoShroom")) {
                    createCardWithCooldown(i, hypnoShroomPane, hypnoShroomButton, 5000);
                } else if (chosenCards.get(i).equals("Blover")) {
                    createCardWithCooldown(i, bloverPane, bloverButton, 5000);
                }
            }
        }
    }

    public void findPosForGraves(){
        Random random = new Random();
        Set<String> usedPairs = new HashSet<>();
        int count = 0;
        while (count < 5) {
            int first = random.nextInt(5);
            int second = random.nextInt(9);
            String key = first + "," + second;
            if (!usedPairs.contains(key)) {
                usedPairs.add(key);
                gravePosPairs[count][0] = first;
                gravePosPairs[count][1] = second;
                count++;
            }
        }
        if(Game.getInstance().isServer) {
            StringBuilder ms = new StringBuilder("gr ");
            for (int i = 0; i < gravePosPairs.length; i++) {
                ms.append(gravePosPairs[i][0]).append(" ");
                ms.append(gravePosPairs[i][1]).append(" ");
            }
            Game.getInstance().getServer().getOut().println(ms);
        }
    }
    public int[][] getGravePosPairs() {
        return gravePosPairs;
    }

    public boolean checkForGravePos(int row, int col){
        for(int i = 0; i < 5; i++){
            if(gravePosPairs[i][0] == row && gravePosPairs[i][1] == col){
                System.out.println("found a grave!");
                return true;
            }
        }
        return false;

    }
    public Scene getScene(){
        return scene;
    }
}