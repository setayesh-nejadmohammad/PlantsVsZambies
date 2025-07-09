package game.plantsvszambies;

import javafx.scene.image.ImageView;

public class RepeaterPeaShooter extends ShooterPlant {
    private double timeBetweenShots = 0.2; // 200ms between the two peas
    private boolean isSecondShot = false;
    public RepeaterPeaShooter(int row, int col, ImageView imageView) {
        super(row, col, 5, 200, 7, imageView);
        this.fireRate = 1;      // Shots per second
        this.damage = 15;
        this.isSnowPea = false;
    }

    @Override
    public void update(double deltaTime) {
        timeSinceLastShot += deltaTime;

        if (isSecondShot && timeSinceLastShot >= timeBetweenShots) {
            shoot();
            isSecondShot = false;
            timeSinceLastShot = 0;
        }
        else if (!isSecondShot &&
                timeSinceLastShot >= (1.0/fireRate) &&
                hasZombieInLane()) {
            shoot();
            isSecondShot = true;
            timeSinceLastShot = 0; // Reset for second shot
        }
    }
}
