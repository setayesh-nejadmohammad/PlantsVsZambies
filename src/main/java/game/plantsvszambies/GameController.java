package game.plantsvszambies;

public class GameController {
    int totalScore = 0;

    public void addScore(int score) {
        totalScore += score;
        System.out.println("Total Score: " + totalScore);
    }
}
