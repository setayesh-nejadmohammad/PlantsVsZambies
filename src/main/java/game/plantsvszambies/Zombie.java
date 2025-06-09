package game.plantsvszambies;

public abstract class Zombie extends Thread {
    protected int row;
    protected int col;
    protected int health;
    protected int speed; // واحد حرکت در ثانیه
    protected boolean alive = true;
    protected Map map;

    public Zombie(int row, int col, Map map, int health, int speed) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.speed = speed;
        this.map = map;
    }

    @Override
    public void run() {
        while (alive && col > 0) {
            try {
                Thread.sleep(1000 / speed);
                Cell cell = map.getCell(row, col);
                Plant plant = cell.getPlant();
                if (plant != null) {
                    plant.takeDamage(1);
                    if (plant.isDead()) cell.removePlant();
                } else {
                    map.moveZombieForward(this);
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) alive = false;
    }

    /*public boolean isAlive() {
        return alive;
    }*/

    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}
