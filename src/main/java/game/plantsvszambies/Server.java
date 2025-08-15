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
        //ShowIP();
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
                    //out.println("GAME_OVER WIN");
                    Game.getInstance().getGameLoop().stop();
                    Game.getInstance().showGameOver(false);
                    break;
                    // سرور بازنده = پایان بازی سمت خودش
                }
                else if(msg.equals("ClientLOST")) {
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

    public void setOut(PrintWriter p){
        this.out = p;
    }
    public PrintWriter getOut(){
        return out;
    }
    public void printLnOut(String s){
        out.println(s);
    }
    private void WinnerWinner() {
        Platform.runLater(()->{
            Game.won = true;
            StackPane pane = new StackPane();
            Scene scene = new Scene(pane, 1024, 626);
            Label label = new Label("Winner Winner Chicken Dinner (server WON)");
            pane.getChildren().add(label);
            Game.getInstance().getGameLoop().stop();
            Game.getInstance().map.stage.setScene(scene);
        });
    }
    private void Looooser(){
        Platform.runLater(()->{
            Game.lost = true;
            StackPane pane = new StackPane();
            Scene scene = new Scene(pane, 1024, 626);
            Label label = new Label("LOOOOser (sever LOST)");
            pane.getChildren().add(label);
            Game.getInstance().getGameLoop().stop();
            Game.getInstance().map.stage.setScene(scene);
        });
    }
    private void ShowIP(){
        Platform.runLater(()->{
            StackPane pane = new StackPane();
            Scene IPscene = new Scene(pane, 1024, 626);
            String IP = getLocalIP();
            Label label1 = new Label("Server started. Waiting for client...");
            Label label = new Label("Server IP: " + IP);
            pane.getChildren().addAll(label1, label);
            Game.getInstance().map.stage.setScene(IPscene);
        });
    }
    private void GoToGame(){
        Platform.runLater(()->{
            Game.getInstance().map.stage.setScene(Game.getInstance().map.getScene());
        });
    }

    private String getLocalIP(){
        String s = "";
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            s = localHost.getHostAddress();
            System.out.println("Server IP: "+s);
        } catch (UnknownHostException e) {
            System.out.println("Could not determine IP address!");
            e.printStackTrace();
        }
        return s;
    }
    private String getLanIP(){
        String s = "";
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface netint = nets.nextElement();

                // کارت شبکه باید فعال باشه و Loopback نباشه
                if (netint.isUp() && !netint.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress addr = inetAddresses.nextElement();

                        // just IPv4 and no local
                        if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                            System.out.println("LAN IP: " + addr.getHostAddress());
                            s = addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}