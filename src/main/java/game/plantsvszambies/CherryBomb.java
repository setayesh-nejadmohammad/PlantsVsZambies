package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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

    private double x;
    private double y;

    public CherryBomb(int row, int col, Map map, StackPane pane, ImageView imageView) {
        super(row, col, 5, 150, 15, imageView);

        Bounds boundsInScene = pane.localToScene(pane.getBoundsInLocal());
        this.x = boundsInScene.getMinX();
        this.y = boundsInScene.getMinY();

        // Load cherry bomb image
        Image image = new Image(getClass().getResourceAsStream("images/Plants/cherrybomb.gif"));
        //this.imageView = new ImageView(image);
        //this.imageView.setFitWidth(80);
        //this.imageView.setFitHeight(80);
        view.setImage(image);
        view.setFitWidth(80);
        view.setFitHeight(80);

        // Set position
        view.setX(col * 100 + 10); // Assuming each cell is 100px wide
        view.setY(row * 120 + 20); // Assuming each row is 120px tall
        pane.getChildren().add(view);

        // Setup explosion animation
        setupExplosion(pane);

        System.out.println("a New Cherry at row: " + row + ", col: " + col);
    }

    private void setupExplosion(StackPane pane) {
        // After 1.5 seconds, explode
        explosionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.6), e -> explode(pane))
        );
        explosionTimeline.setCycleCount(1);
        explosionTimeline.play();
    }

    public void update(double deltaTime){
        // سرکاریه
    }

    public void explode(StackPane pane) {
        // Change to explosion image
        Image explosionImage = new Image(getClass().getResourceAsStream("images/Plants/Boom.gif"));
        view.setImage(explosionImage);

        // Damage zombies in 3x3 area
        for (Zombie zombie : Game.getInstance().getZombies()) {
            //System.out.println("Zombie at row: "+ zombie.getView().getLayoutX()+250 + "col: " + zombie.getView().getLayoutY()+85);
            if(zombie.getView().getLayoutX()+200 < x+130 && zombie.getView().getLayoutY()+50 < y+130
               && zombie.getView().getLayoutX()+200 > x-130 && zombie.getView().getLayoutY()+50 > y-100) {
                zombie.die();
            }
            /*if(zombie.getView().getLayoutX()+250 < pane.getLayoutX()+80 && zombie.getView().getLayoutY()+85 < pane.getLayoutY()+90
               && zombie.getView().getLayoutX()+250 > pane.getLayoutX()-80 && zombie.getView().getLayoutY()+85 > pane.getLayoutY()-90) {
                zombie.die();
            }
            if (isInExplosionRange(zombie)) {
                System.out.println("cheery found a zombie to kill!");
                zombie.die();
            }*/
        }

        // Remove explosion image after animation
        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.7), e -> pane.getChildren().remove(view))
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
        return view;
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
