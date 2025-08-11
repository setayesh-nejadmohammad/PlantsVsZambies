package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plantern extends Plant {
    public Plantern(int row, int col, ImageView imageView) {
        super(row, col, 5, 75, 7.5f, imageView);
        this.isNightPlant = true;
        if(!Game.isNight) {
            this.isSleeping = true;
        }
        if(Game.isNight) lanteningAround();
    }
    public void update(double t){
        lanteningAround();
    }
    public void lanteningAround(){
        for(Fog f: Game.getInstance().map.fogs){
            if(f.getRow() <= row+1 && f.getRow() >= row-1
            && f.getCol() <= col+1 && f.getCol() >= col-1){
                f.imageView.setVisible(false);
                f.isPlanternInArea = true;
            }
        }
    }
}
