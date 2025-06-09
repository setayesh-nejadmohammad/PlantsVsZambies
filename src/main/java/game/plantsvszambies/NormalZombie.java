package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NormalZombie extends Zombie{
    private Image normalZombieImage = new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif"));

    public NormalZombie(int row, int col, Map map) {
        super(row, col, map, 5, 1);
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(normalZombieImage);
        return imageView;
    }
}
