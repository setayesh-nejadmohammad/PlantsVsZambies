package game.plantsvszambies;

import javafx.scene.image.ImageView;

public class Peashooter extends ShooterPlant {
    private double timeSinceLastShot;
    private double fireRate;      // Shots per second
    private int damage;
    private boolean isSnowPea;

    public Peashooter(int row, int col, ImageView imageView) {
        super(row, col, 5, 100, 7, imageView);
        this.fireRate = 1.5;      // Shots per second
        this.damage = 20;
        this.isSnowPea = false;
    }
}
