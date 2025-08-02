package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class TallNut extends Plant{
    public TallNut(int row, int col, ImageView view, StackPane pane) {
        super(row, col, 50, 50, 7, view);
        Image image = new Image(getClass().getResourceAsStream("images/Plants/TallNut.gif"));
        view.setImage(image);
        checkForDamageRangeImage();
        view.setFitHeight(90);
        view.setFitWidth(65);
        pane.getChildren().add(view);
    }
    public void update(double d){}

    public void takeDamage(int amount) {
        health -= amount;
        playDamageAnimation();
        checkForDamageRangeImage();
        if (health <= 0) {
            destroy();
        }
    }
    private void checkForDamageRangeImage(){
        Image image1 = new Image(getClass().getResourceAsStream("images/Plants/TallnutCracked1.gif"));
        Image image2 = new Image(getClass().getResourceAsStream("images/Plants/TallnutCracked2.gif"));
        if(health <= 15){
            view.setImage(image2);
        }
        else if(health <= 30){
            view.setImage(image1);
        }
    }
}