package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.concurrent.*;

public class Sunflower extends Plant {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    Timeline sunSpawnTimeline;

    public Sunflower(int row, int col, GameController gameController, StackPane cell) {
        super(row, col, 5, 50, 7);
        startProducing(gameController, cell);
    }

    private void startProducing(GameController gameController, StackPane cell) {
        sunSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            new Sun(cell, gameController, row+270, col+100); // `this` is GameController
        }));
        sunSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        sunSpawnTimeline.play();
    }

    /*@Override
    public void act(Map map) {
        // Sunflower عمل مستقیم ندارد، ولی act را می‌توان برای انیمیشن استفاده کرد.
    }*/

    public void stop() {
        sunSpawnTimeline.stop();
    }
}

