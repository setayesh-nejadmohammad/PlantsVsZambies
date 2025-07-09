package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class Jalapeno extends Plant{
    StackPane pane;
    public Jalapeno(int row, int col, StackPane pane, ImageView view) {
        super(row, col, 5, 150, 7, view);
        this.pane = pane;
        Image image = new Image(getClass().getResourceAsStream("images/Plants/jalapeno.gif"));
        view.setImage(image);
        view.setFitWidth(80);
        view.setFitHeight(80);

        pane.getChildren().add(view);
        view.setTranslateX(pane.getTranslateX());
        setupExplosion();
    }
    public void update(double d){}

    private void setupExplosion() {
        // After 0.8 seconds, explode
        Timeline explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.8), e -> explode())
        );
        explosionTimeline.setCycleCount(1);
        explosionTimeline.play();
    }

    public void explode(){

        Image explosionImage = new Image(getClass().getResourceAsStream("images/Plants/JalapenoAttack.gif"));
        view.setImage(explosionImage);
        view.setFitWidth(720);
        view.setTranslateX(view.getTranslateX()-(col*80));
        //view.setLayoutX(Game.getInstance().map.grid.getLayoutX());


        List<Zombie> zombiesInArea = Game.getInstance().getZombies().stream()
                .filter(z -> z.getRow() == this.row)
                .toList();  // یا collect(Collectors.toList()) برای Java 8

        zombiesInArea.forEach(z -> {
            System.out.println("🌶️ jalaPeno killed a zombie at row: " + z.getRow() + ", col: " + z.getColumn());
            z.die();
        });

        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1.4), e -> pane.getChildren().remove(view))
        );
        removeTimeline.play();

        Game.getInstance().getPlants().remove(this);
    }
}
