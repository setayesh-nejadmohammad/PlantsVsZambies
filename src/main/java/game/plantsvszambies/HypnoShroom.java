package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HypnoShroom extends Plant {
    private static final int COST = 75; // Example cost
    private static final int HEALTH = 4; // Same as other plants
    private static final int COOLDOWN = 30; // Seconds

    public HypnoShroom(int row, int col, ImageView imageView) {
        super(row, col, HEALTH, COST, 7.5F, imageView);
        this.view = imageView;
    }

    public void act() {
        // Check for zombie collisions
        for (Zombie zombie : Game.getInstance().getZombies().stream().filter()) {
            if (zombie.getColumn() == this.col) {
                hypnotizeZombie(zombie);
                // HypnoShroom dies after hypnotizing one zombie
                this.health = 0;
                break;
            }
        }
    }

    private void hypnotizeZombie(Zombie zombie, GameModel model) {
        zombie.setHypnotized(true);

        // Change behavior - now attacks other zombies
        zombie.setOnCollision(z -> {
            if (z instanceof Zombie && !((Zombie)z).isHypnotized()) {
                // Two zombies fight each other
                zombie.setHealth(zombie.getHealth() - 1);
                z.setHealth(z.getHealth() - 1);

                // Check if either died
                if (zombie.getHealth() <= 0) {
                    model.removeZombie(zombie);
                }
                if (z.getHealth() <= 0) {
                    model.removeZombie(z);
                }
            }
        });
    }

    @Override
    void update(double deltaTime) {
        act();

    }
}