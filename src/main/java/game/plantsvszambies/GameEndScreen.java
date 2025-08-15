package game.plantsvszambies;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GameEndScreen {
    private Stage stage;
    private boolean playAgain = false;

    public boolean show(boolean playerWon, Stage primaryStage) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.TRANSPARENT);

        String imagePath = playerWon ? "images/winpage.png" : "images/you_lose.png";
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setOnAction(e -> {
            playAgain = true;
            stage.close();
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setStyle("-fx-font-size: 18px; -fx-background-color: #f44336; -fx-text-fill: white;");
        exitButton.setOnAction(e -> {
            playAgain = false;
            stage.close();
        });
        playAgainButton.setOnMouseEntered(e -> {
            playAgainButton.setStyle("-fx-font-size: 20px; -fx-background-color: #2E7D32; -fx-text-fill: white;");
        });
        playAgainButton.setOnMouseExited(e -> {
            playAgainButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        });
        // Layout
        VBox layout = new VBox(20, imageView, playAgainButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 40px; -fx-border-color: gold; -fx-border-width: 5px;");
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), layout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        Scene scene = new Scene(layout);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/game_styles.css").toExternalForm());
        playAgainButton.setId("playAgainButton");
        exitButton.setId("exitButton");
        stage.setScene(scene);
        stage.showAndWait();

        return playAgain;
    }
}