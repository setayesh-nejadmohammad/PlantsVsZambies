package game.plantsvszambies;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(this::listenToServer).start();
    }
    private void listenToServer() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                String[] parts = msg.split(" ");
                switch (parts[0]) {
                    case "SPAWN":
                        String type = parts[1];
                        int row = Integer.parseInt(parts[2]);
                        Zombie z = Game.getInstance().createZombieByType(type, row, 9.3);
                        Game.getInstance().positionZombie(z);
                        Game.getInstance().getZombies().add(z);
                        Platform.runLater(() -> {
                            Game.getInstance().map.borderPane.getChildren().add(z.getView());
                        });
                        break;
                    case "GAME_OVER":
                        if (parts[1].equals("WIN")) {
                            Game.getInstance().showGameOver(true);
                            System.out.println("You Win!");
                        } else {
                            Game.getInstance().showGameOver(false);
                            System.out.println("You Lose!");
                        }
                        break;
                    case "gr":
                        Game.getInstance().getMap().getGravePosPairs()[0][0] = Integer.parseInt(parts[1]);
                        Game.getInstance().getMap().getGravePosPairs()[0][1] = Integer.parseInt(parts[2]);
                        Game.getInstance().getMap().getGravePosPairs()[1][0] = Integer.parseInt(parts[3]);
                        Game.getInstance().getMap().getGravePosPairs()[1][1] = Integer.parseInt(parts[4]);
                        Game.getInstance().getMap().getGravePosPairs()[2][0] = Integer.parseInt(parts[5]);
                        Game.getInstance().getMap().getGravePosPairs()[2][1] = Integer.parseInt(parts[6]);
                        Game.getInstance().getMap().getGravePosPairs()[3][0] = Integer.parseInt(parts[7]);
                        Game.getInstance().getMap().getGravePosPairs()[3][1] = Integer.parseInt(parts[8]);
                        Game.getInstance().getMap().getGravePosPairs()[4][0] = Integer.parseInt(parts[9]);
                        Game.getInstance().getMap().getGravePosPairs()[4][1] = Integer.parseInt(parts[10]);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getOut() {
        return out;
    }
    private void WinnerWinner() {
        Platform.runLater(()->{
            StackPane pane = new StackPane();
            Scene scene = new Scene(pane, 1024, 626);
            Label label = new Label("Winner Winner Chicken Dinner (client WON)");
            pane.getChildren().add(label);
            Game.getInstance().getGameLoop().stop();
            Game.getInstance().map.stage.setScene(scene);
        });
    }
    private void Looooser(){
        Platform.runLater(()->{
            StackPane pane = new StackPane();
            Scene scene = new Scene(pane, 1024, 626);
            Label label = new Label("LOOOOser (client LOST)");
            pane.getChildren().add(label);
            Game.getInstance().getGameLoop().stop();
            Game.getInstance().map.stage.setScene(scene);
        });
    }
}