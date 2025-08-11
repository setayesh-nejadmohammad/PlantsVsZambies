package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CoffeeBean extends Plant{
    public CoffeeBean(int row, int col, StackPane pane, ImageView imageView, Plant plant) {
        super(row, col, 5, 75, 3, imageView);
        this.isNightPlant = true;
        AwakeThePlant(plant, pane);
    }
    public void update(double t){}
    public void AwakeThePlant(Plant plant, StackPane pane){
        Timeline awakingTime = new Timeline(
                new KeyFrame(Duration.seconds(2), e->{
                    plant.beAwake();
                    pane.getChildren().remove(this.view);
                })
        );
        awakingTime.play();
    }
}
