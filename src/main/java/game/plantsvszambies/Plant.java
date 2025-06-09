package game.plantsvszambies;


public abstract class Plant {
    protected int row;
    protected int col;
    protected int health;
    protected int cost;
    protected int rechargeTime;

    public Plant(int row, int col, int health, int cost, int rechargeTime) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.cost = cost;
        this.rechargeTime = rechargeTime;
    }

    //public abstract void act(Map map); // حرکت/شلیک یا تولید خورشید

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getCost() { return cost; }
}
