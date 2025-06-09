package game.plantsvszambies;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameController {
    int totalScore = 0;
    BorderPane borderPane;
    Label scoreLabel = new Label("Score: " + totalScore);
    public GameController(BorderPane borderPane) {
        this.borderPane = borderPane;
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
    private void UpdateScoreLabel(int newScore) {
        scoreLabel = new Label("Score: " + totalScore);
        borderPane.setTop(scoreLabel);
        scoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-background-color: white");
        borderPane.setPadding(new Insets(10));
    }
}
