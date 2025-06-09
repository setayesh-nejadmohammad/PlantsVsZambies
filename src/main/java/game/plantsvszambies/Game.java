package game.plantsvszambies;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Game {
    Stage stage;
    Map map;
    BorderPane borderPane = new BorderPane();
    Image frontYard;

    public Game(Stage stage){
        this.stage = stage;
        map = new Map(borderPane);
        frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));

    }

    public void startGame(){
        stage.setTitle("PlantsVsZambies");
        Scene scene = new Scene(borderPane, 800, 600);

    }




}
