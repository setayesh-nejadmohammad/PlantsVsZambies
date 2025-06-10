package game.plantsvszambies;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainApplication extends Application {
    public static int rowNum = 5;
    public static int colNum = 9;
    public static int CELL_SIZE = 80;
    private static AtomicInteger num = new AtomicInteger(0);
    @Override
    public void start(Stage stage) throws IOException {
        Game game = new Game(stage);
        game.startGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
