package game.plantsvszambies;

import javafx.application.Platform;

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

                        Platform.runLater(() -> {  // because it should be in JavaFx thread?!
                            Game.getInstance().map.borderPane.getChildren().add(z.getView());
                        });
                        //System.out.println("A Zombie created in client!");
                        //Zombie z = ZombieFactory.createZombie(type, lane);
                        //Mapp.addZombie(z);
                        break;
                    case "GAME_OVER":
                        if (parts[1].equals("WIN")) {
                            System.out.println("You Win!");
                        } else {
                            System.out.println("You Lose!");
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lost() {
        out.println("LOST");
    }
}
