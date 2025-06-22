package game.plantsvszambies;

import javafx.scene.image.ImageView;

public class SnowPea extends ShooterPlant {
    public SnowPea(int row, int col, int health, int cost, int rechargeTime, ImageView imageView) {
        super(row, col, health, cost, rechargeTime, imageView);
        this.fireRate = 1.2;
        this.damage = 15;
        this.isSnowPea = true;
    }
}
