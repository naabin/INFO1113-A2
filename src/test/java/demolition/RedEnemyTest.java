package demolition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import demolition.util.GameConfig;
import demolition.util.GameLevel;

public class RedEnemyTest {
    RedEnemy redEnemy;
    GameConfig config;
    GameLevel currentGameLevel;
    App app;
    String[][] grid;
    public RedEnemyTest() {
        this.app = new App();
        this.config = new GameConfig();
        this.redEnemy = new RedEnemy();
        this.app.loadConfig(config);
        this.currentGameLevel = this.config.getLevels().get(0);
        this.grid = this.currentGameLevel.getGridFromFile();
        this.redEnemy.setCoord(grid);
    }

    @Test
    public void testRedEnemyMove() {
        assertTrue(this.redEnemy.canMoveTo(new Integer[] {11, 7}, grid));
        redEnemy.move(grid);
        redEnemy.setNextCoord(new Integer[]{11, 7}, Orientation.LEFT);
        assertEquals(false, this.redEnemy.canMoveTo(new Integer[]{0, 0}, grid));
    }

    @Test
    public void testCoords() {
       Map<Orientation, Integer[]> coords =  redEnemy.getAdjacentCoords(grid);
       assertNotNull(coords);
       redEnemy.setRandomCoord(coords);
       assertNotEquals(redEnemy.getOriginalPosition(), new Integer[]{redEnemy.getxCoord(), redEnemy.getyCoord()});
    }
}