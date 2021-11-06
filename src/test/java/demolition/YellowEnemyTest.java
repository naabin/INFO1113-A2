package demolition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import demolition.util.GameConfig;
import demolition.util.GameLevel;

public class YellowEnemyTest {
    YellowEnemy yellowEnemy;
    GameConfig config;
    GameLevel currentGameLevel;
    App app;
    String[][] grid;

    public YellowEnemyTest() {
        this.app = new App();
        this.config = new GameConfig();
        this.yellowEnemy = new YellowEnemy();
        this.app.loadConfig(config);
        this.currentGameLevel = this.config.getLevels().get(0);
        this.grid = this.currentGameLevel.getGridFromFile();
        this.yellowEnemy.setCoord(grid);
    }

    @Test
    public void testMove() {
        Integer[] testCoord = new Integer[]{9, 6};
        assertEquals(true, this.yellowEnemy.canMoveTo(testCoord, grid));
        this.yellowEnemy.setCaughtInExplosion(false);
        assertEquals(false, this.yellowEnemy.isCaughtInExplosion());
        this.yellowEnemy.move(grid);
        this.yellowEnemy.setOrientation(Orientation.DOWN);
        this.yellowEnemy.move(grid);
        this.yellowEnemy.setOrientation(Orientation.LEFT);
        this.yellowEnemy.canMoveTo(new Integer[] {5, 9}, grid);
        this.yellowEnemy.move(grid);
        this.yellowEnemy.setOrientation(Orientation.UP);
        this.yellowEnemy.canMoveTo(testCoord, grid);
        this.yellowEnemy.move(grid);
        this.yellowEnemy.setOrientation(Orientation.RIGHT);
        this.yellowEnemy.move(grid);
    }
}
