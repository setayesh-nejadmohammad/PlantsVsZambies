package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImpZombie extends Zombie{
    public ImpZombie(int row) {
        super(150, 25, 0.5, row);
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
        view.setFitWidth(100);
        view.setFitHeight(100);
        return view;
    }
}
