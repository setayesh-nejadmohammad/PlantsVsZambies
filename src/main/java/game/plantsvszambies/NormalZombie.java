package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NormalZombie extends Zombie{
    private Image normalZombieImage = new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif"));

    public NormalZombie(int row) {
        super(100, 50, 0.25, row);
    }
    public NormalZombie(int row, double col) {
        super(100, 50, 0.25, row);
        this.column = col;
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(normalZombieImage);
        return imageView;
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif")));
        view.setFitWidth(100);
        view.setFitHeight(100);
        return view;
    }
}