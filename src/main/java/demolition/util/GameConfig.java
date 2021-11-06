package demolition.util;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>This class stores the data coming from config.json</p>
 * {@link GameLevel} stores the path for the game map and time. 
 */
public class GameConfig {
    private ArrayList<GameLevel> levels = new ArrayList<>();
    private int lives;
    
    /**
     * 
     * @return all levels from read from config.json
     */
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
