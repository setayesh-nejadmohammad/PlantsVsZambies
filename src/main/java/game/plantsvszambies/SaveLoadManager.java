package game.plantsvszambies;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadManager {

    /*public static void saveGame(String filename){
        SaveGameData saveData = new SaveGameData();
        saveData.plants = new ArrayList<>();
        //saveData.zombies = new ArrayList<>();

        if (Game.getInstance().getPlants() != null) {
            for (Plant p : Game.getInstance().getPlants()) {
                PlantData pd = new PlantData();
                //pd.type = p.getClass().getSimpleName();
                pd.row = p.getRow();
                pd.col = p.getCol();
                pd.health = (int) p.getHealth();
                saveData.plants.add(pd);
            }
        }

        */
    /*for (Zombie z : Game.getInstance().getZombies()) {
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
    }*/

    public static void saveGame(String filename, int score) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("CARDS ");
            for(String s : Game.getInstance().chosenCards){
                writer.write(s);
                writer.write(" ");
            }
            writer.write("\n");

            writer.write("SCORE ");
            writer.write(String.format("%d",score));
            writer.write("\n");

            for (Plant plant : Game.getInstance().getPlants()) {
                String line = String.format("PLANT %s %d %d %d",
                        plant.getClass().getSimpleName(),
                        plant.getRow(),
                        plant.getCol(),
                        (int) plant.getHealth()
                );
                writer.write(line + "\n");
            }
            for (Zombie z : Game.getInstance().getZombies()) {
                String line = String.format("ZOMBIE %s %d %f %d %f %f",
                        z.getClass().getSimpleName(),
                        z.getRow(),
                        z.getColumn(),
                        (int) z.health,
                        z.getView().getLayoutX(),
                        z.getView().getLayoutY()
                );
                writer.write(line + "\n");
            }
            for (Zombie z : Game.getInstance().getHZombies()) {
                String line = String.format("HZOMBIE %s %d %f %d %f %f",
                        z.getClass().getSimpleName(),
                        z.getRow(),
                        z.getColumn(),
                        (int) z.health,
                        z.getView().getLayoutX(),
                        z.getView().getLayoutY()
                );
                writer.write(line + "\n");
            }
            String sTime = String.format("TIME %d", Game.getInstance().getTime());
            writer.write(sTime);
            writer.write("\n");
            String phase = String.format("currentP %d", Game.getInstance().getCurrentPhaseS());
            writer.write(phase);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void loadGame(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            SaveGameData saveData = gson.fromJson(reader, SaveGameData.class);

            Game.getInstance().clearGame();

            for (PlantData pd : saveData.plants) {
                Plant plant = Game.getInstance().createPlantByType(pd.type, pd.row, pd.col);
                if (plant != null) {
                    plant.health = pd.health;
                    //Game.getInstance().addPlant(plant);
                }
            }
             // Load Zombies
            */
    /*for (ZombieData zd : saveData.zombies) {
                Zombie zombie = Game.getInstance().createZombieByType(zd.type, zd.row);
                if (zombie != null) {
                    zombie.health = zd.health;
                    zombie.column = zd.column;
                    zombie.getView().setLayoutX(zd.column * 80);
                    Game.getInstance().addZombie(zombie);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void loadGame(String filename, List<Plant> plants, List<Zombie> zombies,List<Zombie> hZombies, ArrayList<String> chosenCards, int[] score , long time) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            Game.getInstance().clearGame();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("PLANT")) {
                    String type = parts[1];
                    int row = Integer.parseInt(parts[2]);
                    int col = Integer.parseInt(parts[3]);
                    int health = Integer.parseInt(parts[4]);

                    Plant plant = Game.getInstance().createPlantByType(type, row, col);
                    if (plant != null) {
                        plant.health = health;
                        plants.add(plant);
                    }
                }
                else if(parts[0].equals("CARDS")) {
                    for(int i = 1; i <= 6; i++){
                        chosenCards.add(parts[i]);
                    }
                }
                else if(parts[0].equals("SCORE")) {
                    if(score != null) score[0] = Integer.parseInt(parts[1]);
                }
                else if (parts[0].equals("ZOMBIE")) {
                        String type = parts[1];
                        int row = Integer.parseInt(parts[2]);
                        double col = Double.parseDouble(parts[3]);
                        int health = Integer.parseInt(parts[4]);
                        double layX = Double.parseDouble(parts[5]);
                        double layY = Double.parseDouble(parts[6]);
                        Zombie zombie = Game.getInstance().createZombieByType(type, row, col);
                        zombie.setHealth(health);
                        zombie.getView().setLayoutX(layX);
                        zombie.getView().setLayoutY(layY);
                        zombie.getView().setTranslateX(180);
                        zombies.add(zombie);
                }
                else if (parts[0].equals("HZOMBIE")) {
                    String type = parts[1];
                    int row = Integer.parseInt(parts[2]);
                    double col = Double.parseDouble(parts[3]);
                    int health = Integer.parseInt(parts[4]);
                    double layX = Double.parseDouble(parts[5]);
                    double layY = Double.parseDouble(parts[6]);
                    Zombie zombie = Game.getInstance().createZombieByType(type, row, col);
                    zombie.setHealth(health);
                    zombie.getView().setLayoutX(layX);
                    zombie.getView().setLayoutY(layY);
                    zombie.getView().setTranslateX(180);
                    zombie.getView().setScaleX(-1);
                    zombie.isR(true);
                    zombie.setHypnotized(true);
                    hZombies.add(zombie);
                }
                else if (parts[0].equals("TIME")) {
                    Game.getInstance().setTime(Long.parseLong(parts[1]));
                }
                else if (parts[0].equals("PLANT")) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

// ----- Helper Classes (not public) -----

/*class PlantData {
    String type;
    int row, col, health;
}*/
