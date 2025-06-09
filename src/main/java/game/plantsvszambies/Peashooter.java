package game.plantsvszambies;

public class Peashooter extends Plant {

    public Peashooter(int row, int col) {
        super(row, col, 5, 100, 7); // health, cost, rechargeTime
    }

    /*@Override
    public void act(Map map) {
        Bullet bullet = new Bullet(row, col + 1, 1, BulletType.NORMAL);
        map.getCell(row, col).addBullet(bullet);
        new Thread(bullet).start();
    }*/
}