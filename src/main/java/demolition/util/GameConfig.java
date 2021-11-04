package demolition.util;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private ArrayList<GameLevel> levels = new ArrayList<>();
    private int lives;

    public List<GameLevel> getLevels() {
        return levels;
    }
    public void addLevel(GameLevel gLevel) {
        this.levels.add(gLevel);
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
}
