package game.plantsvszambies;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController {
    //private List<Zombie> activeZombies = new ArrayList<>();
    int totalScore = 99999999;
    GridPane gridPane;

    BorderPane borderPane;
    Label scoreLabel = new Label("Score: " + totalScore);
    private StackPane shovelStack = new StackPane();
    private ImageView shovelConImage = new ImageView(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/shovelBack.png")));
    private ImageView shovelImage = new ImageView(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/shovel.png")));

    public ImageView getShovelConImage() {
        return shovelConImage;
    }
    public void setShovelConImage(ImageView shovelConImage) {
        this.shovelConImage = shovelConImage;
    }
    public ImageView getShovelImage() {
        return shovelImage;
    }
    public void setShovelImage(ImageView shovelImage) {
        this.shovelImage = shovelImage;
    }
    public StackPane getShovelStack() {
        return shovelStack;
    }
    public void setShovelStack(StackPane shovelStack) {
        this.shovelStack = shovelStack;
    }
    public GameController(BorderPane borderPane, GridPane gridPane) {
        this.borderPane = borderPane;
        this.gridPane = gridPane;
        borderPane.setTop(scoreLabel);
        borderPane.setPadding(new Insets(10));
        scoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white");
    }

    public void addScore(int score) {
        totalScore += score;
        System.out.println("Total Score: " + totalScore);
        UpdateScoreLabel(totalScore);
    }

    public void reduceScore(int score) {
        totalScore -= score;
        System.out.println("Total Score: " + totalScore);
        UpdateScoreLabel(totalScore);
    }
    public void UpdateScoreLabel(int newScore) {
        scoreLabel = new Label("Score: " + totalScore);
        borderPane.setTop(scoreLabel);
        scoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white");
        borderPane.setPadding(new Insets(10));
    }
}
