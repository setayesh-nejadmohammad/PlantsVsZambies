package game.plantsvszambies;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    protected Map map;
    static int ZombieCounter = 0;
    protected int ID;

    public Zombie(int health, int damage, double speed, int row, Map map) {
        ZombieCounter++;
        this.ID = ZombieCounter;
        this.map = map;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.row = row;
        this.column = 9.3; // Starts at rightmost column
        this.view = createImageView();
        //System.out.println("Zombie num "+ID+"is at row "+row+" and column "+column);
    }

    protected abstract ImageView createImageView();

    public void update(double deltaTime) {
        if (!isEating) {
            // Move left
            /*column -= speed * deltaTime;
            view.setLayoutX(column * 80);*/

            // get the cell that zombie was in it before move
            Cell myCell = getCellFromGridPane(map.grid, (int)this.getColumn(), this.getRow());

            // check if zombies col has changed
            double newCol = column - speed * deltaTime;
            if((int)newCol != (int)column) { // if the col is changed
                //System.out.println("Zombie number "+ ID +" updated to " + (int)newCol);
                if(myCell != null) myCell.removeZombie(this);
                myCell = getCellFromGridPane(map.grid, (int)newCol, this.getRow());
                if(myCell != null) myCell.addZombie(this);
            }


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

    public void dieWithShooter(){
        Game.getInstance().removeZombie(this);
    }

    public void die() {
        System.out.println("Zombie number "+ID+" died at row: " + row + ", col: " + column);

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


            // remove zombie from the cell
            Cell currentCell = getCellFromGridPane(map.grid, (int)column, row);
            if (currentCell != null) {
                currentCell.removeZombie(this);
            }
        });
        delay.play();
    }
    public double getX(){
        return view.getLayoutX();
    }
    public double getY(){
        return view.getY();
    }

//    private void die() {
//        // Remove from game
//        Game.getInstance().removeZombie(this);
//    }

    // Getters
    public ImageView getView() { return view; }
    public int getRow() { return row; }
    public double getColumn() { return column; }
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
}
