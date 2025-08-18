package game.plantsvszambies;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class IceShroom extends Plant{
    StackPane pane;

    public IceShroom(int row, int col, StackPane pane, ImageView imageView) {
        super(row, col, 5, 75, 7, imageView);
        this.pane = pane;
        pane.getChildren().add(view);
        if(Game.isNight) act(pane);
        this.isNightPlant = true;
        if(!Game.isNight) {
            this.isSleeping = true;
            view.setImage(new Image(getClass().getResourceAsStream("images/Plants/IceShroomSleep.gif")));
        }
    }

    public void update(double time){}

    public void act(StackPane pane){
        freezAllZombies();

//        Timeline snowComing = new Timeline(
//                new KeyFrame(Duration.seconds(0.7), event -> {SnowGif(pane);})
//        );
//        snowComing.play();


        Timeline removeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.7), e -> {
                    pane.getChildren().remove(view);
                    Game.getInstance().getPlants().remove(this);
                })
        );
        removeTimeline.play();

        // after 5 sec release Zombies
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            releaseZombie();
        });
        pause.play();
    }

    public void freezAllZombies(){
        for(Zombie z: Game.getInstance().getZombies()){
            z.freezeZombie();
            if(z.getClass().getSimpleName().equals("NormalZombie")){
                z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/freezedZombie.png")));
            }
            else if(z.getClass().getSimpleName().equals("ConeheadZombie")){
                z.view.setImage(new Image (getClass().getResourceAsStream("images/Zombie/freezedconeheadZombie.png")));
            }
            else if(z.getClass().getSimpleName().equals("ScreenDoorZombie")){
                z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/freezedscreenDoorZombie.png")));
            }
            else if(z.getClass().getSimpleName().equals("ImpZombie")){
                z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/freezedImp.png")));
            }

            if(z.getClass().getSimpleName().equals("ImpZombie")){
                z.view.setFitHeight(60);
                z.view.setFitWidth(40);
                z.view.setTranslateX(z.view.getTranslateX()-15);
            }
            else {
                z.view.setFitHeight(85);
                z.view.setFitWidth(50);
            }

            if(!z.wasEating()){
                z.view.setLayoutY(z.view.getLayoutY() + 15);
                z.view.setTranslateX(z.view.getTranslateX() + 42);
            }
        }
    }


    private void releaseZombie(){
        for(Zombie z: Game.getInstance().getZombies()){
            if(z.getOriginalSpeed() == 0) {
                if (z.getClass().getSimpleName().equals("NormalZombie")) {
                    z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/normalZombie.gif")));
                } else if (z.getClass().getSimpleName().equals("ConeheadZombie")) {
                    z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/coneheadzombie.gif")));
                } else if (z.getClass().getSimpleName().equals("ScreenDoorZombie")) {
                    z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/screenDoorZombie.gif")));
                } else if (z.getClass().getSimpleName().equals("ImpZombie")) {
                    z.view.setImage(new Image(getClass().getResourceAsStream("images/Zombie/imp.gif")));
                }

                if (z.getClass().getSimpleName().equals("ImpZombie")) {
                    z.view.setFitWidth(64);
                    z.view.setFitHeight(80);
                    view.setTranslateX(z.view.getTranslateX() + 15);
                } else {
                    view.setFitHeight(100);
                    view.setFitWidth(95);
                }
                if (!z.wasEating()) {
                    z.view.setLayoutY(z.view.getLayoutY() - 15);
                    z.view.setTranslateX(z.view.getTranslateX() - 42);
                }
                else{
                    z.view.setTranslateX(z.view.getTranslateX() + 20);
                }
                z.resumeZombie();
            }
        }
    }

    public void beAwake(){
        isSleeping = false;
        view.setImage(new Image(getClass().getResourceAsStream("images/Plants/iceShroom.gif")));
        act(pane);
    }
}