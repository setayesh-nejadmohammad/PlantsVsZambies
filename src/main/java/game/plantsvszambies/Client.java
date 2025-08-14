/*
package game.plantsvszambies;

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
                        int lane = Integer.parseInt(parts[2]);
                        Zombie z = ZombieFactory.createZombie(type, lane);
                        Mapp.addZombie(z);
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
*/
