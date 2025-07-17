package game.plantsvszambies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadManager {

    public static void saveGame(String filename) {
        SaveGameData saveData = new SaveGameData();
        saveData.plants = new ArrayList<>();
        saveData.zombies = new ArrayList<>();

        for (Plant p : Game.getInstance().getPlants()) {
            PlantData pd = new PlantData();
            pd.type = p.getClass().getSimpleName();
            pd.row = p.getRow();
            pd.col = p.getCol();
            pd.health = (int) p.getHealth();
            saveData.plants.add(pd);
        }

        for (Zombie z : Game.getInstance().getZombies()) {
            ZombieData zd = new ZombieData();
            zd.type = z.getClass().getSimpleName();
            zd.row = z.getRow();
            zd.column = z.getColumn();
            zd.health = z.health;
            saveData.zombies.add(zd);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void StupidSave(){
        Game.getInstance().clearGame();
    }

    public static void loadGame(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            SaveGameData saveData = gson.fromJson(reader, SaveGameData.class);

            Game.getInstance().clearGame(); // You should implement this

            for (PlantData pd : saveData.plants) {
                Plant plant = Game.getInstance().createPlantByType(pd.type, pd.row, pd.col);
                if (plant != null) {
                    plant.health = pd.health;
                    //Game.getInstance().addPlant(plant);
                }
            }
             // Load Zombies
            /*for (ZombieData zd : saveData.zombies) {
                Zombie zombie = Game.getInstance().createZombieByType(zd.type, zd.row);
                if (zombie != null) {
                    zombie.health = zd.health;
                    zombie.column = zd.column;
                    zombie.getView().setLayoutX(zd.column * 80);
                    Game.getInstance().addZombie(zombie);
                }
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ----- Helper Classes (not public) -----

class PlantData {
    String type;
    int row, col, health;
}

class ZombieData {
    String type;
    int row;
    double column;
    int health;
}

class SaveGameData {
    List<PlantData> plants;
    List<ZombieData> zombies;
}
