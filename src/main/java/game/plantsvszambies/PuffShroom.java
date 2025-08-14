package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PuffShroom extends ShooterPlant{
    public PuffShroom(int row, int col, ImageView imageView){
        super(row, col, 5, 0, 5, imageView);
        this.fireRate = 0.9;
        this.isNightPlant = true;
        if(!Game.isNight) {
            this.isSleeping = true;
            view.setImage(new Image(getClass().getResourceAsStream("images/Plants/PuffShroomSleep.gif")));
        }
    }
    protected void shoot() {
        if(isSleeping) {return;}
        Bullet bullet = new Bullet(
                this.getX() + 10,     // Start slightly ahead of plant
                this.getY()+45,     // Center vertically
                this.getRow(),
                damage,
                300.0,                // Pixel per second speed
                isSnowPea,
                true
        );

        Game.getInstance().addBullet(bullet);
    }
    protected boolean hasZombieInLane() {
        // Check if any zombies exist in this row to the right of plant

        for (Zombie zombie : Game.getInstance().getZombies()) {
            if (zombie.getRow() == this.getRow() && zombie.getView().getLayoutX() >= this.getCol() * 80 + 70
                    && zombie.getColumn() <= this.getCol()+4) {

                return true;
            }
        }
        return false;
    }

    public void beAwake(){
        isSleeping = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Plants/PuffShroom.gif")));
    }
}