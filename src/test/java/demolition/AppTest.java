package demolition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest{

    App app;

    public AppTest() {
        this.app = new App();
    }
    
    @Test
    public void simpleTest() {
        assertEquals(480, App.HEIGHT);
        assertEquals(480, App.WIDTH);
    }

    @Test
    public void testPlayers() {
        assertNotNull(app.getBombGuy());
        assertNotNull(app.getRedEnemies());
        assertNotNull(app.getYellowEnemies());
        assertNotNull(app.getGrid());
    }
}
