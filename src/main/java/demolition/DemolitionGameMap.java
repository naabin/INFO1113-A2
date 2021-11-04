package demolition;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class DemolitionGameMap {
    private PImage emptyTile;
    private PImage brokenWall;
    private PImage solidWall;
    private PImage goaltile;
    private Integer[] goalCoord = new Integer[2];
    private List<RedEnemy> redEnemies;
    private List<YellowEnemy> yellowEnemies;
    public static final int TOP_OFFSET = 64;

    public DemolitionGameMap() {
        this.redEnemies = new ArrayList<>();
        this.yellowEnemies = new ArrayList<>();
    }

    public void setEmptyTile(PImage emptyTile) {
        this.emptyTile = emptyTile;
    }

    public void setBrokenWall(PImage brokenWall) {
        this.brokenWall = brokenWall;
    }

    public void setSolidWall(PImage solidWall) {
        this.solidWall = solidWall;
    }

    public void setGoalTile(PImage goalTile) {
        this.goaltile = goalTile;
    }

    public Integer[] getGoalCoord() {
        return goalCoord;
    }



    public void buildMap(String[][] grid, PApplet app) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].equals("W")) {
                    app.image(this.solidWall, j * 32, i * 32 + TOP_OFFSET);
                }
                else if (grid[i][j].equals(" ")) {
                    app.image(this.emptyTile, j * 32, i * 32 + TOP_OFFSET);
                }
                else if (grid[i][j].equals("B")) {
                    app.image(this.brokenWall, j * 32, i * 32 + TOP_OFFSET);
                }
                else if (grid[i][j].equals("G")) {
                    this.goalCoord[0] = i;
                    this.goalCoord[1] = j;
                    app.image(this.goaltile, j * 32, i * 32 + TOP_OFFSET);
                } else {
                    app.image(this.emptyTile, j * 32, i * 32 + TOP_OFFSET);
                }
            }
        }
    }

    public int getNumberOfEnemies(String[][] grid, String enemyType) {
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals(enemyType)) num++;
            }
        }
        return num;
    }

    public List<RedEnemy> getRedEnemies(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("R")) {
                    RedEnemy redEnemy = new RedEnemy(j, i);
                    redEnemy.setOrientation(Orientation.RIGHT);
                    this.redEnemies.add(redEnemy);
                }
            }
        }
        return this.redEnemies;
    }

    public List<YellowEnemy> getYellowEnemies(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("Y")) {
                    YellowEnemy yellowEnemy = new YellowEnemy(j, i);
                    yellowEnemy.setOrientation(Orientation.LEFT);
                    this.yellowEnemies.add(yellowEnemy);
                }
            }
        }
        return this.yellowEnemies;
    }
}
