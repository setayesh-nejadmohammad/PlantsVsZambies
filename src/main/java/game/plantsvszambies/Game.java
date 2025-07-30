package game.plantsvszambies;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Map;


import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static Game instance;
    Stage stage;
    int CELL_SIZE = 80;
    Image frontYard;
    Mapp map;
    private long lastFrameTime = 0;
    private double accumulatedTime = 0;
    private static final double FRAME_TIME = 1.0 / 60.0;
    ArrayList<String> chosenCards = new ArrayList<String>();
    private List<Bullet> activeBullets = new ArrayList<>();
    private long startTime;
    private static final double SPAWN_INTERVAL = 3.0;
    private Timeline spawnTimeline;
    private List<Zombie> zombies = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private Map<Integer, List<Plant>> plantsByRow = new HashMap<Integer, List<Plant>>();

    public Plant findPlantBeingEaten(Zombie zombie) {
        List<Plant> plantsInRow = plantsByRow.getOrDefault(zombie.getRow(), Collections.emptyList());

        for (Plant plant : plantsInRow) {
            if (!plant.isDead()) {
                double distance = Math.abs(zombie.getColumn() - (plant.getCol() + 0.9));
                if (distance < 0.5) { // Overlap threshold
                    return plant;
                }
            }
        }
        return null;
    }

    public Game(Stage stage){
        frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
        this.stage = stage;
        ChooseCard();

    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(new Stage());
        }
        return instance;
    }

    public void addBullet(Bullet bullet) {
        activeBullets.add(bullet);
        map.borderPane.getChildren().add(bullet.getView());

    }
    private void sleepRemainingFrameTime(double actualDelta) {
        try {
            double targetTime = 1_000_000_000 / 60.0; // 16.67ms
            double elapsed = actualDelta * 1_000_000_000;
            if (elapsed < targetTime) {
                Thread.sleep((long)((targetTime - elapsed) / 1_000_000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void updateBullets(double deltaTime) {
        Iterator<Bullet> iterator = activeBullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();

            // Update position
            bullet.update(deltaTime);

            // Check collisions
            for (Zombie zombie : getZombiesInRow(bullet.getRow())) {
                if (bullet.checkCollision(zombie)) {
                    bullet.applyEffect(zombie);
                    iterator.remove();
                    map.borderPane.getChildren().remove(bullet.getView());
                    break;
                }
            }

            // Remove if out of bounds
            if (bullet.isOutOfBounds(map.borderPane.getWidth())) {
                iterator.remove();
                map.borderPane.getChildren().remove(bullet.getView());
            }
        }
    }
    private List<Zombie> getZombiesInRow(int row) {
        return zombies.stream()
                .filter(z -> z.getRow() == row)
                .collect(Collectors.toList());
    }
    public List<Plant> getPlants() {
        return plants;
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

        boolean[] checkButtonPressed = {false, false, false, false, false, false, false, false};

        sunflowerButton.setOnAction(e->{
            if(!checkButtonPressed[0]){
                checkButtonPressed[0] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("sunflower");
                    sunflowerImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[0] = false;
                chosenCards.remove("sunflower");
                sunflowerImageView.setOpacity(0.5);
            }

        });
        peashooterButton.setOnAction(e -> {
            if(!checkButtonPressed[1]){
                checkButtonPressed[1] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("peashooter");
                    peashooterImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[1] = false;
                chosenCards.remove("peashooter");
                peashooterImageView.setOpacity(0.5);
            }
        });
        snowpeaButton.setOnAction(e ->{
            if(!checkButtonPressed[2]){
                checkButtonPressed[2] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("snowpea");
                    snowpeaImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[2] = false;
                chosenCards.remove("snowpea");
                snowpeaImageView.setOpacity(0.5);
            }
        });
        tallnutButton.setOnAction(e ->{
            if(!checkButtonPressed[3]){
                checkButtonPressed[3] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("tallnut");
                    tallnutImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[3] = false;
                chosenCards.remove("tallnut");
                tallnutImageView.setOpacity(0.5);
            }
        });
        wallnutButton.setOnAction(e -> {
            if(!checkButtonPressed[4]){
                checkButtonPressed[4] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("wallnut");
                    wallnutImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[4] = false;
                chosenCards.remove("wallnut");
                wallnutImageView.setOpacity(0.5);
            }
        });
        cherrybombButton.setOnAction(e ->{
            if(!checkButtonPressed[5]){
                checkButtonPressed[5] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("cherrybomb");
                    cherrybombImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[5] = false;
                chosenCards.remove("cherrybomb");
                cherrybombImageView.setOpacity(0.5);
            }
        });
        jalapenoButton.setOnAction(e ->{
            if(!checkButtonPressed[6]){
                checkButtonPressed[6] = true;
                if (chosenCards.size() < 6) {
                    chosenCards.add("jalapeno");
                    jalapenoImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[6] = false;
                chosenCards.remove("jalapeno");
                jalapenoImageView.setOpacity(0.5);
            }
        });
        repeaterButton.setOnAction(e ->{
            if(!checkButtonPressed[7]){
                checkButtonPressed[7] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("repeater");
                    repeaterImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[7] = false;
                chosenCards.remove("repeater");
                repeaterImageView.setOpacity(0.5);
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
        this.map = new Mapp(stage, chosenCards, plants);
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

        Zombie zombie = ZombieFactory.createRandomZombie(currentPhase, row);
        zombies.add(zombie);
        map.borderPane.getChildren().add(zombie.getView());
        positionZombie(zombie);
    }

    private int getCurrentPhase() {
        double elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0;

        // Cap at phase 4 (60 seconds is end of game)
        if (elapsedSeconds >= 60) return 4;

        return (int) (elapsedSeconds / 15) + 1;
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                // Update all game systems
                updatePlants(deltaTime);
                checkZombiePlantCollisions();
                updateBullets(deltaTime);
                updateZombies(deltaTime);

                // Cap at 60 FPS if needed
//                try {
//                    Thread.sleep((long)(16.67 - deltaTime*1000));
//                } catch (InterruptedException e) {}
            }
        };
        gameLoop.start();
    }
    public void checkZombiePlantCollisions() {
        for (Zombie zombie : zombies) {
            // Check if zombie reached a plant's cell
            if (!zombie.isEating) {
                Plant plant = findPlantAt(zombie.getRow(), zombie.getColumn());
                if (plant != null) {
                    zombie.startEating();
                }
            }
        }
    }

    private Plant findPlantAt(int row, double column) {
        return plants.stream()
                .filter(p -> p.getRow() == row)
                .filter(p -> ((column - p.getCol()) <= 1.3 && column - p.getCol() >= 0.5))
                .findFirst()
                .orElse(null);
    }

    private void updateZombies(double deltaTime) {
        Map<Plant, List<Zombie>> zombiesByPlant = zombies.stream()
                .filter(Objects::nonNull)
                .filter(Zombie::isEating)
                .map(zombie -> new AbstractMap.SimpleEntry<>(findPlantBeingEaten(zombie), zombie))
                .filter(entry -> entry.getKey() != null)  // Explicit null-key removal
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toList()
                        )
                ));

        zombiesByPlant.forEach((plant, zombies) -> {
            if (plant != null) {
                plant.repositionAttackers();
            }
        });
        for (Iterator<Zombie> iterator = zombies.iterator(); iterator.hasNext();) {
            Zombie zombie = iterator.next();
            if (zombie.isEating()) {
                Plant target = findPlantBeingEaten(zombie);
                if (target != null) {
                    target.addAttacker(zombie);
                   // zombie.updateAttackPosition();
                }
            }
            zombie.update(deltaTime);
            if(checkReachedEnd(zombie)) {
                map.borderPane.getChildren().remove(zombie.getView());
                iterator.remove();

            }
        }
    }

    private void updatePlants(double deltaTime) {
        plants.forEach(plant -> {
            plant.update(deltaTime);

            // Plants automatically shoot via their update()
            // (ShooterPlant handles its own fire rate timing)
        });
    }

    private boolean checkReachedEnd(Zombie zombie) {
        return zombie.getColumn() <= 0;
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
        map.borderPane.getChildren().remove(zombie.getView());
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
        map.getGridPane().getChildren().remove(plant);
    }

//    private void checkCollisions(Zombie zombie) {
//
//    }


}