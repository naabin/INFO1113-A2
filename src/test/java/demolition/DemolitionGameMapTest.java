package demolition;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import processing.core.PImage;

public class DemolitionGameMapTest {
    DemolitionGameMap gameMap;
    public DemolitionGameMapTest() {
        this.gameMap = new DemolitionGameMap();
        this.gameMap.setBrokenWall(new PImage());
        this.gameMap.setSolidWall(new PImage());
        this.gameMap.setEmptyTile(new PImage());
        this.gameMap.setGoalTile(new PImage());
    }

    @Test
    public void testMap() {

        assertNotNull(this.gameMap.getBrokenWall());
        assertNotNull(this.gameMap.getSolidWall());
        assertNotNull(this.gameMap.getEmptyTile());
        assertNotNull(this.gameMap.getGoaltile());
        assertNotNull(this.gameMap.getGoalCoord());
    }
}
