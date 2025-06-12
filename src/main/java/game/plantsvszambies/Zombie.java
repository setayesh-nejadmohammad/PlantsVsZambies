package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Base Zombie class
public abstract class Zombie {
    protected int health;
    protected int damage;
    protected double speed; // cells per second
    protected int row;
    protected double column; // Using double for smooth movement
    protected ImageView view;
    protected boolean isEating = false;

    public Zombie(int health, int damage, double speed, int row) {
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.row = row;
        this.column = 9.3; // Starts at rightmost column
        this.view = createImageView();
    }

    protected abstract ImageView createImageView();

    public void update(double deltaTime) {
        if (!isEating) {
            // Move left
            column -= speed * deltaTime;
            view.setLayoutX(column * 80);
        }
    }

//    public void attack(Plant plant) {
//        isEating = true;
//        plant.takeDamage(damage);
//        // Play eating animation
//    }

    public void stopEating() {
        isEating = false;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
           // die();
        }
    }

//    private void die() {
//        // Remove from game
//        Game.getInstance().removeZombie(this);
//    }

    // Getters
    public ImageView getView() { return view; }
    public int getRow() { return row; }
    public double getColumn() { return column; }
}
