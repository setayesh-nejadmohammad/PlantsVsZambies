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
        this.map = new Map(stage);
        frontYard = new Image(getClass().getResourceAsStream("images/frontyard.png"));
    }

    public void startGame(){
        map.drawMap();

    }




}
