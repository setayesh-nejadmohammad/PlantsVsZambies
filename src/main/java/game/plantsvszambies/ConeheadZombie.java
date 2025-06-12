package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConeheadZombie extends Zombie {
    public ConeheadZombie(int row) {
        super(150, 25, 0.25, row);
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/coneheadzombie.gif")));
        view.setFitWidth(100);
        view.setFitHeight(100);
        return view;
    }

}