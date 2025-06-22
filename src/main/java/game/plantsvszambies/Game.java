package game.plantsvszambies;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static Game instance;
    Stage stage;
    int CELL_SIZE = 80;
    Image frontYard;
    Map map;
    ArrayList<String> chosenCards = new ArrayList<String>();
    private long startTime;
    private static final double SPAWN_INTERVAL = 3.0;
    private Timeline spawnTimeline;
    private List<Zombie> zombies = new ArrayList<>();


    public Game(Stage stage){
        frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
        this.stage = stage;
        ChooseCard();

    }

    public static Game getInstance(){
        if(instance == null){
            instance = new Game(new Stage());
        }
        return instance;
    }


    public void ChooseCard(){
        StackPane pane = new StackPane();
        Scene ChooseCardScene = new Scene(pane, 1024, 626);
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(bgImage));

        Image sunflowerCard = new Image(getClass().getResourceAsStream("images/Cards/sunflowerCard.png"));
        Image peashooterCard = new Image(getClass().getResourceAsStream("images/Cards/peashooterCard.png"));
        Image snowpeaCard = new Image(getClass().getResourceAsStream("images/Cards/snowpeaCard.jpg"));
        Image tallnutCard = new Image(getClass().getResourceAsStream("images/Cards/tallnutCard.jpg"));
        Image wallnutCard = new Image(getClass().getResourceAsStream("images/Cards/wallnutCard.png"));
        Image repeaterCard = new Image(getClass().getResourceAsStream("images/Cards/repeaterCard.png"));
        Image jalapenoCard = new Image(getClass().getResourceAsStream("images/Cards/jalapenoCard.png"));
        Image cherrybombCard = new Image(getClass().getResourceAsStream("images/Cards/cherrybombCard.png"));
        ImageView sunflowerImageView = new ImageView(sunflowerCard);
        ImageView peashooterImageView = new ImageView(peashooterCard);
        ImageView snowpeaImageView = new ImageView(snowpeaCard);
        ImageView tallnutImageView = new ImageView(tallnutCard);
        ImageView wallnutImageView = new ImageView(wallnutCard);
        ImageView repeaterImageView = new ImageView(repeaterCard);
        ImageView jalapenoImageView = new ImageView(jalapenoCard);
        ImageView cherrybombImageView = new ImageView(cherrybombCard);
        sunflowerImageView.setFitWidth(CELL_SIZE*1.5); sunflowerImageView.setFitHeight(CELL_SIZE);
        peashooterImageView.setFitWidth(CELL_SIZE*1.5); peashooterImageView.setFitHeight(CELL_SIZE);
        snowpeaImageView.setFitWidth(CELL_SIZE*1.5); snowpeaImageView.setFitHeight(CELL_SIZE);
        tallnutImageView.setFitWidth(CELL_SIZE*1.5); tallnutImageView.setFitHeight(CELL_SIZE);
        wallnutImageView.setFitWidth(CELL_SIZE*1.5); wallnutImageView.setFitHeight(CELL_SIZE);
        repeaterImageView.setFitWidth(CELL_SIZE*1.5); repeaterImageView.setFitHeight(CELL_SIZE);
        jalapenoImageView.setFitWidth(CELL_SIZE*1.5); jalapenoImageView.setFitHeight(CELL_SIZE);
        cherrybombImageView.setFitWidth(CELL_SIZE*1.5); cherrybombImageView.setFitHeight(CELL_SIZE);

        sunflowerImageView.setOpacity(0.5);
        peashooterImageView.setOpacity(0.5);
        snowpeaImageView.setOpacity(0.5);
        tallnutImageView.setOpacity(0.5);
        cherrybombImageView.setOpacity(0.5);
        repeaterImageView.setOpacity(0.5);
        jalapenoImageView.setOpacity(0.5);
        wallnutImageView.setOpacity(0.5);

        Button sunflowerButton = new Button();
        Button peashooterButton = new Button();
        Button snowpeaButton = new Button();
        Button tallnutButton = new Button();
        Button cherrybombButton = new Button();
        Button repeaterButton = new Button();
        Button jalapenoButton = new Button();
        Button wallnutButton = new Button();

        sunflowerButton.getStyleClass().add("button");
        sunflowerButton.setGraphic(sunflowerImageView);
        peashooterButton.getStyleClass().add("button");
        peashooterButton.setGraphic(peashooterImageView);
        snowpeaButton.getStyleClass().add("button");
        snowpeaButton.setGraphic(snowpeaImageView);
        tallnutButton.getStyleClass().add("button");
        tallnutButton.setGraphic(tallnutImageView);
        cherrybombButton.getStyleClass().add("button");
        cherrybombButton.setGraphic(cherrybombImageView);
        repeaterButton.getStyleClass().add("button");
        repeaterButton.setGraphic(repeaterImageView);
        jalapenoButton.getStyleClass().add("button");
        jalapenoButton.setGraphic(jalapenoImageView);
        wallnutButton.getStyleClass().add("button");
        wallnutButton.setGraphic(wallnutImageView);

        sunflowerButton.setOnAction(e->{
            if(chosenCards.size() < 6){
                chosenCards.add("sunflower");
                sunflowerImageView.setOpacity(1);
            }
        });
        peashooterButton.setOnAction(e -> {
            if(chosenCards.size() < 6){
                chosenCards.add("peashooter");
                peashooterImageView.setOpacity(1);
            }
        });
        snowpeaButton.setOnAction(e ->{
            if(chosenCards.size() < 6){
                chosenCards.add("snowpea");
                snowpeaImageView.setOpacity(1);
            }
        });
        tallnutButton.setOnAction(e ->{
            if(chosenCards.size() < 6){
                chosenCards.add("tallnut");
                tallnutImageView.setOpacity(1);
            }
        });
        wallnutButton.setOnAction(e -> {
            if(chosenCards.size() < 6){
                chosenCards.add("wallnut");
                wallnutImageView.setOpacity(1);
            }
        });
        cherrybombButton.setOnAction(e ->{
            if(chosenCards.size() < 6){
                chosenCards.add("cherrybomb");
                cherrybombImageView.setOpacity(1);
            }
        });
        jalapenoButton.setOnAction(e ->{
            if(chosenCards.size() < 6){
                chosenCards.add("jalapeno");
                jalapenoImageView.setOpacity(1);
            }
        });
        repeaterButton.setOnAction(e ->{
            if(chosenCards.size() < 6){
                chosenCards.add("repeater");
                repeaterImageView.setOpacity(1);
            }
        });

        Image startGameImage = new Image(getClass().getResourceAsStream("images/button_menus/startgame.png"));
        ImageView startGameView = new ImageView(startGameImage);

        Button startButton = new Button();
        startButton.getStyleClass().add("button");
        startButton.setGraphic(startGameView);
        startButton.setOnAction(e->{
            if(chosenCards.size() == 6){
                System.out.println("start Game");
                startGame();
            }
        });




        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox1.setSpacing(10);
        hbox2.setSpacing(10);
        hbox1.getChildren().addAll(sunflowerButton, peashooterButton, snowpeaButton, tallnutButton);
        hbox2.getChildren().addAll(wallnutButton, repeaterButton, jalapenoButton, cherrybombButton);


        Label label = new Label("Choose 6 cards to play");
        //label.setPadding(new Insets(100));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, hbox1, hbox2, startButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        pane.getChildren().addAll(vbox);



        ChooseCardScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(ChooseCardScene);
        stage.show();
    }

    public void startGame(){
        startTime = System.currentTimeMillis();
        this.map = new Map(stage, chosenCards);
        map.drawMap();
        setupSpawnTimer();
        startGameLoop();
    }

    private void positionZombie(Zombie zombie) {
        ImageView view = zombie.getView();
        view.setTranslateX(180);
        int row = zombie.getRow();
        double x;
        if (row == 0)
            view.setLayoutY(map.grid.getLayoutY() + zombie.getRow() * 80 + zombie.getRow() * 8 - 10);
        else if (row >= 3)
            view.setLayoutY(map.grid.getLayoutY() + zombie.getRow() * 80 + zombie.getRow() * 8);

            else

        view.setLayoutY(map.grid.getLayoutY() + zombie.getRow() * 80 + zombie.getRow() * 4);

    }

    private void setupSpawnTimer() {
        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(SPAWN_INTERVAL), e -> spawnZombie())
        );
        spawnTimeline.setCycleCount(Animation.INDEFINITE);
        spawnTimeline.play();
    }
    private void spawnZombie() {
        int currentPhase = getCurrentPhase(); // Implement based on game time
        int row = (new Random()).nextInt(5); // Random row (0-4)

        Zombie zombie = ZombieFactory.createRandomZombie(currentPhase, row, map);
        zombies.add(zombie);
        map.borderPane.getChildren().add(zombie.getView());
        positionZombie(zombie);

        // add Zobmie to my grid
        Cell myCell = getCellFromGridPane(map.grid, 9, row);
        if(myCell != null) myCell.addZombie(zombie);
    }

    private int getCurrentPhase() {
        double elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0;

        // Cap at phase 4 (60 seconds is end of game)
        if (elapsedSeconds >= 60) return 4;

        return (int) (elapsedSeconds / 15) + 1;
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = 1.0 / 60; // Assuming 60 FPS

                // Update all zombies
                for (Zombie zombie : new ArrayList<>(zombies)){
                    zombie.update(deltaTime);
                    //checkCollisions(zombie);
                    checkReachedEnd(zombie);
                    //Map.updateZombies();
                }
            }
        };
        gameLoop.start();
    }

    public void updateZombiePosOnGrid(Zombie zombie) {
        //Cell myCell = getCellFromGridPane(map.grid, (int)zombie.getColumn(), zombie.getRow());
        //?????
    }

    private void checkReachedEnd(Zombie zombie) {
        if (zombie.getColumn() <= 0) {
            removeZombie(zombie);
        }
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
        map.borderPane.getChildren().remove(zombie.getView());
    }

//    private void checkCollisions(Zombie zombie) {
//
//    }

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

    public List<Zombie> getZombies() {
        return zombies;
    }

}
