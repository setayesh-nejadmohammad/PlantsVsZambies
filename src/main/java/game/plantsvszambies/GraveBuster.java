package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GraveBuster extends Plant{
    public GraveBuster(int row, int col, StackPane pane, ImageView imageView, int[][] pos){
        super(row, col, 5, 75, 7, imageView);
        Image graveBuster = new Image(getClass().getResourceAsStream("images/Plants/GraveBuster.gif"));
        ImageView view1 = new ImageView(graveBuster);
        view1.setFitHeight(80);
        view1.setFitWidth(80);
        pane.getChildren().add(view1);
        act(pane);
        removeGravePos(pos);
    }
    public void update(double time){}

    private void act(StackPane pane){
        Timeline busteringTime = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    pane.getChildren().clear();
                })
        );
        busteringTime.play();
    }

    private void removeGravePos(int[][] pos){
        for(int i =0; i <5; i++){
            if(pos[i][0] == row && pos[i][1] == col){
                pos[i][0] = -1;
                pos[i][1] = -1;
            }
        }
    }
}
