package game.plantsvszambies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;

public abstract class Zombie extends Thread {
    protected int row;
    protected int col;
    protected int health;
    protected int speed; // واحد حرکت در ثانیه
    protected boolean alive = true;
    protected Map map;
    Image image = new Image(getClass().getResourceAsStream("images/Zombie/normalzombie.gif"));
    ImageView imageView = new ImageView(image);
    int CELL_SIZE = map.CELL_SIZE;

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
                Thread.sleep(1500); // حرکت در هر ثانیه

                int previousCol = col;
                col--; // حرکت به چپ

                Platform.runLater(() -> {
                    // حذف از خانه قبلی و افزودن به خانه جدید
                    GridPane grid = map.getGridPane();

                    // حذف از موقعیت قبلی
                    grid.getChildren().remove(imageView);

                    // اضافه به موقعیت جدید
                    grid.add(imageView, col, row);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Platform.runLater(() -> map.getStackPane(row, col).getChildren().add(imageView));

        // while (alive && col > 0) {
        //     try {
        //         Thread.sleep(2000); // تاخیر بین حرکات

        //         int prevCol = col;
        //         int newCol = col - 1;
        //         col = newCol;

        //         Platform.runLater(() -> {
        //             TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);
        //             transition.setByX(-CELL_SIZE); // حرکت به چپ یک خانه
        //             transition.setOnFinished(e -> {
        //                 // بعد از حرکت، موقعیت منطقی زامبی آپدیت شده (col--) است
        //                 // می‌تونی اینجا برخورد یا بررسی جدید انجام بدی
        //             });
        //             transition.play();
        //         });

        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        // while (alive && col > 0) {
        //     try {
        //         Thread.sleep(1000 / speed);
        //         Cell cell = map.getCell(row, col);
        //         Plant plant = cell.getPlant();
        //         map.moveZombieForward(this);
        //         if (plant != null) {
        //             plant.takeDamage(1);
        //             if (plant.isDead()) cell.removePlant();
        //         } else {
        //             map.moveZombieForward(this);
        //         }
        //     } catch (InterruptedException e) {
        //         break;
        //     }
        // }
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
