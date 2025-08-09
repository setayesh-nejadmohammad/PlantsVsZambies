package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImpZombie extends Zombie{
    public ImpZombie(int row) {
        super(45, 25, 0.5, row);
    }
    public ImpZombie(int row, double col) {
        super(100, 50, 0.25, row);
        this.column = col;
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
        view.setFitWidth(100);
        view.setFitHeight(100);
        return view;
    }
    public void startEating() {
        this.isEating = true;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/impAttack.gif")));
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
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
    }
}
