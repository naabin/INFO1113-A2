package demolition;

public class BombGuy extends GamePlayer {

    private Integer[] originalPosition;

    public BombGuy(int xCoord, int yCoord) {
        super(xCoord, yCoord);
    }

    public BombGuy() {
        this.setOrientation(Orientation.STRAIGHT);
    }
    
    @Override
    public void move(String[][] grid) {
        
    }

    public void setOriginalPosition(Integer[] originalPosition) {
        this.originalPosition = originalPosition;
    }

    public Integer[] getOriginalPosition() {
        return originalPosition;
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
