/*
package game.plantsvszambies;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Random random = new Random();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started. Waiting for client...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(this::listenToClient).start();

        // شروع تایمر زامبی
        new Thread(this::spawnLoop).start();
    }

    private void spawnLoop() {
        try {
            while (true) {
                Thread.sleep(3000);
                String type = randomZombie();
                int lane = random.nextInt(5);

                // اضافه به بازی سرور
                Zombie z = ZombieFactory.createZombie(type, lane);
                Mapp.addZombie(z);

                // ارسال به کلاینت
                out.println("SPAWN " + type + " " + lane);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String randomZombie() {
        String[] types = {"Basic", "Conehead", "Buckethead"};
        return types[random.nextInt(types.length)];
    }

    private void listenToClient() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equals("LOST")) {
                    out.println("GAME_OVER WIN");
                    // سرور بازنده = پایان بازی سمت خودش
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
