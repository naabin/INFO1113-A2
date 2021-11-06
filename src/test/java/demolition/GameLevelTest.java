package demolition;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import demolition.util.GameLevel;

public class GameLevelTest {
    private GameLevel gameLevel;

    public GameLevelTest() {
        this.gameLevel = new GameLevel();
        this.gameLevel.setPath("level1.txt");
        this.gameLevel.setTime(180);
    }

    @Test
    public void testPath() {
        assertNotNull(this.gameLevel.getPath());
        assertNotNull(this.gameLevel.getGrids());
        assertNotNull(this.gameLevel.getTime());
    }

    @Test
    public void testException() {
        assertNotNull(this.gameLevel.getGridFromFile());
    }
}
