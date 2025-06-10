package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConeheadZombie extends Zombie {
    private Image coneheadZombieImage = new Image(getClass().getResourceAsStream("images/Zombie/conehead_zombie.gif"));

    public ConeheadZombie(int row, int col, Map map) {
        super(row, col, map, 7, 1); // health = 7, speed = 1 cell/sec
    }
    public ImageView getImageView() {
        ImageView imageView = new ImageView(coneheadZombieImage);
        return imageView;
    }
}
