package demolition;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * This class represents the red enemy character in the demolition game. Red enemy can move along empty tiles 
 * in the game map. It's moving patter are already predetermined by the program. It follows the straigt line, however 
 * when it hits the wall, it selects the movable nearest coordinate randomly and goes there and its moving pattern goes like this 
 * during it's lifecycle in the program. It can be destroyed by bomb's explosion. When it meets with other enemy, the behaviour remains the same.
 * It can be destroyed by the bomb's explosion. When it collides with bomb guy, bomb
 * guy loses life and current level of the game is reset.
 */
public class RedEnemy extends GamePlayer {

    public RedEnemy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }
    private boolean hitleft;
    private boolean hitTop;
    public RedEnemy() {
    }

    @Override
    public void move(String[][] grid) {
        if (isCaughtInExplosion()) return;
        if (this.xCoord == null || this.yCoord == null) return;
        Integer[] leftCoord = new Integer[] {this.xCoord - 1, this.yCoord};
        Integer[] rightCoord = new Integer[] {this.xCoord + 1, this.yCoord};
        Integer[] topCoord = new Integer[] {this.xCoord, this.yCoord - 1};
        Integer[] bottomCoord = new Integer[]{this.xCoord, this.yCoord + 1};
        if (hitleft) setRandomCoord(getAdjacentCoords(grid));
        else if (hitTop) setRandomCoord(getAdjacentCoords(grid));
        else if (canMoveTo(leftCoord, grid)) {
            setNextCoord(leftCoord, Orientation.LEFT);
        }
        else if (canMoveTo(rightCoord, grid)) {
            hitleft = true;
            setNextCoord(rightCoord, Orientation.RIGHT);
        }
        else if (canMoveTo(topCoord, grid)) {
            setNextCoord(topCoord, Orientation.UP);
        }
        else if (canMoveTo(bottomCoord, grid)) {
            hitTop = true;
            setNextCoord(bottomCoord, Orientation.DOWN);
        } else setRandomCoord(getAdjacentCoords(grid));
    }
    /**
     * Randomly selects the x and y coordinate and sets it along with orientation for the next move for the red enemy
     * @param adjacentCoords is the map container temporarily stored where player can move into ceratin coordinates.
     */
    private void setRandomCoord(Map<Orientation, Integer[]> adjacentCoords) {
        Map.Entry[] coords = adjacentCoords.entrySet().toArray(new Map.Entry[adjacentCoords.size()]);
        Random random = new Random();
        int randomIndex = random.nextInt(Math.max(0, coords.length));
        Map.Entry<Orientation, Integer[]> randomCoord = coords[randomIndex];
        setOrientation(randomCoord.getKey());
        setCoord(randomCoord.getValue());
    }
    /**
     * Helper method for setting coordinate and orientaton for the next player move
     * @param coord is movable x and y coordinate
     * @param orientation is direction for the next move
     */
    private void setNextCoord(Integer[] coord, Orientation orientation) {
        setOrientation(orientation);
        setCoord(coord);
    }
    /**
     * <p>Temporarily stores the movable adjacent coordinates into the map container with direction(orientation) as key and
     * coordinate point (x, y) as value in Integer[] array. </p>
     * @param grid
     * @return
     */
    private Map<Orientation, Integer[]> getAdjacentCoords(String[][] grid) {
        Map<Orientation, Integer[]> adjacentCoords = new HashMap<>();
        Integer[] leftCoord = new Integer[] {this.xCoord - 1, this.yCoord};
        Integer[] rightCoord = new Integer[] {this.xCoord + 1, this.yCoord};
        Integer[] topCoord = new Integer[] {this.xCoord, this.yCoord - 1};
        Integer[] bottomCoord = new Integer[]{this.xCoord, this.yCoord + 1};
        if(this.canMoveTo(leftCoord, grid)) adjacentCoords.put(Orientation.LEFT, leftCoord);
        if(this.canMoveTo(rightCoord, grid)) adjacentCoords.put(Orientation.RIGHT, rightCoord);
        if(this.canMoveTo(topCoord, grid))  adjacentCoords.put(Orientation.UP, topCoord);
        if(this.canMoveTo(bottomCoord, grid)) adjacentCoords.put(Orientation.DOWN, bottomCoord);
        return adjacentCoords;
    }

    @Override
    public void setCoord(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("R")) {
                    this.setxCoord(j);
                    this.setyCoord(i);
                    setOriginalPosition(new Integer[]{j, i});
                }
            }
        }
    }

    @Override
    public void setCoord(Integer[] coords) {
        this.setxCoord(coords[0]);
        this.setyCoord(coords[1]);
    }    
}
 