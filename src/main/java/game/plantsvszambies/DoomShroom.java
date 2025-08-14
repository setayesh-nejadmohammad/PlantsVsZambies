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
        if(Game.isNight) setupExplosion(pane);

        this.isNightPlant = true;
        if(!Game.isNight) {
            this.isSleeping = true;
            view.setImage(new Image(getClass().getResourceAsStream("images/Plants/Sleep.gif")));
        }
    }
    public void update(double deltaTime){}

    private void setupExplosion(StackPane pane) {
        // After 1.5 seconds, explode
        Timeline explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.6), e -> {
                    explode(pane);
                })
        );
        explosionTimeline.setCycleCount(1);
        explosionTimeline.play();
    }

    public void explode(StackPane pane) {
        // تغییر به تصویر انفجار
        Image explosionImage = new Image(getClass().getResourceAsStream("images/Plants/DoomExplosion.gif"));
        Image craterImage = new Image(getClass().getResourceAsStream("images/Plants/crater.png"));
        ImageView explosionView = new ImageView(explosionImage);
        explosionView.setFitHeight(200);
        explosionView.setFitWidth(200);
        explosionView.setTranslateX(80*(this.col)+200);
        explosionView.setTranslateY(80*row+130 - (4-row)*10 - 80);

        pane.getChildren().remove(view);
        Game.getInstance().map.borderPane.getChildren().add(explosionView);

        List<Zombie> zombiesInArea = Game.getInstance().getZombies().stream().toList();

        zombiesInArea.forEach(z -> {
            z.die();
        });

        // حذف تصویر پس از انفجار
        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1.3), e -> {
                    pane.getChildren().add(view);
                    view.setImage(craterImage);
                    view.setFitWidth(80);
                    view.setFitHeight(70);
                    Game.getInstance().map.borderPane.getChildren().remove(explosionView);
                })
        );
        removeTimeline.play();
        Game.getInstance().getPlants().remove(this);

    }
    public void beAwake(){
        isSleeping = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Plants/DoomShroom.gif")));
        setupExplosion(pane);
    }
}