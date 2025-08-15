package game.plantsvszambies;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
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
    public static boolean isNight = false;
    public static boolean isFog = false;
    public static StackPane scoreStack = new StackPane();
    private ArrayList<Integer> chargeTimes = new ArrayList<>();
    private boolean[] clicked = new boolean[6];
    private int startAttackTime = 0;
    public static boolean isServer = false;
    public static boolean isClient = false;
    private Server server;
    private Client client;
    private int lastSpawnTime = 0;
    private AnimationTimer gameLoop;
    private String IP = "";
    private Stage getIPStage;

    public Image day = new Image(getClass().getResourceAsStream("images/frontyard.png"));
    public Image night = new Image(getClass().getResourceAsStream("images/night1.png"));
    public Image ZombieStaring = new Image(getClass().getResourceAsStream("images/button_menus/StartgameBg.png"));
    public static boolean lost = false;
    public static boolean won = false;

    public void setStartAttackTime(int time) {
        this.startAttackTime = time;
    }
    public int getStartAttackTime(){
        return startAttackTime;
    }
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
    private CopyOnWriteArrayList<Zombie> zombies = new CopyOnWriteArrayList<>();
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
    public AnimationTimer getGameLoop() {
        return gameLoop;
    }
    public List<Zombie> getHzombies() {
        return Hzombies;
    }
    public void showGameOver(boolean playerWon) {
        Platform.runLater(() -> {
            GameEndScreen endScreen = new GameEndScreen();
            boolean playAgain = endScreen.show(playerWon, stage);

            if (playAgain) {
                stage.close();
                //ChooseMultiOrSingle();
                //instance = null;
                //Game game = Game.getInstance();
            } else {
                Platform.exit();
            }
        });
    }
    public Game(Stage stage){
        frontYard = new Image(getClass().getResourceAsStream("images/button_menus/startGameBg.png"));
        this.stage = stage;
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/button_menus/ZombieHead.png")));
        for(int i = 0; i < 6; i++){
            chargeTimes.add(0);
        }
        ChooseMultiOrSingle();
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

    public void startGameAsServer() { // Server
        try {
            server = new Server();
            server.start(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGame();
    }
    public void startGameAsClient() {  // Client  ... this should be different from main startGame
        try {
            client = new Client();
            client.start(IP, 5555); // we should get the ip ; (127.0.0.1) is for lockal host
        } catch (IOException e) {
            e.printStackTrace();
        }
        //startGame();
        // new StartGame for Client:
        startTime = System.currentTimeMillis();
        time = 0;
        if(chosenCards == null){
            List<String> elementsToAdd = Arrays.asList("Sunflower", "Peashooter", "WallNut", "Jalapeno", "SnowPea", "TallNut");
            chosenCards.addAll(elementsToAdd);
        }
        if(this.map == null)
            this.map = new Mapp(stage, chosenCards, plants);
        map.drawMap();

        startGameLoop();  // ??!!
    }
    private void AddIpStage(){
        getIPStage = new Stage();
        StackPane pane = new StackPane();
        TextField input = new TextField();
        Label label = new Label("Enter the Server IP: ");
        Button send = new Button("Submit");
        send.setOnAction(e->{
            IP = input.getText();
            getIPStage.close();
        });
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(label, input, send);
        pane.getChildren().addAll(hbox);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 500, 200);
        getIPStage.setScene(scene);
        getIPStage.show();
    }

    public void ChooseMultiOrSingle(){
        ImageView MultiView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/MultiplayerButton.png")));
        ImageView SingleView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/SingleplayerButton.png")));
        MultiView.setFitHeight(50); MultiView.setFitWidth(150);
        SingleView.setFitHeight(50); SingleView.setFitWidth(150);

        Button MultiPlayerButton = new Button();
        MultiPlayerButton.setAlignment(Pos.CENTER);
        MultiPlayerButton.setGraphic(MultiView);
        MultiPlayerButton.getStyleClass().add("button");

        Button SinglePlayerButton = new Button();
        SinglePlayerButton.setAlignment(Pos.CENTER);
        SinglePlayerButton.setGraphic(SingleView);
        SinglePlayerButton.getStyleClass().add("button");

        MultiPlayerButton.setOnAction(e -> {
            ChooseServerOrClient();
        });
        SinglePlayerButton.setOnAction(e -> {
            ChooseState();
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(MultiPlayerButton, SinglePlayerButton);
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        vbox.setLayoutX(195);
        vbox.setLayoutY(295);

        Pane pane = new Pane();
        pane.getChildren().add(vbox);
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(bgImage));
        Scene scene = new Scene(pane, 1024, 626);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void ChooseServerOrClient(){
        ImageView serverButtonView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/ServerButton.png")));
        ImageView clientButtonView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/ClientButton.png")));

        serverButtonView.setFitHeight(50); serverButtonView.setFitWidth(150);
        clientButtonView.setFitHeight(50); clientButtonView.setFitWidth(150);

        Pane pane = new Pane();
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(bgImage));
        Scene chooseServerScene = new Scene(pane, 1024, 626);

        Button serverButton = new Button();
        serverButton.getStyleClass().add("button");
        serverButton.setGraphic(serverButtonView);

        Button clientButton = new Button();
        clientButton.getStyleClass().add("button");
        clientButton.setGraphic(clientButtonView);
        serverButton.setOnAction(e -> {
            isServer = true;
            //startGameAsServer();
            //ChooseCard();
            ChooseState();
        });
        clientButton.setOnAction(e -> {
            isClient = true;
            //startGameAsClient();
            //ChooseCard();
            ChooseState();
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll();
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        vbox.setLayoutX(195);
        vbox.setLayoutY(295);

        vbox.getChildren().addAll(serverButton, clientButton);

        pane.getChildren().add(vbox);
        chooseServerScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(chooseServerScene);
        stage.show();
    }
    public void ChooseState(){
        Pane pane = new Pane();
        Scene ChooseStateScene = new Scene(pane, 1024, 626);
        frontYard = ZombieStaring;
        BackgroundImage bgImage = new BackgroundImage(
                frontYard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(bgImage));

        ImageView nightButtonView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/nightButton (2).png")));
        ImageView dayButtonView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/dayButton (2).png")));
        ImageView fogButtonView = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/fogButton (2).png")));
        nightButtonView.setFitHeight(50); nightButtonView.setFitWidth(150);
        dayButtonView.setFitHeight(50); dayButtonView.setFitWidth(150);
        fogButtonView.setFitHeight(50); fogButtonView.setFitWidth(150);
        Button chooseNight = new Button();
        chooseNight.setGraphic(nightButtonView);
        chooseNight.getStyleClass().add("button");

        Button chooseDay = new Button();
        chooseDay.setGraphic(dayButtonView);
        chooseDay.getStyleClass().add("button");

        Button chooseFog = new Button();
        chooseFog.setGraphic(fogButtonView);
        chooseFog.getStyleClass().add("button");

        Button multiplayerButton = new Button("Multiplayer");


        chooseDay.setOnAction(e -> {
            isNight = false;
            frontYard = day;
            ChooseCard();
        });
        chooseNight.setOnAction(e -> {
            isNight = true;
            frontYard = night;
            ChooseCard();
        });
        chooseFog.setOnAction(e -> {
            isNight = true;
            isFog = true;
            frontYard = night;
            ChooseCard();
        });
        multiplayerButton.setOnAction(e -> {
            ChooseServerOrClient();
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(chooseNight, chooseDay, chooseFog);
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        vbox.setLayoutX(195);
        vbox.setLayoutY(295);

        pane.getChildren().add(vbox);
        ChooseStateScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(ChooseStateScene);
        stage.show();
    }
    public void ChooseCard(){
        chosenCards.clear();
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
        Image graveBusterCard = new Image(getClass().getResourceAsStream("images/Cards/graveBusterCard.png"));
        Image doomShroomCard = new Image(getClass().getResourceAsStream("images/Cards/DoomShroomCard.png"));
        Image hypnoShroomCard = new Image(getClass().getResourceAsStream("images/Cards/HypnoShroomCard.png"));
        Image iceShroomCard = new Image(getClass().getResourceAsStream("images/Cards/IceShroomCard.jpg"));
        Image planternCard = new Image(getClass().getResourceAsStream("images/Cards/PlanternCard.png"));
        Image puffShroom = new Image(getClass().getResourceAsStream("images/Cards/PuffShroomCard.png"));
        Image scaredyShroom = new Image(getClass().getResourceAsStream("images/Cards/ScaredyShroomCard.png"));
        Image bloverCard = new Image(getClass().getResourceAsStream("images/Cards/BloverCard.png"));
        Image coffeeBean = new Image(getClass().getResourceAsStream("images/Cards/CoffeeBeanCard.png"));
        ImageView sunflowerImageView = new ImageView(sunflowerCard);
        ImageView peashooterImageView = new ImageView(peashooterCard);
        ImageView snowpeaImageView = new ImageView(snowpeaCard);
        ImageView tallnutImageView = new ImageView(tallnutCard);
        ImageView wallnutImageView = new ImageView(wallnutCard);
        ImageView repeaterImageView = new ImageView(repeaterCard);
        ImageView jalapenoImageView = new ImageView(jalapenoCard);
        ImageView cherrybombImageView = new ImageView(cherrybombCard);
        ImageView graveBusterImageView = new ImageView(graveBusterCard);
        ImageView doomShroomImageView = new ImageView(doomShroomCard);
        ImageView hypnoShroomImageView = new ImageView(hypnoShroomCard);
        ImageView iceShroomImageView = new ImageView(iceShroomCard);
        ImageView planternImageView = new ImageView(planternCard);
        ImageView puffShroomImageView = new ImageView(puffShroom);
        ImageView scaredyShroomImageView = new ImageView(scaredyShroom);
        ImageView bloverCardImageView = new ImageView(bloverCard);
        ImageView coffeeBeanImageView = new ImageView(coffeeBean);

        sunflowerImageView.setFitWidth(CELL_SIZE*1.5); sunflowerImageView.setFitHeight(CELL_SIZE);
        peashooterImageView.setFitWidth(CELL_SIZE*1.5); peashooterImageView.setFitHeight(CELL_SIZE);
        snowpeaImageView.setFitWidth(CELL_SIZE*1.5); snowpeaImageView.setFitHeight(CELL_SIZE);
        tallnutImageView.setFitWidth(CELL_SIZE*1.5); tallnutImageView.setFitHeight(CELL_SIZE);
        wallnutImageView.setFitWidth(CELL_SIZE*1.5); wallnutImageView.setFitHeight(CELL_SIZE);
        repeaterImageView.setFitWidth(CELL_SIZE*1.5); repeaterImageView.setFitHeight(CELL_SIZE);
        jalapenoImageView.setFitWidth(CELL_SIZE*1.5); jalapenoImageView.setFitHeight(CELL_SIZE);
        cherrybombImageView.setFitWidth(CELL_SIZE*1.5); cherrybombImageView.setFitHeight(CELL_SIZE);
        graveBusterImageView.setFitWidth(CELL_SIZE*1.5); graveBusterImageView.setFitHeight(CELL_SIZE);
        doomShroomImageView.setFitWidth(CELL_SIZE*1.5); doomShroomImageView.setFitHeight(CELL_SIZE);
        hypnoShroomImageView.setFitWidth(CELL_SIZE*1.5); hypnoShroomImageView.setFitHeight(CELL_SIZE);
        iceShroomImageView.setFitWidth(CELL_SIZE*1.5); iceShroomImageView.setFitHeight(CELL_SIZE);
        planternImageView.setFitWidth(CELL_SIZE*1.5); planternImageView.setFitHeight(CELL_SIZE);
        puffShroomImageView.setFitWidth(CELL_SIZE*1.5); puffShroomImageView.setFitHeight(CELL_SIZE);
        scaredyShroomImageView.setFitWidth(CELL_SIZE*1.5); scaredyShroomImageView.setFitHeight(CELL_SIZE);
        bloverCardImageView.setFitWidth(CELL_SIZE*1.5); bloverCardImageView.setFitHeight(CELL_SIZE);
        coffeeBeanImageView.setFitWidth(CELL_SIZE*1.5); coffeeBeanImageView.setFitHeight(CELL_SIZE);

        sunflowerImageView.setOpacity(0.5);
        peashooterImageView.setOpacity(0.5);
        snowpeaImageView.setOpacity(0.5);
        tallnutImageView.setOpacity(0.5);
        cherrybombImageView.setOpacity(0.5);
        repeaterImageView.setOpacity(0.5);
        jalapenoImageView.setOpacity(0.5);
        wallnutImageView.setOpacity(0.5);
        graveBusterImageView.setOpacity(0.5);
        doomShroomImageView.setOpacity(0.5);
        hypnoShroomImageView.setOpacity(0.5);
        iceShroomImageView.setOpacity(0.5);
        planternImageView.setOpacity(0.5);
        puffShroomImageView.setOpacity(0.5);
        scaredyShroomImageView.setOpacity(0.5);
        bloverCardImageView.setOpacity(0.5);
        coffeeBeanImageView.setOpacity(0.5);

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

        boolean[] checkButtonPressed = {false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false};

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
        graveBusterButton.setOnAction(e ->{
            if(!checkButtonPressed[8]){
                checkButtonPressed[8] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("GraveBuster");
                    graveBusterImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[8] = false;
                chosenCards.remove("GraveBuster");
                graveBusterImageView.setOpacity(0.5);
            }
        });
        doomShroomButton.setOnAction(e ->{
            if(!checkButtonPressed[9]){
                checkButtonPressed[9] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("DoomShroom");
                    doomShroomImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[9] = false;
                chosenCards.remove("DoomShroom");
                doomShroomImageView.setOpacity(0.5);
            }
        });
        hypnoShroomButton.setOnAction(e ->{
            if(!checkButtonPressed[10]){
                checkButtonPressed[10] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("HypnoShroom");
                    hypnoShroomImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[10] = false;
                chosenCards.remove("HypnoShroom");
                hypnoShroomImageView.setOpacity(0.5);
            }
        });
        iceShroomButton.setOnAction(e ->{
            if(!checkButtonPressed[11]){
                checkButtonPressed[11] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("IceShroom");
                    iceShroomImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[11] = false;
                chosenCards.remove("IceShroom");
                iceShroomImageView.setOpacity(0.5);
            }
        });
        planternButton.setOnAction(e ->{
            if(!checkButtonPressed[12]){
                checkButtonPressed[12] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("Plantern");
                    planternImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[12] = false;
                chosenCards.remove("Plantern");
                planternImageView.setOpacity(0.5);
            }
        });
        puffShroomButton.setOnAction(e ->{
            if(!checkButtonPressed[13]){
                checkButtonPressed[13] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("PuffShroom");
                    puffShroomImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[13] = false;
                chosenCards.remove("PuffShroom");
                puffShroomImageView.setOpacity(0.5);
            }
        });
        scaredyShroomButton.setOnAction(e ->{
            if(!checkButtonPressed[14]){
                checkButtonPressed[14] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("ScaredyShroom");
                    scaredyShroomImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[14] = false;
                chosenCards.remove("ScaredyShroom");
                scaredyShroomImageView.setOpacity(0.5);
            }
        });
        coffeeBeanButton.setOnAction(e ->{
            if(!checkButtonPressed[15]){
                checkButtonPressed[15] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("CoffeeBean");
                    coffeeBeanImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[15] = false;
                chosenCards.remove("CoffeeBean");
                coffeeBeanImageView.setOpacity(0.5);
            }
        });
        bloverButton.setOnAction(e ->{
            if(!checkButtonPressed[16]){
                checkButtonPressed[16] = true;
                if(chosenCards.size() < 6){
                    chosenCards.add("Blover");
                    bloverCardImageView.setOpacity(1);
                }
            }
            else{
                checkButtonPressed[16] = false;
                chosenCards.remove("Blover");
                bloverCardImageView.setOpacity(0.5);
            }
        });
        Image startGameImage = new Image(getClass().getResourceAsStream("images/button_menus/startgame.png"));
        ImageView startGameView = new ImageView(startGameImage);
        Image loadImage = new Image(getClass().getResourceAsStream("images/button_menus/loadgame.png"));
        ImageView loadView = new ImageView(loadImage);
        Image backImage = new Image(getClass().getResourceAsStream("images/button_menus/back.png"));
        ImageView backView = new ImageView(backImage);
        Button startButton = new Button();
        startButton.getStyleClass().add("button");
        startButton.setGraphic(startGameView);
        startButton.setOnAction(e->{
            if(chosenCards.size() == 6){
                System.out.println("start Game");
                if(isServer){
                    startGameAsServer();
                }
                else if(isClient){
                    startGameAsClient();
                }
                else {
                    startGame();
                }
            }
        });
        Button loadButton = new Button();
        loadButton.getStyleClass().add("button");
        loadButton.setGraphic(loadView);
        loadButton.setOnAction(e->{
            loadGame();
        });
        Button backButton = new Button();
        backButton.getStyleClass().add("button");
        backButton.setGraphic(backView);
        backButton.setOnAction(e->{
            ChooseState();
        });

        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(-40);
        hbox3.getChildren().addAll(startButton, loadButton, backButton);
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox4 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox4.setAlignment(Pos.CENTER);
        hbox1.setSpacing(10);
        hbox2.setSpacing(10);
        hbox4.setSpacing(10);
        hbox1.getChildren().addAll(sunflowerButton, peashooterButton, snowpeaButton, tallnutButton, wallnutButton, repeaterButton);
        hbox2.getChildren().addAll(jalapenoButton, cherrybombButton, doomShroomButton, graveBusterButton, hypnoShroomButton, iceShroomButton);
        hbox4.getChildren().addAll(planternButton, puffShroomButton, scaredyShroomButton, coffeeBeanButton, bloverButton);

        Label label = new Label("Choose 6 cards to play");
        if(isNight) label.setStyle("-fx-text-fill: white;");
        //label.setPadding(new Insets(100));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, hbox1, hbox2,hbox4, hbox3);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        pane.getChildren().addAll(vbox);
        ChooseCardScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(ChooseCardScene);
        stage.show();
        if(isClient) AddIpStage();
    }
    public Mapp getMap(){
        return map;
    }
    public boolean[] getClicked() {
        return clicked;
    }

    public List<Integer> getCT() {
        return chargeTimes;
    }
    public void startGame(){
        startTime = System.currentTimeMillis();
        time = 0;
        if(chosenCards == null){
            List<String> elementsToAdd = Arrays.asList("Sunflower", "Peashooter", "WallNut", "Jalapeno", "SnowPea", "TallNut");
            chosenCards.addAll(elementsToAdd);
        }
        if(this.map == null)
            this.map = new Mapp(stage, chosenCards, plants);
        map.drawMap();
        setupSpawnTimer();
        setupAttackPhases();
        startGameLoop();
    }
    private void updateChargeTimes(int delta) {
        for (int i = 0 ; i < 6; i++) {
            if (clicked[i])
                chargeTimes.set(i,chargeTimes.get(i) + delta);
        }
    }
    public void positionZombie(Zombie zombie) {
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
        spawnTimeline.setCycleCount(4);
        spawnTimeline.play();
    }
    private void setupSpawnTimer(long time) {
        if (time > 75000) return;
        int phase = (int) (time / 15000);
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
        if(du != 0)
            spawnTimeline.setCycleCount((int) (Math.ceil(15/du)));
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
        if(du != 0)
            spawnTimeline.setCycleCount((int) (remainingTimeMil/(du * 1000)));
        spawnTimeline.play();
    }
    private void spawnZombie() {
        updateLSpawn();
        int currentPhase = getCurrentPhaseS();
        int row = (new Random()).nextInt(5);
        Zombie zombie = ZombieFactory.createRandomZombie(currentPhase, row);
        zombies.add(zombie);
        map.borderPane.getChildren().add(zombie.getView());
        positionZombie(zombie);

        if (server != null && server.getOut() != null) {
            server.getOut().println("SPAWN " + zombie.getClass().getSimpleName() + " " + zombie.getRow() + " ");
        }
    }
    private void setupAttackPhases() {

        Timeline atTimeline = new Timeline(new KeyFrame(Duration.seconds(15), e -> createAttackPhase()));
        atTimeline.setCycleCount(3);
        atTimeline.setDelay(Duration.seconds(16.5));
        atTimeline.play();
    }
    private void setupAttackPhaseS() {
        if (startAttackTime != 0)
            createAttackPhaseS();
        Timeline atTimeline = new Timeline(new KeyFrame(Duration.seconds(15), e -> createAttackPhase()));
        atTimeline.setCycleCount(3);
        atTimeline.jumpTo(Duration.millis(time - startAttackTime));
        if (startAttackTime == 0)
            atTimeline.setDelay(Duration.millis(31500 - time));
        atTimeline.play();
    }
    public void loadGame(){
        plants.clear();
        zombies.clear();
        Hzombies.clear();
        chosenCards.clear();
        this.map = new Mapp(stage, chosenCards, plants);
        SaveLoadManager.loadGame("savedData.txt", plants, zombies, Hzombies,chosenCards, score, time);
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
        for(Zombie z: Hzombies){
            map.borderPane.getChildren().add(z.getView());
        }
        setupSpawnTimer(time);
        setupAttackPhaseS();
        //setupGraveZombies();
        startGameLoop();

    }
    public void updateLSpawn() {
        lastSpawnTime = (int) time;
    }
    private void createAttackPhase() {
        startAttackTime = (int)time;
        int phase = getCurrentPhaseS();
        if(time > 55000) phase = 4;
        final int PHASE = phase;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.28), e -> { updateLSpawn(); int row = (new Random()).nextInt(5);
            Zombie zombie = ZombieFactory.createRandomZombie(PHASE, row);
            zombies.add(zombie);
            map.borderPane.getChildren().add(zombie.getView());
            positionZombie(zombie);
            if(server != null && server.getOut() != null) {
                server.getOut().println("SPAWN " + zombie.getClass().getSimpleName() + " " + zombie.getRow() + " ");
            }
        }));
        timeline.setCycleCount(15);
        //timeline.jumpTo(Duration.millis(time % 15000));
        timeline.play();
    }
    private void createAttackPhaseS() {
        int phase = getCurrentPhaseS();
        if (time > 55000) phase = 4;
        int finalPhase = phase;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.28), e -> {updateLSpawn(); int row = (new Random()).nextInt(5);
            Zombie zombie = ZombieFactory.createRandomZombie(finalPhase, row);
            zombies.add(zombie);
            map.borderPane.getChildren().add(zombie.getView());
            positionZombie(zombie);
            if(server != null && server.getOut() != null) {
                server.getOut().println("SPAWN " + zombie.getClass().getSimpleName() + " " + zombie.getRow() + " ");
            }
        }));
        timeline.setCycleCount(15);
        timeline.jumpTo(Duration.millis(time - startAttackTime));
        timeline.play();
    }

    public int getCurrentPhase() {
        double elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000.0;
        if (elapsedSeconds >= 73.5) return 4;
        return (int) (elapsedSeconds / 15);
    }
    public int getCurrentPhaseS() {
        double elapsedSeconds = time / 1000.0;
        if (elapsedSeconds >= 73.5) return 4;
        return (int) (elapsedSeconds / 15);
    }
    public long getTime(){
        return time;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                boolean happened = false;
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                time += (long) (deltaTime * 1000);
                if (time >= 58000 && time <= 58040 && !happened && isNight) {
                    happened = true;
                    spawnFromGrave();
                    for(int i = 0; i < 5; i++){
                        map.gravePosPairs[i][0] = -1;
                        map.gravePosPairs[i][1] = -1;
                    }
                }
                //System.out.println("___________" + time);
                currentPhase = getCurrentPhaseS();
                updateChargeTimes((int) (deltaTime * 1000));
                updatePlants(deltaTime);
                updateHZombies(deltaTime);
                checkZombiePlantCollisions();
                checkZombieHzombieCollisions();
                updateBullets(deltaTime);
                updateZombies(deltaTime);

                if (lost || won) gameLoop.stop();
                if (time > 75000 && time - lastSpawnTime > 1900 && zombies.isEmpty()) {
                    if(isServer){
                        server.getOut().println("GAME_OVER LOST");
                    }
                    else if (isClient){
                        client.getOut().println("ClientWON");
                    }
                    won = true;
                    gameLoop.stop();
                    showGameOver(won);
                }
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
                if(isClient){
                    lost = true;
                    client.getOut().println("ClientLOST");
                }
                else if(isServer){
                    lost = true;
                    server.getOut().println("GAME_OVER WIN");
                }
                gameLoop.stop();
                showGameOver(false);
            }
        }
    }
    public void lose(){
        Scene scene = new Scene(new StackPane(new Label("LOOOOser")), 1024, 626);
        stage.setScene(scene);
        lost = true;
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
            if (zombie.getColumn() > 9.3) {
                iterator.remove();
                map.borderPane.getChildren().remove(zombie.getView());
            }
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
        if(plant != null && plant.getClass().getSimpleName().equals("Sunflower")){
            ((Sunflower)plant).stop();
        }
        else if(plant != null && plant.getClass().getSimpleName().equals("Plantern")){
            for(Fog f: Game.getInstance().map.fogs){
                if(f.getRow() <= plant.row+1 && f.getRow() >= plant.row-1
                        && f.getCol() <= plant.col+1 && f.getCol() >= plant.col-1){
                    f.imageView.setVisible(true);
                    f.isPlanternInArea = false;
                }
            }
        }
        plants.remove(plant);
        if(plant != null && plant.view.getParent() != null)
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

    private void setupGraveZombies() {
        spawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (time > 57500 && time < 58600 && !map.checkForGravePos(map.getGravePosPairs()[0][0], map.getGravePosPairs()[0][1])) {

                        spawnFromGrave();

                    }
                })
        );
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();

    }
    private void spawnFromGrave() {
        for (int i = 0 ; i < 5 ; i++) {
            if (map.getGravePosPairs()[i][0] == -1) {continue;}
            Zombie zombie = ZombieFactory.createRandomZombie(4, map.getGravePosPairs()[i][0]);
            zombie.setColumn(map.getGravePosPairs()[i][1]+ 0.5);
            positionZombie(zombie);
            updateLSpawn();
            zombie.getView().setLayoutX(map.getGridPane().getLayoutX() + zombie.getColumn() * 80 + 30);
            zombies.add(zombie);
            map.borderPane.getChildren().add(zombie.getView());
            ((StackPane)(map.getNodeFromGrid(map.getGridPane(), map.getGravePosPairs()[i][1], map.getGravePosPairs()[i][0]))).getChildren().clear();

        }
    }
    public Server getServer(){
        return server;
    }
    public boolean isClient() {
        return isClient;
    }
}