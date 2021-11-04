package demolition;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RedEnemy extends GamePlayer {

    public RedEnemy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }

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
        Map<Orientation, Integer[]> adjacentCoords = new HashMap<>();
        if (canMoveTo(leftCoord[0], leftCoord[1], grid)) {
            adjacentCoords.put(Orientation.LEFT, leftCoord);
        }
        if (canMoveTo(rightCoord[0], rightCoord[1], grid)) {
            adjacentCoords.put(Orientation.RIGHT, rightCoord);
        }
        if (canMoveTo(topCoord[0], topCoord[1], grid)) {
            adjacentCoords.put(Orientation.UP, topCoord);
        }
        if (canMoveTo(bottomCoord[0], bottomCoord[1], grid)) {
            adjacentCoords.put(Orientation.DOWN, bottomCoord);
        }
        Map.Entry[] coords = adjacentCoords.entrySet().toArray(new Map.Entry[adjacentCoords.size()]);
        Random random = new Random();
        int randomIndex = random.nextInt(coords.length);
        Map.Entry<Orientation, Integer[]> randomCoord = coords[randomIndex];
        setOrientation(randomCoord.getKey());
        setCoord(randomCoord.getValue());
    }

    @Override
    public void setCoord(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("R")) {
                    this.setxCoord(j);
                    this.setyCoord(i);
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
