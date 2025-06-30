package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Stack;

public class Sun extends ImageView {
    private static final int SUN_POINTS = 25;
    private static final double FALL_SPEED = 50; // pixels per second
    private static final Duration STAY_DURATION = Duration.seconds(4);

    private final Pane parent;
    private final GameController gameController; // You should define this to manage score
    private Timeline fallTimeline;

    public Sun(Pane parent, GameController gameController, double startX) {
        this.parent = parent;
        this.gameController = gameController;

        // Set image
        this.setImage(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/sun.png"))); // Make sure this path is correct
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.setX(startX);
        this.setY(0); // Start at the top

        parent.getChildren().add(this);

        // Start falling
        startFalling();

        // Handle click
        this.setOnMouseClicked(event -> {
            if(gameController != null) {
                gameController.addScore(SUN_POINTS); // Your method to update score
                destroy();
            }
        });
    }

    public Sun(StackPane cell, GameController gameController, int row, int col){
        this.gameController = gameController;
        this.parent = cell;
        //this.parent = parent;

        cell.getChildren().addAll(this);

        // Set image
        this.setImage(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/sun.png"))); // Make sure this path is correct
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.setX(row);
        this.setY(col);

        // Handle click
        this.setOnMouseClicked(event -> {
            if(gameController != null) {
                gameController.addScore(SUN_POINTS); // Update score
                destroy();
            }
        });

        // destroy the sun after 3 sec if the pleyer didt'n take it
        PauseTransition pause = new PauseTransition(STAY_DURATION);
        pause.setOnFinished(e -> destroy());
        pause.play();
    }

    private void startFalling() {
        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            setY(getY() + FALL_SPEED * 0.05); // Move sun downward
            if (getY() >= parent.getHeight() - getFitHeight()) {
                fallTimeline.stop();
                stickToBottom();
            }
        }));
        fallTimeline.setCycleCount(Timeline.INDEFINITE);
        fallTimeline.play();
    }

    private void stickToBottom() {
        setY(parent.getHeight() - getFitHeight());
        PauseTransition pause = new PauseTransition(STAY_DURATION);
        pause.setOnFinished(e -> destroy());
        pause.play();
    }

    private void destroy() {
        Platform.runLater(() -> parent.getChildren().remove(this));
    }
}
