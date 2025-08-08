package game.plantsvszambies;

import javafx.scene.image.ImageView;

public class SnowPea extends ShooterPlant {
    public static final double SLOW_DURATION = 3.0; // 3 seconds slow effect
    public static final double SLOW_FACTOR = 0.5;

    public SnowPea(int row, int col,  ImageView imageView) {
        super(row, col, 5, 175, 7, imageView);
        this.fireRate = 1;
        this.damage = 15;
        this.isSnowPea = true;
    }
    protected void shoot() {
        Bullet bullet = new Bullet(
                this.getX() + 10,     // Start slightly ahead of plant
                this.getY(),     // Center vertically
                this.getRow(),
                damage,
                300.0,                // Pixel per second speed
                isSnowPea,
                false
        );

        Game.getInstance().addBullet(bullet);
    }

}
