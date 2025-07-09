package game.plantsvszambies;


import javafx.scene.image.ImageView;

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

    public void takeDamage(int damage) {
        health -= damage;
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
