package game.plantsvszambies;

import javafx.scene.image.ImageView;

public class Fog {
    private int row;
    private int col;
    ImageView imageView;
    public boolean isPlanternInArea = false;
    public Fog(int row, int col, ImageView imageView){
        this.col = col;
        this.row = row;
        this.imageView = imageView;
    }

    public int getRow() {return row;}
    public int getCol() {return col;}
    public ImageView getImageView() {return imageView;}
    public void setRow(int row) {this.row = row;}
    public void setCol(int col) {this.col = col;}
    public void setImageView(ImageView imageView) {this.imageView = imageView;}
}
