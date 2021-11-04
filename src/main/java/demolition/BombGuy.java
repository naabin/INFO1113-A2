package demolition;

public class BombGuy extends GamePlayer {

    public BombGuy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }

    public BombGuy() {
        this.setOrientation(Orientation.STRAIGHT);
    }
    
    @Override
    public void move(String[][] grid) {
        
    }

    @Override
    public void setCoord(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("P")) {
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
