package game.plantsvszambies;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ImpZombie extends Zombie{
    public ImpZombie(int row) {
        super(100, 25, 0.5, row);
    }
    public ImpZombie(int row, double col) {
        super(100, 25, 0.5, row);
        this.column = col;
    }

    @Override
    protected ImageView createImageView() {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
        view.setFitWidth(64);
        view.setFitHeight(80);
        view.setLayoutY(view.getLayoutY()-30);
        return view;
    }

    public void dieWithShooter(){
        if(isDead) return;
        isDead = true;
        Game.getInstance().removeZombie(this);

        // make the head
        ImageView headGif = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/ImpDie.gif")));
        headGif.setFitWidth(64);
        headGif.setFitHeight(80);

        // put in stackPane
        StackPane animationPane = new StackPane();
        animationPane.getChildren().addAll(headGif);

        // Put in Zombies Pos
        animationPane.setLayoutX(view.getX());
        animationPane.setLayoutY(view.getY());

        // add it to the scene
        Game.getInstance().map.grid.add(animationPane, (int)getColumn(), getRow());

        // remove it after a few sec
        PauseTransition delay = new PauseTransition(Duration.seconds(2.6));
        delay.setOnFinished(e -> Game.getInstance().map.grid.getChildren().remove(animationPane));
        delay.play();
    }

    public void die() {
        if(isDead) return;
        isDead = true;
        //System.out.println("Zombie number "+ID+" died at row: " + row + ", col: " + column);

        // change imageView to DEATH MOD
        Image deathImage = new Image(getClass().getResourceAsStream("images/Zombie/BoomDieImp.gif"));
        view.setImage(deathImage);
        //view.setLayoutX(view.getLayoutX());

        isEating = true;

        // 2 sec pause before remove zombie
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            // remove Zombie's ImageView from the Main pane
            if (view.getParent() != null) {
                ((BorderPane)view.getParent()).getChildren().remove(view);
            }

            // remove the zombie from zombies List
            Game.getInstance().getZombies().remove(this);

        });
        delay.play();
    }

    public void startEating() {
        this.isEating = true;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/impAttack.gif")));
        Plant plant = findPlantInFront();
        if (!hEat) {
            super.baseColumn = plant.getCol(); // Set reference point
            updateAttackPosition();
        }
        super.eatCooldown = super.eatInterval;
    }
    public void stopEating() {
        hEat = false;
        isEating = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
    }
}
