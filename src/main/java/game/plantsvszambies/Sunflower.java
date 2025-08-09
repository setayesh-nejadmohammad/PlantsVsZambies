package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.concurrent.*;

public class Sunflower extends Plant {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    Timeline sunSpawnTimeline;

    public Sunflower(int row, int col, StackPane cell, ImageView imageView) {
        super(row, col, 5, 50, 7, imageView);
        //cell.getChildren().add(imageView);
        startProducing(cell);
    }

    private void startProducing(StackPane cell) {
        sunSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            new Sun(cell, row+270, col+100); // `this` is GameController
        }));
        sunSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        sunSpawnTimeline.play();
    }
    public void update(double deltaTime){
        // سرکاریه
    }
    public void stop() {
        sunSpawnTimeline.stop();
    }
}