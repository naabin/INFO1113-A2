package demolition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import demolition.util.GameConfig;
import demolition.util.GameLevel;
import processing.core.PImage;

public class ExplosionTest {
    Explosion explosion;
    GameConfig config;
    App app;
    GameLevel currentGameLevel;
    String grid[][];
    BombGuy bombGuy;
    RedEnemy redEnemy;
    YellowEnemy yellowEnemy;
    public ExplosionTest() {
        this.explosion = new Explosion(2, 1);
        this.app = new App();
        this.config = new GameConfig();
        this.app.loadConfig(config);
        //Load images for the red enemy
        this.currentGameLevel = this.config.getLevels().get(0);
        this.grid = this.currentGameLevel.getGridFromFile();
        this.bombGuy = new BombGuy();
        this.bombGuy.setCoord(grid);
        this.yellowEnemy = new YellowEnemy();
        this.redEnemy = new RedEnemy();
        this.yellowEnemy.setCoord(grid);
        this.redEnemy.setCoord(grid);

        Map<ExplosionSpriteOrientation, PImage> expSprites = new HashMap<>();
        expSprites.put(ExplosionSpriteOrientation.CENTRE, new PImage());
        expSprites.put(ExplosionSpriteOrientation.END_BOTTOM, new PImage());
        expSprites.put(ExplosionSpriteOrientation.END_LEFT, new PImage());
        expSprites.put(ExplosionSpriteOrientation.END_RIGHT, new PImage());
        expSprites.put(ExplosionSpriteOrientation.END_TOP, new PImage());
        expSprites.put(ExplosionSpriteOrientation.HORIZONTAL, new PImage());
        expSprites.put(ExplosionSpriteOrientation.VERTICAL, new PImage());
        explosion.setExplosionSprites(expSprites);
    }
    @Test
    public void testCanExplode() {
        int x = explosion.getxCoord();
        int y = explosion.getyCoord();
        assertEquals(2, x);
        assertEquals(1, y);
        Integer[] leftCoord = new Integer[] {x-1, y};
        Integer[] topCoord = new Integer[] {x, y -1};
        Integer[] rightCoord = new Integer[] {x + 1, y};
        Integer[] bottomCoord = new Integer[] {x - 1, y - 1};
        assertEquals(true, explosion.canExplodeInCoord(leftCoord, grid));
        assertEquals(false, explosion.canExplodeInCoord(topCoord, grid));
        assertEquals(false, explosion.canExplodeInCoord(bottomCoord, grid));
        assertEquals(true, explosion.canExplodeInCoord(rightCoord, grid));
        assertEquals(true, explosion.detetectPlayersAndBrokenWalls(this.bombGuy.getOriginalPosition(), grid, app));
        assertEquals(true, explosion.detetectPlayersAndBrokenWalls(this.redEnemy.getOriginalPosition(), grid, app));
        assertEquals(true, explosion.detetectPlayersAndBrokenWalls(this.yellowEnemy.getOriginalPosition(), grid, app));
        assertNotNull(explosion.getExplosionSprites());
    }
}
