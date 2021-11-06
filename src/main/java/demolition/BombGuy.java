package demolition;
/**
 * <p>This class represents the Bomb guy. Bomb guy is the controlled player in the game. 
 * It can move upon user's providing direction from the keyboard. It can move horizontally and vertically along 
 * the empty tiles in the game map. It has infinite amount of bomb and it can place anywhere in the empty tile.
 * It it get caught with explosion or collide with enemies, it loses life.
 */
public class BombGuy extends GamePlayer {


    public BombGuy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }

    public BombGuy() {
        this.setOrientation(Orientation.STRAIGHT);
    }
    
    @Override
    public void move(String[][] grid) {
        //No implementation required here.
    }

    @Override
    public void setCoord(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("P")) {
                    this.setxCoord(j);
                    this.setyCoord(i);
                    setOriginalPosition(new Integer[] {j , i});
                }
            }
        }
    }

    @Override
    public void setCoord(Integer[] coords) {
        this.setOrientation(Orientation.STRAIGHT);
        this.setxCoord(coords[0]);
        this.setyCoord(coords[1]);
    }
}
