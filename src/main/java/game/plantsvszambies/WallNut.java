package game.plantsvszambies;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class WallNut extends Plant{
    public WallNut(int row, int col, ImageView view, StackPane pane) {
        super(row, col, 25, 50, 7, view);
        Image image = new Image(getClass().getResourceAsStream("images/Plants/WallNut.gif"));
        view.setImage(image);
        checkForDamageRangeImage();
        view.setFitHeight(80);
        view.setFitWidth(80);
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
        Image image1 = new Image(getClass().getResourceAsStream("images/Plants/Wallnut_cracked1.gif"));
        Image image2 = new Image(getClass().getResourceAsStream("images/Plants/Wallnut_cracked2.gif"));
        if(health <= 17){
            view.setImage(image2);
        }
        else if(health <= 10){
            view.setImage(image1);
        }
    }
}
