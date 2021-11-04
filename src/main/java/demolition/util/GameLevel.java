package demolition.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class GameLevel implements Serializable {
    private String path;
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
