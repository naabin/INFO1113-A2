package demolition;
/**
 * This class represents the yellow enemy character in the demolition game. It can move along the empty tile in the map 
 * and it cannot move along Solid wall or broken wall in the game map. It's moving pattern is pre-determined by the program and 
 * it tries to move clockwise in the map. For example, If its going down and hits the wall then it tries to move left or it was moving left and 
 * hits the wall then it wil try to move up and so on. It can be destroyed by the bomb's explosion. When it collides with bomb guy, bomb
 * guy loses life and current level of the game is reset.
 */
public class YellowEnemy extends GamePlayer {

    public YellowEnemy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }

    public YellowEnemy() {
        
    }
    
    @Override
    public void move(String[][] grid) {
        if (isCaughtInExplosion()) return;
        if (this.xCoord == null || this.yCoord == null) return;
        Integer[] leftCoord = new Integer[] {this.xCoord - 1, this.yCoord};
        Integer[] rightCoord = new Integer[] {this.xCoord + 1, this.yCoord};
        Integer[] topCoord = new Integer[] {this.xCoord, this.yCoord - 1};
        Integer[] bottomCoord = new Integer[]{this.xCoord, this.yCoord + 1};
        // If the current direction is down
        if (orientation.equals(Orientation.DOWN)) {
            if (canMoveTo(bottomCoord, grid)) {
                setNextCoord(bottomCoord, Orientation.DOWN);
            } else if (canMoveTo(leftCoord, grid)) {
                setNextCoord(leftCoord, Orientation.LEFT);
            } else if (canMoveTo(topCoord, grid)) {
                setNextCoord(topCoord, Orientation.UP);
            } else if (canMoveTo(rightCoord, grid)) {
                setNextCoord(rightCoord, Orientation.RIGHT);
            }
        }
        // if the current direction is left
        else if (orientation.equals(Orientation.LEFT)) {
            if (canMoveTo(leftCoord, grid)) {
                setNextCoord(leftCoord, Orientation.LEFT);
            } else if (canMoveTo(topCoord, grid)) {
                setNextCoord(topCoord, Orientation.UP);
            } else if (canMoveTo(rightCoord, grid)) {
                setNextCoord(rightCoord, Orientation.RIGHT);
            } else if (canMoveTo(bottomCoord, grid)) {
                setNextCoord(bottomCoord, Orientation.DOWN);
            }
        // if the current direction is up
        } else if (orientation.equals(Orientation.UP)) {
            if (canMoveTo(topCoord, grid)) {
                setNextCoord(topCoord, Orientation.UP);
            } else if (canMoveTo(rightCoord, grid)) {
                setNextCoord(rightCoord, Orientation.RIGHT);
            } else if (canMoveTo(bottomCoord, grid)) {
                setNextCoord(bottomCoord, Orientation.DOWN);
            } else if (canMoveTo(leftCoord, grid)) {
                setNextCoord(leftCoord, Orientation.LEFT);
            }
        // if the current direction is right
        } else if (orientation.equals(Orientation.RIGHT)) {
            if (canMoveTo(rightCoord, grid)) {
                setNextCoord(rightCoord, Orientation.RIGHT);
            } else if (canMoveTo(bottomCoord, grid)) {
                setNextCoord(bottomCoord, Orientation.DOWN);
            } else if (canMoveTo(leftCoord, grid)) {
                setNextCoord(leftCoord, Orientation.LEFT);
            } else if (canMoveTo(topCoord, grid)) {
                setNextCoord(topCoord, Orientation.UP);
            }
        }
    }

    private void setNextCoord(Integer[] coord, Orientation orientation) {
        setOrientation(orientation);
        setCoord(coord);
    }

    @Override
    public void setCoord(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("Y")) {
                    this.setxCoord(j);
                    this.setyCoord(i);
                    setOriginalPosition(new Integer[]{i, j});
                    setOrientation(Orientation.LEFT);
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
