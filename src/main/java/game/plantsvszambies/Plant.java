package game.plantsvszambies;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public abstract class Plant {
    protected int row;
    protected int col;
    protected int health;
    protected int cost;
    protected int rechargeTime;
    protected ImageView view;

    public Plant(int row, int col, int health, int cost, int rechargeTime, ImageView view) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.cost = cost;
        this.rechargeTime = rechargeTime;
        this.view = view;
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
    protected void destroy() {
        // Particle effect
        // createExplosionParticles();


        // Remove from game
        ((StackPane)view.getParent()).getChildren().remove(view);
        //Game.getInstance().map.grid.getChildren().remove(view.getParent());
        Game.getInstance().removePlant(this);
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

        // Shake effect
        TranslateTransition shake = new TranslateTransition(
                Duration.millis(100), view);
        shake.setByX(5);
        shake.setCycleCount(2);
        shake.setAutoReverse(true);
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