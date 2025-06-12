package game.plantsvszambies;

import java.util.concurrent.*;

public class Sunflower extends Plant {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Sunflower(int row, int col) {
        super(row, col, 5, 50, 7);
        startProducing();
    }

    private void startProducing() {
        scheduler.scheduleAtFixedRate(() -> {
            //Sun sun = new Sun(row, col);
            System.out.println("Sun produced at (" + row + "," + col + ")");
            
        }, 0, 6, TimeUnit.SECONDS);
    }

    

    public void stop() {
        scheduler.shutdown();
    }
}
