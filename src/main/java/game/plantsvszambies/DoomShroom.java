package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class DoomShroom extends Plant{
    StackPane pane;
    public DoomShroom(int row, int col, StackPane pane, ImageView imageView){
        super(row, col, 5, 75, 7, imageView);
        this.pane = pane;
        pane.getChildren().add(imageView);
    }
    public void update(double deltaTime){}

    private void setupExplosion(StackPane pane) {
        // After 1.5 seconds, explode
        Timeline explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.6), e -> explode(pane))
        );
        explosionTimeline.setCycleCount(1);
        explosionTimeline.play();
    }

    public void explode(StackPane pane) {
        // تغییر به تصویر انفجار
        Image explosionImage = new Image(getClass().getResourceAsStream("images/Plants/DoomExplosion.gif"));
        view.setImage(explosionImage);

        Game.getInstance().getZombies().forEach(z -> {
            System.out.println("💥 cherry bomb at col "+col+" killed a zombie at row: " + z.getRow() + ", col: " + z.getColumn());
            z.die();
        });

        // حذف تصویر پس از انفجار
        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.7), e -> pane.getChildren().remove(view))
        );
        removeTimeline.play();
        Game.getInstance().getPlants().remove(this);

    }
}
