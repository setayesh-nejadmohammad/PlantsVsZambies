package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Blover extends Plant{
    public Blover(int row, int col, StackPane pane, ImageView imageView){
        super(row, col, 5, 100, 10, imageView);
        act(pane);
    }
    public void update(double time){}
    public void act(StackPane pane){
        Timeline blowingTime = new Timeline(
                new KeyFrame(Duration.seconds(3.5), e->{
                    pane.getChildren().remove(view);
                    for(Fog f:Game.getInstance().map.fogs){
                        f.imageView.setVisible(false);
                    }
                })
        );
        blowingTime.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e->{
                    for(Fog f:Game.getInstance().map.fogs){
                        f.imageView.setVisible(true);
                    }
                })
        );
        timeline.play();
    }
}
