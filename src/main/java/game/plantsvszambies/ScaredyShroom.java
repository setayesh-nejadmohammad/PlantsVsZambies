package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ScaredyShroom extends ShooterPlant{
    private boolean isScared = false;
    public ScaredyShroom(int row, int col, ImageView imageView){
        super(row, col, 5, 25, 5, imageView);
        this.fireRate = 1.5;
        //view.setImage(new Image(getClass().getResourceAsStream("images/Plants/ScaredyShroom.gif")));
    }

    public void update(double deltaTime) {

        if(isTimeToBeScared() && !isScared){
            view.setImage(new Image(getClass().getResourceAsStream("images/Plants/ScaredyShroomCry.gif")));
            isScared = true;
            return;
        }
        else if(!isTimeToBeScared() && isScared){
            view.setImage(new Image(getClass().getResourceAsStream("images/Plants/ScaredyShroom.gif")));
            isScared = false;
        }
        if(isScared) return;

        timeSinceLastShot += deltaTime;

        if (timeSinceLastShot >= 1.0 / fireRate) {
            if (hasZombieInLane()) {

                shoot();
                timeSinceLastShot = 0;
            }
        }

    }
    protected void shoot() {
        Bullet bullet = new Bullet(
                this.getX() + 10,     // Start slightly ahead of plant
                this.getY()+30,     // Center vertically
                this.getRow(),
                damage,
                300.0,                // Pixel per second speed
                isSnowPea,
                true
        );

        Game.getInstance().addBullet(bullet);
    }

    public boolean isTimeToBeScared(){
        for(Zombie z: Game.getInstance().getZombies()){
            if(z.getRow() == this.row && z.getColumn() <= this.col + 3){
                return true;
            }
        }
        return false;
    }
}