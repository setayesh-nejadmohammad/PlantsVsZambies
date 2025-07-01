package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
    private double x, y;          // Position
    private double speed;         // Pixels per second
    private int damage;
    private boolean isFrozen;     // For snow peas
    private ImageView view;
    private int row;             // Track which row the bullet is in

    public Bullet(double startX, double startY, int row, int damage, double speed, boolean isFrozen) {
        this.x = startX;
        this.y = startY;
        this.row = row;
        this.damage = damage;
        this.speed = speed;
        this.isFrozen = isFrozen;

        // Set up visual representation
        this.view = new ImageView();
        this.view.setImage(getBulletImage());
        this.view.setLayoutX(startX);
        this.view.setLayoutY(startY);
    }

    private Image getBulletImage() {
        return isFrozen ?
                new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/pea.png")):
                new Image(getClass().getResourceAsStream("images/Mower,sun,pea,lock/pea.png"));
    }

    public void update(double deltaTime) {
        x += speed * deltaTime;
        view.setLayoutX(x);
    }

    public boolean checkCollision(Zombie zombie) {
        System.out.println(x + "    " + zombie.getX());
        // Simple bounding box collision
        return zombie.getRow() == this.row &&
                (x - 250 )>= zombie.getX() - 30 &&
                (x - 250) <= zombie.getX() + 30;
    }

    public void applyEffect(Zombie zombie) {
        zombie.takeDamage(damage);
        if (isFrozen) {
           // zombie.applySlow(3.0); // Slow for 3 seconds
        }
    }

    // Getters
    public ImageView getView() { return view; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getRow() { return row; }
    public boolean isOutOfBounds(double screenWidth) {
        return x > screenWidth;
    }
}