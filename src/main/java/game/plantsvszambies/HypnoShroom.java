package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HypnoShroom extends Plant {
    private static final int COST = 75; // Example cost
    private static final int HEALTH = 400; // Same as other plants
    private static final int COOLDOWN = 30; // Seconds

    public HypnoShroom(int row, int col, ImageView imageView) {
        super(row, col, HEALTH, COST, 7.5F, imageView);
        this.view = imageView;
    }

    public void act() {
        for (Zombie zombie : Game.getInstance().getZombies()) {
            if (zombie.getRow() == this.row && Math.abs(this.col - zombie.getColumn()) <= 1.35 && Math.abs(this.col - zombie.getColumn()) >= 0.5) {
                hypnotizeZombie(zombie);
                this.health = 0;
                break;
            }
        }
    }

    private void hypnotizeZombie(Zombie zombie) {
        zombie.getView().setScaleX(-1);
        zombie.isR(true);
        zombie.setHypnotized(true);
        Game.getInstance().getZombies().remove(zombie);
        Game.getInstance().getHzombies().add(zombie);

    }

    @Override
    void update(double deltaTime) {
        act();

    }
}