package demolition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class BombTest {
    Bomb bomb;

    public BombTest() {
        this.bomb = new Bomb(1, 2);
    }

    @Test
    public void testBomb() {
        this.bomb.setxCoord(2);
        this.bomb.setyCoord(3);
        this.bomb.setCanPlaceBomb(true);
        this.bomb.setBombSpriteIndex(1);
        this.bomb.placeBomb(2, 3);
        assertEquals(2, this.bomb.getxCoord());
        assertEquals(3, this.bomb.getyCoord());
        assertEquals(true, this.bomb.canPlaceBomb());
        assertNotEquals(2, this.bomb.getBombSpriteIndex());

    }

}
