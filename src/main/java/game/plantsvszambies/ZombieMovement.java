package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZombieMovement extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1080, 626);

        // Load your zombie GIF
        Image zombieGif = new Image(getClass().getResourceAsStream("images/Zombie/coneheadzombie.gif")); // Make sure it's in resources or the same directory
        ImageView zombieView = new ImageView(zombieGif);
        zombieView.setLayoutY(200); // vertical position of the zombie
        zombieView.setLayoutX(950); // start off-screen to the right

        root.getChildren().add(zombieView);

        // Move the zombie left by 1 pixel every 10ms
        Timeline zombieMovement = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            zombieView.setLayoutX(zombieView.getLayoutX() - 1);
        }));

        zombieMovement.setCycleCount(Timeline.INDEFINITE); // run forever
        zombieMovement.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Plants vs Zombies - Zombie Move Demo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
