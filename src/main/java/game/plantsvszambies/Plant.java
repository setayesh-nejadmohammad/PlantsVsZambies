package game.plantsvszambies;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class Plant {
    private List<Zombie> attackingZombies = new ArrayList<>();
    protected int row;
    protected int col;
    protected int health;
    protected int cost;
    protected float rechargeTime;
    protected ImageView view;

    public Plant(int row, int col, int health, int cost, float rechargeTime, ImageView view) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.cost = cost;
        this.rechargeTime = rechargeTime;
        this.view = view;
    }


    public void removeAttacker(Zombie zombie) {
        attackingZombies.remove(zombie);
    }
    abstract void update(double deltaTime);


    //public abstract void act(Map map); // حرکت/شلیک یا تولید خورشید

    public void takeDamage(int amount) {
        health -= amount;
        playDamageAnimation();

        if (health <= 0) {
            destroy();
        }
    }
    public double getHealth() {
        return health;
    }
    public void clearAttackers() {
        attackingZombies.clear();
    }
    protected void destroy() {
        // Particle effect
        // createExplosionParticles();
        new ArrayList<>(attackingZombies).forEach(zombie -> {
            zombie.stopEating();
            zombie.removeFromPlant();// Zombie's method that calls removeAttacker
        });

        // Remove from game
        ((StackPane)view.getParent()).getChildren().remove(view);
        //Game.getInstance().map.grid.getChildren().remove(view.getParent());
        Game.getInstance().removePlant(this);
    }
    public void repositionAttackers() {
        double spacing = 0.6 / attackingZombies.size();

        for (int i = 0; i < attackingZombies.size(); i++) {
            double offset = -0.3 + (i * spacing);
            attackingZombies.get(i).setAttackOffset(offset);
        }
    }
    public void addAttacker(Zombie zombie) {
        if (!attackingZombies.contains(zombie)) {
            attackingZombies.add(zombie);
            repositionAttackers(); // Recalculate positions
        }
    }

    private double calculateOffset(int index, int total) {
        // Distribute zombies evenly across attack range
        double spacing = 0.6 / total; // 60% of cell width divided
        return -0.3 + (index * spacing); // -0.3 to +0.3 range
    }


    protected void playDamageAnimation() {
        // Flash effect
        Timeline flash = new Timeline(
                new KeyFrame(Duration.millis(50),
                        new KeyValue(view.opacityProperty(), 0.5)),
                new KeyFrame(Duration.millis(100),
                        new KeyValue(view.opacityProperty(), 1.0))
        );
        flash.play();

        Timeline shake = new Timeline(
                // Move right
                new KeyFrame(Duration.millis(50),
                        new KeyValue(view.translateXProperty(), 5)),
                // Move left
                new KeyFrame(Duration.millis(100),
                        new KeyValue(view.translateXProperty(), -5)),
                // Return to center
                new KeyFrame(Duration.millis(150),
                        new KeyValue(view.translateXProperty(), 0))
        );
        shake.setCycleCount(2); // Shake twice
        shake.play();
    }

    public boolean isDead() {
        return health <= 0;
    }
    public double getX() {

        return col * 80 + 300;

    }
    public double getY() {

        return row * 90 + 97;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getCost() { return cost; }
}