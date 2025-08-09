package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScreenDoorZombie extends Zombie {
    public ScreenDoorZombie(int row) {
        super(150, 25, 0.25, row);
    }
    public ScreenDoorZombie(int row, double col) {
        super(100, 50, 0.25, row);
        this.column = col;
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
        if (!hEat) {
            super.baseColumn = plant.getCol(); // Set reference point
            updateAttackPosition();
        }
        super.eatCooldown = super.eatInterval;
    }
    public void stopEating() {
        hEat = false;
        isEating = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/ScreendoorZombie.gif")));
    }
}
