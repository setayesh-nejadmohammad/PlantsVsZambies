package game.plantsvszambies;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game.plantsvszambies.ZombieFactory.ZombieType.NORMAL;

public class Game {
    Stage stage;
    Map map;
    private long startTime;
    private static final double SPAWN_INTERVAL = 3.0;
    private Timeline spawnTimeline;
    private List<Zombie> zombies = new ArrayList<>();

    Image frontYard;

    public Game(Stage stage){

        this.stage = stage;
        this.map = new Map(stage);

    }

    public void startGame(){
        startTime = System.currentTimeMillis();
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
            @Override
            public void handle(long now) {
                double deltaTime = 1.0 / 60; // Assuming 60 FPS

                // Update all zombies
                for (Zombie zombie : new ArrayList<>(zombies)) {
                    zombie.update(deltaTime);
                    //checkCollisions(zombie);
                    checkReachedEnd(zombie);
                }
            }
        };
        gameLoop.start();
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


}
