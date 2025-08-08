package game.plantsvszambies;

import javafx.scene.image.ImageView;

import java.util.List;

public abstract class ShooterPlant extends Plant {
    protected double timeSinceLastShot;
    protected double fireRate;      // Shots per second
    protected int damage;
    protected boolean isSnowPea;

    public ShooterPlant(int row, int col, int health, int cost, float rechargeTime, ImageView imageView) {
        super(row, col, health, cost, rechargeTime, imageView);
        this.fireRate = 1.5;
        this.damage = 15;
        this.isSnowPea = false;
    }

    public void update(double deltaTime) {
        timeSinceLastShot += deltaTime;

        if (timeSinceLastShot >= 1.0/fireRate) {
            if (hasZombieInLane()) {

                shoot();
                timeSinceLastShot = 0;
            }
        }
    }

    protected void shoot() {
        Bullet bullet = new Bullet(
                this.getX() + 20,     // Start slightly ahead of plant
                this.getY(),     // Center vertically
                this.getRow(),
                damage,
                300.0,                // Pixel per second speed
                isSnowPea,
                false
        );

        Game.getInstance().addBullet(bullet);
    }

    protected boolean hasZombieInLane() {
        // Check if any zombies exist in this row to the right of plant

        for (Zombie zombie : Game.getInstance().getZombies()) {
            if (zombie.getRow() == this.getRow() && zombie.getView().getLayoutX() >= this.getCol() * 80 + 70) {

                return true;
            }
        }
        return false;

    }
}

// Concrete implementations

