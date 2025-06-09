package game.plantsvszambies;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int row;
    private final int col;
    private Plant plant;
    private final List<Zombie> zombies;
   // private final List<Bullet> bullets;
    private final StackPane stackPane;

    public Cell(int row, int col, StackPane stackPane) {
        this.row = row;
        this.col = col;
        this.stackPane = stackPane;
        this.zombies = new ArrayList<>();
        //this.bullets = new ArrayList<>();
    }

    // --- Plant ---
    public void setPlant(Plant plant) {
        this.plant = plant;
       // Node plantView = plant.getView(); // فرض: هر plant نمای گرافیکی دارد
        //stackPane.getChildren().add(plantView);
    }

    public void removePlant() {
        if (plant != null) {
            //stackPane.getChildren().remove(plant.getView());
            this.plant = null;
        }
    }

    public Plant getPlant() {
        return plant;
    }

    public boolean isEmpty() {
        return plant == null;
    }

    // --- Zombie ---
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
        //stackPane.getChildren().add(zombie.getView()); // نمای گرافیکی
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
        //stackPane.getChildren().remove(zombie.getView());
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    // --- Bullet ---
    /*public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        stackPane.getChildren().add(bullet.getView());
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        stackPane.getChildren().remove(bullet.getView());
    }

    public List<Bullet> getBullets() {
        return bullets;
    }*/

    public StackPane getStackPane() {
        return stackPane;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
