package game.plantsvszambies;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
public class  MainApplication extends Application{
    public static int rowNum = 5;
    public static int colNum = 9;
    public static int CELL_SIZE = 80;
    private static AtomicInteger num = new AtomicInteger(0);
    @Override
    public void start(Stage stage) throws IOException {
        Game game = Game.getInstance();
        //game.startGame();
    }

    public static void main(String[] args) {
        launch();
    }
}