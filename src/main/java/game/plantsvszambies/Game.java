package game.plantsvszambies;

import javafx.animation.*;
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
    public int[] score = {0};
    private long lastFrameTime = 0;
    private double accumulatedTime = 0;

    private static final double FRAME_TIME = 1.0 / 60.0;
    ArrayList<String> chosenCards = new ArrayList<String>();
    private List<Bullet> activeBullets = new ArrayList<>();
    private long startTime;
    private long time;
    private static final double SPAWN_INTERVAL1 = 3.0;
    private static final double SPAWN_INTERVAL2 = 2.0;
    private static final double SPAWN_INTERVAL3 = 1.8;
    private int currentPhase = 0;
    private Timeline spawnTimeline;
    private List<Zombie> zombies = new ArrayList<>();
    private List<Zombie> Hzombies = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private boolean durAt = false;
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
    public boolean getDurAt() {
        return durAt;
    }
    public List<Zombie> getHzombies() {
        return Hzombies;
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

        boolean[] checkButtonPressed = {false, false, false, false, false, false, false, false, false};

        sunflowerButton.setOnAction(e->{
            if(!checkButtonPressed[0]){
                checkButtonPressed[0] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("Sunflower");
                    sunflowerImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[0] = false;
                chosenCards.remove("Sunflower");
                sunflowerImageView.setOpacity(0.5);
            }

        });
        peashooterButton.setOnAction(e -> {
            if(!checkButtonPressed[1]){
                checkButtonPressed[1] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("Peashooter");
                    peashooterImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[1] = false;
                chosenCards.remove("Peashooter");
                peashooterImageView.setOpacity(0.5);
            }
        });
        snowpeaButton.setOnAction(e ->{
            if(!checkButtonPressed[2]){
                checkButtonPressed[2] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("SnowPea");
                    snowpeaImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[2] = false;
                chosenCards.remove("SnowPea");
                snowpeaImageView.setOpacity(0.5);
            }
        });
        tallnutButton.setOnAction(e ->{
            if(!checkButtonPressed[3]){
                checkButtonPressed[3] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("TallNut");
                    tallnutImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[3] = false;
                chosenCards.remove("TallNut");
                tallnutImageView.setOpacity(0.5);
            }
        });
        wallnutButton.setOnAction(e -> {
            if(!checkButtonPressed[4]){
                checkButtonPressed[4] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("WallNut");
                    wallnutImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[4] = false;
                chosenCards.remove("WallNut");
                wallnutImageView.setOpacity(0.5);
            }
        });
        cherrybombButton.setOnAction(e ->{
            if(!checkButtonPressed[5]){
                checkButtonPressed[5] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("CherryBomb");
                    cherrybombImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[5] = false;
                chosenCards.remove("CherryBomb");
                cherrybombImageView.setOpacity(0.5);
            }
        });
        jalapenoButton.setOnAction(e ->{
            if(!checkButtonPressed[6]){
                checkButtonPressed[6] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("Jalapeno");
                    jalapenoImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[6] = false;
                chosenCards.remove("Jalapeno");
                jalapenoImageView.setOpacity(0.5);
            }
        });
        repeaterButton.setOnAction(e ->{
            if(!checkButtonPressed[7]){
                checkButtonPressed[7] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("RepeaterPeaShooter");
                    repeaterImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[7] = false;
                chosenCards.remove("RepeaterPeaShooter");
                repeaterImageView.setOpacity(0.5);
            }
        });
        Image startGameImage = new Image(getClass().getResourceAsStream("images/button_menus/startgame.png"));
        ImageView startGameView = new ImageView(startGameImage);
        Image loadImage = new Image(getClass().getResourceAsStream("images/button_menus/loadgame.png"));
        ImageView loadView = new ImageView(loadImage);
        Button startButton = new Button();
        startButton.getStyleClass().add("button");
        startButton.setGraphic(startGameView);
        startButton.setOnAction(e->{
            if(chosenCards.size() == 6){
                System.out.println("start Game");
                startGame();
            }
        });
        Button loadButton = new Button();
        loadButton.getStyleClass().add("button");
        loadButton.setGraphic(loadView);
        loadButton.setOnAction(e->{
            loadGame();
        });
        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(-40);
        hbox3.getChildren().addAll(startButton, loadButton);
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
        vbox.getChildren().addAll(label, hbox1, hbox2, hbox3);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        pane.getChildren().addAll(vbox);
        ChooseCardScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(ChooseCardScene);
        stage.show();
    }
    public void startGame(){
        startTime = System.currentTimeMillis();
        time = 0;
        if(this.map == null)
        this.map = new Mapp(stage, chosenCards, plants);
        map.drawMap();
        //setupSpawnTimer();
        setupAttackPhases();
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
        spawner();
        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> spawner())
        );
        spawnTimeline.setCycleCount(3);
        spawnTimeline.play();
    }
    private void setupSpawnTimer(long time) {
        int phase = (int) (time / 15000) + 1;

        int remainingTimeMil = (15000 - ((int)time % 15000));
        int count = 4 - phase;
            spawner(remainingTimeMil);

        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> spawner(15000))
        );
        spawnTimeline.setCycleCount(count);
        spawnTimeline.jumpTo(Duration.millis(15000 - remainingTimeMil));
        spawnTimeline.play();
    }
    private void spawner() {
        double du = 0;
        if (getCurrentPhase() == 1) {
            du = SPAWN_INTERVAL1;
        }
        else if (getCurrentPhase() == 2) {
            du = SPAWN_INTERVAL2;
        }
        else if (getCurrentPhase() == 3) {
            du = SPAWN_INTERVAL3;
        }
        else if (getCurrentPhase() == 4) {
            du = SPAWN_INTERVAL3;
        }
        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(du), e -> spawnZombie())
        );
        spawnTimeline.setCycleCount((int) (15/du));
        spawnTimeline.play();
    }
    private void spawner(int remainingTimeMil) {
        System.out.println("spawner");
        double du = 0;
        if (getCurrentPhaseS() == 1) {
            du = SPAWN_INTERVAL1;
        }
        else if (getCurrentPhaseS() == 2) {
            du = SPAWN_INTERVAL2;
        }
        else if (getCurrentPhaseS() == 3) {
            du = SPAWN_INTERVAL3;
        }
        else if (getCurrentPhaseS() == 4) {
            du = SPAWN_INTERVAL3;
        }
        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(du), e -> spawnZombie())
        );
        spawnTimeline.setCycleCount((int) (remainingTimeMil/(du * 1000)));
        spawnTimeline.play();
    }
    private void spawnZombie() {
        int currentPhase = getCurrentPhaseS();
        int row = (new Random()).nextInt(5);
        Zombie zombie = ZombieFactory.createRandomZombie(currentPhase, row);
        zombies.add(zombie);
        map.borderPane.getChildren().add(zombie.getView());
        positionZombie(zombie);
    }
    private void setupAttackPhases() {

        Timeline atTimeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> createAttackPhase()));
        atTimeline.setCycleCount(2);
        atTimeline.play();
    }
    private void  setupAttackPhaseS(){
        if (time <= 28655 || (time > 34255 && time <= 57919)) {
            Timeline atTimeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> createAttackPhase()));
            atTimeline.setCycleCount(2);
            atTimeline.jumpTo(Duration.millis(time));
            atTimeline.play();
        }
        else if(time <= 34255) {
            int c = (int) ((34255 - time) / 280);
            createAttackPhase(c);
            Timeline atTimeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> createAttackPhase()));
            atTimeline.setCycleCount(2);
            atTimeline.jumpTo(Duration.millis(time));
            atTimeline.play();
        }
        else if(time < 63519) {
            int c = (int) ((63519 - time) / 280);
            createAttackPhase(c);
        }

    }
    public void loadGame(){
        plants.clear();
        zombies.clear();
        chosenCards.clear();
        this.map = new Mapp(stage, chosenCards, plants);
        SaveLoadManager.loadGame("savedData.txt", plants, zombies, chosenCards, score, time);
        map.setChosenCards(chosenCards);
        map.setPlants((ArrayList<Plant>) plants);
        startTime = System.currentTimeMillis();
        map.gameController.totalScore = score[0];   // score logic update
        map.gameController.UpdateScoreLabel(score[0]); // score label update
        for(Plant p: Game.getInstance().getPlants()){  // draw plants on the scene
            if(p != null && p.view != null){
                if(p.getClass().getSimpleName().equals("WallNut") || p.getClass().getSimpleName().equals("TallNut")){
                    map.plantss[p.row][p.col] = p;
                }
                else if(p.getClass().getSimpleName().equals("Sunflower")){
                    map.plantss[p.row][p.col] = p;
                }
                else{
                    map.plantss[p.row][p.col] = p;
                }
            }
        }
        map.drawMap();
        for(Zombie z: zombies){
            map.borderPane.getChildren().add(z.getView());
        }
        //setupSpawnTimer(time);
        setupAttackPhaseS();
        startGameLoop();
    }
    private void createAttackPhase() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.28), e -> { int phase = getCurrentPhaseS();int row = (new Random()).nextInt(5);
            Zombie zombie = ZombieFactory.createRandomZombie(phase, row);
            zombies.add(zombie);
            map.borderPane.getChildren().add(zombie.getView());
            positionZombie(zombie);}));
        timeline.setCycleCount(20);
        timeline.play();
        System.out.println(time);
    }
    private void createAttackPhase(int c) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.28), e -> { int phase = getCurrentPhaseS();int row = (new Random()).nextInt(5);
            Zombie zombie = ZombieFactory.createRandomZombie(phase, row);
            zombies.add(zombie);
            map.borderPane.getChildren().add(zombie.getView());
            positionZombie(zombie);}));
        timeline.setCycleCount(c);
        timeline.play();
    }

    public int getCurrentPhase() {
        double elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0;
        if (elapsedSeconds >= 60) return 4;
        return (int) (elapsedSeconds / 15) + 1;
    }
    public int getCurrentPhaseS() {
        double elapsedSeconds = time / 1000.0;
        if (elapsedSeconds >= 60) return 4;
        return (int) (elapsedSeconds / 15) + 1;
    }
    public long getTime(){
        return time;
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
                time += (long) (deltaTime * 1000);
                System.out.println(time);
                currentPhase = getCurrentPhaseS();
                updatePlants(deltaTime);
                updateHZombies(deltaTime);
                checkZombiePlantCollisions();
                checkZombieHzombieCollisions();
                updateBullets(deltaTime);
                updateZombies(deltaTime);
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
    public void checkZombieHzombieCollisions() {
        for (Zombie zombie : zombies) {
            if (!zombie.isEating) {
                Zombie hZombie = findHzombieAt(zombie.getRow(), zombie.getColumn());
                if (hZombie != null) {
                    zombie.setHEat(true);
                    hZombie.setHEat(true);
                    zombie.startEating();
                    hZombie.startEating();

                }
            }
        }
    }
    private Zombie findHzombieAt(int row, double column) {
        return Hzombies.stream()
                .filter(p -> p.getRow() == row)
                .filter(p -> (Math.abs(column - p.getColumn()) <= 0.3 && Math.abs(column - p.getColumn()) >= 0.2))
                .findFirst()
                .orElse(null);
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
                .filter(entry -> entry.getKey() != null)
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
        for(Iterator<Plant> iterator = plants.iterator(); iterator.hasNext();) {
            Plant plant = iterator.next();
            if (plant instanceof HypnoShroom) {
                if (plant.getHealth() <= 0) {
                    iterator.remove();
                    removePlant(plant);
                }
            }
            plant.update(deltaTime);
        }
    }
    private void updateHZombies(double deltaTime){
      for (Iterator<Zombie> iterator = Hzombies.iterator(); iterator.hasNext();) {
          Zombie zombie = iterator.next();
          zombie.update(deltaTime);
          if (zombie.getColumn() > 9.3) iterator.remove();
      }
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
        if (plant.getClass().getSimpleName().equals("Sunflower")) {
            ((Sunflower) plant).stop();
        }
        plants.remove(plant);
        if(plant.view.getParent() != null)
        ((StackPane)plant.view.getParent()).getChildren().remove(plant.view);
    }
    public List<Zombie> getHZombies() {
        return Hzombies;
    }
    public void removeHZombie(Zombie zombie) {
        Hzombies.remove(zombie);
        map.borderPane.getChildren().remove(zombie.getView());
    }

public void clearGame(){
    for(Plant p : Game.getInstance().getPlants()){
        ((StackPane)p.view.getParent()).getChildren().remove(p.view);
    }
    plants.clear();
}
    public Plant createPlantByType(String type, int row, int col) {
        Image sunflower = new Image(getClass().getResourceAsStream("images/Plants/sunflower.gif"));
        Image peashooter = new Image(getClass().getResourceAsStream("images/Plants/peashooter.gif"));
        Image snowpea = new Image(getClass().getResourceAsStream("images/Plants/SnowPea.gif"));
        Image tallnut = new Image(getClass().getResourceAsStream("images/Plants/TallNut.gif"));
        Image wallnut = new Image(getClass().getResourceAsStream("images/Plants/wallnut.gif"));
        Image repeater = new Image(getClass().getResourceAsStream("images/Plants/repeater.gif"));
        Image jalapeno = new Image(getClass().getResourceAsStream("images/Plants/jalapeno.gif"));
        Image cherrybomb = new Image(getClass().getResourceAsStream("images/Plants/cherrybomb.gif"));
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
        StackPane cell = map.createCell(row, col);

        switch (type) {
            case "Sunflower": return new Sunflower(row, col, cell, sunflowerView);
            case "Peashooter": return new Peashooter(row, col, peashooterView);
            case "SnowPea": return new SnowPea(row, col, snowpeaView);
            case "WallNut": return new WallNut(row, col, wallnutImageView, cell);
            case "TallNut": return new TallNut(row, col, tallnutImageView, cell);
            case "RepeaterPeaShooter": return new RepeaterPeaShooter(row, col, repeaterView);
            // Add other types
            default: return null;
        }
    }
    public Zombie createZombieByType(String type, int row, double col) {
        ImageView normalZombie = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif")));
        ImageView impZombie = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
        ImageView screenDoorZombie = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/ScreendoorZombie.gif")));
        ImageView coneHeadZombie = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/coneheadzombie.gif")));
        switch (type) {
            case "NormalZombie": return new NormalZombie(row, col);
            case "ConeheadZombie": return new ConeheadZombie(row, col);
            case "ImpZombie": return new ImpZombie(row, col);
            case "ScreenDoorZombie": return new ScreenDoorZombie(row, col);
            default: return null;
        }
    }
    public void setTime(long l) {
        this.time = l;
    }
}