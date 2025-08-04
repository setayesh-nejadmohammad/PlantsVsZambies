package game.plantsvszambies;

import javafx.animation.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

// Base Zombie class
public abstract class Zombie {
    private boolean hypnotized = false;

    private static final double ATTACK_OFFSET_RANGE = 0.3; // Cell fraction
    private double attackOffset;
    protected boolean isDead = false;
    private double originalSpeed;
    private double slowEndTime;
    private double currentSpeed;
    protected int health;
    protected int damage;
    protected double speed; // cells per second
    protected int row;
    protected double column; // Using double for smooth movement
    protected ImageView view;
    public boolean isEating = false;
    protected double eatCooldown = 0;
    protected double eatInterval = 1; // Bite every 0.5 seconds
    private int bitesToDestroy = 4; // Default bites needed
    private Animation eatAnimation;
    private ColorAdjust frostEffect = new ColorAdjust();
    private long frostEndTime;
    protected double baseColumn;

    public Zombie(int health, int damage, double speed, int row) {
        // Initialize with normal color
        this.attackOffset = (Math.random() * 2 - 1) * ATTACK_OFFSET_RANGE;
        this.health = health;
        this.damage = damage;
        this.originalSpeed = speed;
        this.row = row;
        this.column = 9.3; // Starts at rightmost column
        this.view = createImageView();
        view.setEffect(null);

        // Configure blue tint
        frostEffect.setHue(-0.8);  // -0.7 shifts to blue
        frostEffect.setSaturation(0.65);
        frostEffect.setBrightness(0.1);
    }

    protected abstract ImageView createImageView();
    public void applyFrostEffect(double durationSeconds) {
        // Apply blue tint
        this.frostEndTime = System.currentTimeMillis() + (long)(durationSeconds * 1000);
        view.setEffect(frostEffect);

        // Remove after duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(durationSeconds),
                e -> view.setEffect(null)));
        timeline.play();
    }

    public void setHypnotized(boolean hypnotized) {
        this.hypnotized = hypnotized;
    }

    public void update(double deltaTime) {
        if (isDead) {return;}
        if (System.currentTimeMillis() < frostEndTime) {
            view.setEffect(frostEffect);
        } else {
            view.setEffect(null);
        }
        if (System.currentTimeMillis() > slowEndTime) {
            currentSpeed = originalSpeed;
        }
        if (!isEating) {
            if (!isHypnotized()) {
                column -= currentSpeed * deltaTime;
                view.setLayoutX(column * 80);
            }
            else {
                column += currentSpeed * deltaTime;
                view.setLayoutX(column * 80);
            }

        }
        else {
            eatCooldown -= deltaTime;
            if (eatCooldown <= 0) {
                bitePlant();
                eatCooldown = eatInterval;
        }
        }
    }
    public boolean isHypnotized() {
        return hypnotized;
    }

    private void bitePlant() {
        Plant target = findPlantInFront();
        if (target != null) {
            target.takeDamage(1);


            if (target.getHealth() <= 0) {
                stopEating();
            }
        }
        else {
            stopEating();
        }
    }

    public Plant findPlantInFront() {
        return Game.getInstance().getPlants().stream()
                .filter(p -> p.getRow() == this.row)
                .filter(p -> ((this.column - p.getCol()) <= 1.3 && this.column - p.getCol() >= 0.5))
                .findFirst()
                .orElse(null);
    }
    public void startEating() {
        this.isEating = true;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/ZombieAttack.gif")));
        Plant plant = findPlantInFront();
        this.baseColumn = plant.getCol(); // Set reference point
        updateAttackPosition();
        this.eatCooldown = eatInterval;
    }


    public void stopEating() {
        isEating = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif")));
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
        if(isDead) return;
        if (isEating) {
            Plant target = Game.getInstance().findPlantBeingEaten(this);
            if (target != null) {
                target.removeAttacker(this); // <-- HERE
            }
        }
        isDead = true;
        Game.getInstance().removeZombie(this);

        // make the head
        ImageView headGif = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/ZombieHead.gif")));
        headGif.setFitWidth(80);
        headGif.setFitHeight(90);

        // make the body
        ImageView bodyGif = new ImageView(new Image(getClass().getResourceAsStream("images/Zombie/ZombieDie.gif")));
        bodyGif.setFitWidth(80);
        bodyGif.setFitHeight(90);

        // put in stackPane
        StackPane animationPane = new StackPane();
        animationPane.getChildren().addAll(bodyGif, headGif); // ترتیب مهمه: بدن زیر، کله بالا

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
       if (isEating) {
           Plant target = Game.getInstance().findPlantBeingEaten(this);
           if (target != null) {
               target.removeAttacker(this); // <-- HERE
           }
       }
       isDead = true;

       // change imageView to DEATH MOD
       Game.getInstance().getZombies().remove(this);
       Image deathImage = new Image(getClass().getResourceAsStream("images/Zombie/burntZombie.gif"));
       view.setImage(deathImage);
       view.setFitWidth(60);
       view.setLayoutX(view.getLayoutX()+30);
       isEating = true;

       // 5 sec pause before remove zombie
       PauseTransition delay = new PauseTransition(Duration.seconds(5));
       delay.setOnFinished(event -> {
           // remove Zombie's ImageView from the Main pane
           if (view.getParent() != null) {
               ((BorderPane)view.getParent()).getChildren().remove(view);
           }

           // remove the zombie from zombies List


       });
       delay.play();
   }

    // Getters
    public ImageView getView() { return view; }
    public int getRow() { return row; }
    public double getColumn() { return column; }

    public void applySlow(double duration, double factor) {
        this.currentSpeed = originalSpeed * factor;
        this.slowEndTime = System.currentTimeMillis() + (duration * 1000);

    }
    public void setAttackOffset(double offset) {
        this.attackOffset = Math.max(-0.3, Math.min(0.3, offset)); // Clamp value
        this.baseColumn = this.column; // Store original position

        updateAttackPosition();
    }
    protected void updateAttackPosition() {
        if (!isEating) return;

        // Calculate new position with offset
        double newX = view.getLayoutX() + attackOffset *  80;

        // Apply with smooth transition
        Timeline reposition = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(view.layoutXProperty(), newX, Interpolator.EASE_BOTH)
                ));
        reposition.play();
        reposition.setOnFinished(e -> view.setLayoutX(newX));

        // Adjust drawing order
        view.setViewOrder(attackOffset > 0 ? -1 : 1);
    }

    public void removeFromPlant() {
        Plant plant = Game.getInstance().findPlantBeingEaten(this);
        if (plant != null) {
            plant.removeAttacker(this);
        }
    }

    public boolean isEating() {
        return isEating;
    }
}
