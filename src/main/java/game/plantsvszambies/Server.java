package game.plantsvszambies;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
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

    }

    private void listenToClient() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equals("ClientWON")) {
                    Game.getInstance().getGameLoop().stop();
                    Game.getInstance().showGameOver(false);
                    break;
                } else if (msg.equals("ClientLOST")) {
                    System.out.println("Client lost and I WONNNNNNNNNN");
                    //out.println("GAME_OVER LOST");
                    Game.getInstance().getGameLoop().stop();
                    Game.getInstance().showGameOver(true);
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
    public void setOut(PrintWriter out) {
        this.out = out;
    }
}