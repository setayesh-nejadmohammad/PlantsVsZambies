package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/*
public class CherryBomb extends Plant{
    private Timeline explosionTimeline;
    public CherryBomb(int row, int col, Map map, List<Zombie> zombies){
        super(row, col, 7, 150, 15);
        setupExplosionTimeline(map);
    }

    private void setupExplosionTimeline(Map map) {
        explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), // هر 1 ثانیه
                        event -> explodeZombies(map)
                ));
        explosionTimeline.setCycleCount(Timeline.INDEFINITE); // تکرار بی‌نهایت
        explosionTimeline.play();
    }

    public void explodeZombies(Map map) {
        */
/*Cell cell1 = getCellFromGridPane(map.grid, col+1, row);
        if(cell1 != null) {
            List<Zombie> zombies1 = cell1.getZombies();
            for(Zombie zombie : zombies1) {
                System.out.println("explode zombie");
                cell1.removeZombie(zombie);
                map.borderPane.getChildren().remove(zombie.getView());
            }
        }*//*

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){

            }
        }
    }

    public Cell getCellFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col &&
                    GridPane.getRowIndex(node) == row &&
                    node instanceof Cell) {
                return (Cell) node;
            }
        }
        return null;
    }


    public void destroy() {
        if (explosionTimeline != null) {
            explosionTimeline.stop();
        }
    }
}
*/

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

public class CherryBomb extends Plant{
    private static final int COST = 150;
    private static final int COOLDOWN = 50;
    private static final int DAMAGE = 1800;
    private static final int EXPLOSION_RANGE = 1; // 1 tile in all directions (3x3 area)

    private ImageView imageView;
    private int row;
    private int col;
    private Timeline explosionTimeline;

    public CherryBomb(int row, int col, Map map, List<Zombie> zombies, StackPane pane) {
        super(row, col, 5, 150, 15);

        // Load cherry bomb image
        Image image = new Image(getClass().getResourceAsStream("images/Plants/cherrybomb.gif"));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);

        // Set position
        this.imageView.setX(col * 100 + 10); // Assuming each cell is 100px wide
        this.imageView.setY(row * 120 + 20); // Assuming each row is 120px tall
        pane.getChildren().add(this.imageView);

        // Setup explosion animation
        setupExplosion(zombies, pane);

        System.out.println("a New Cherry at row: " + row + ", col: " + col);
    }

    private void setupExplosion(List<Zombie> zombies, StackPane pane) {
        // After 1.5 seconds, explode
        explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.7), e -> explode(zombies, pane))
        );
        explosionTimeline.setCycleCount(1);
        explosionTimeline.play();
    }

    public void explode(List<Zombie> zombies, StackPane pane) {
        // Change to explosion image
        Image explosionImage = new Image(getClass().getResourceAsStream("images/Plants/Boom.gif"));
        imageView.setImage(explosionImage);

        // Damage zombies in 3x3 area
        for (Zombie zombie : zombies) {
            if(zombie.getRow() == row+2){
                zombie.die();
            }
            /*if (isInExplosionRange(zombie)) {
                System.out.println("cheery found a zombie to kill!");
                zombie.die();
            }*/
        }

        // Remove explosion image after animation
        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.7), e -> pane.getChildren().remove(this.imageView))
        );
        removeTimeline.play();

    }

    private boolean isInExplosionRange(Zombie zombie) {
        int zombieRow = zombie.getRow();
        int zombieCol = (int)zombie.getColumn();

        if(zombieRow >= row - 1 && zombieRow <= row + 1 && zombieCol >= col - 1 && zombieCol <= col + 1){
            System.out.println("find a Zombie in the area!!!!");
            return true;
        }
        return false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static int getCooldown() {
        return COOLDOWN;
    }

    public void stop() {
        if (explosionTimeline != null) {
            explosionTimeline.stop();
        }
    }
}
