package demolition.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;
/**
 * <p>
 *  This class stores the path for the map and time for each level of the game map.
 *  In addition, this class reads the data from the file with given path and stores in 
 *  2D String array.
 * </p>
 */
public class GameLevel implements Serializable {
    /**
     * Stores the path attribute from config.json
     */
    private String path;
    /**
     * Stroes the time attribute from config.json
     */
    private int time;
    private String[][] grid = new String[13][15];


    public GameLevel() {

    }

    public GameLevel(String path, int time) {
        this.path = path;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    /**
     * Reads the file with given path for the file system and stores it into a 2D String array
     * @return String[][] grid
     */
    public String[][] getGridFromFile() {
        Scanner scanner;
        int row = 0;
        try {
            scanner = new Scanner(new File(this.path));
            while(scanner.hasNextLine()) {
                String[] s = scanner.nextLine().split("");
                this.grid[row++] = s;
            }
            return grid;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new String[][] {{}};
        }
    }

    public String[][] getGrids() {
        return this.grid;
    }
}
