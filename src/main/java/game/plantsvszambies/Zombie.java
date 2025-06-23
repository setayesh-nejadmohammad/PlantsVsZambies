package game.plantsvszambies;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

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
            dieWithShooter();
        }
    }
    public double getX(){
        return view.getLayoutX();
    }
    public double getY(){
        return view.getY();
    }

    public void dieWithShooter() {
        Game.getInstance().removeZombie(this);
    }
   public void die() {
       //System.out.println("Zombie number "+ID+" died at row: " + row + ", col: " + column);

       // change imageView to DEATH MOD
       Image deathImage = new Image(getClass().getResourceAsStream("images/Zombie/burntZombie.gif"));
       view.setImage(deathImage);
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
    // Getters
    public ImageView getView() { return view; }
    public int getRow() { return row; }
    public double getColumn() { return column; }
}
