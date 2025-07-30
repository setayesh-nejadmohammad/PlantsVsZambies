package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScreenDoorZombie extends Zombie {
    public ScreenDoorZombie(int row) {
        super(150, 25, 0.25, row);
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/ScreendoorZombie.gif")));
        view.setFitWidth(100);
        view.setFitHeight(100);
        return view;
    }
    public void startEating() {
        this.isEating = true;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/ScreendoorZombieAttack.gif")));
        Plant plant = findPlantInFront();
        super.baseColumn = plant.getCol(); // Set reference point
        updateAttackPosition();
        super.eatCooldown = super.eatInterval;
    }
    public void stopEating() {
        isEating = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/ScreendoorZombie.gif")));
    }
}
