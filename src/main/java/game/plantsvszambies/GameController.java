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
    int totalScore = 300;
    GridPane gridPane;
    StackPane scoreStack = Game.scoreStack;
    BorderPane borderPane;
    Label scoreLabel = new Label("     "+totalScore);

    private StackPane shovelStack = new StackPane();
    private ImageView shovelConImage = new ImageView(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/shovelBack.png")));
    private ImageView shovelImage = new ImageView(new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/shovel.png")));
    public ImageView scoreBox = new ImageView(new Image(getClass().getResourceAsStream("images/button_menus/score box.jpg")));

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
        scoreBox.setFitHeight(37);
        scoreBox.setFitWidth(165);
        scoreStack.getChildren().add(scoreBox);
        scoreStack.getChildren().add(scoreLabel);
        borderPane.setTop(scoreStack);
        scoreStack.setLayoutX(10);
        borderPane.setPadding(new Insets(10));
        scoreLabel.setStyle("-fx-font-size: 21px; -fx-text-fill: black ; -fx-font-weight: 800");

        shovelStack.getChildren().addAll(shovelConImage, shovelImage);
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
        scoreStack.getChildren().remove(scoreLabel);
        scoreLabel = new Label("     "+totalScore);
        scoreStack.getChildren().add(scoreLabel);
        scoreLabel.setStyle("-fx-font-size: 21px; -fx-text-fill: black; -fx-font-weight: 800");
        borderPane.setPadding(new Insets(10));
    }
}