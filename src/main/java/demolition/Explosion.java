package demolition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;
/**
 * This is an enum for explosion direction.
 * <ul>
 *  <li>CENTRE</li>
 *  <li>END_BOTTOM</li>
 *  <li>END_LEFT</li>
 *  <li>END_RIGHT</li>
 *  <li>END_TOP</li>
 *  <li>HORIZONTAL</li>
 *  <li>VERTICAL</li>
 * </ul>
 */
enum ExplosionSpriteOrientation {
    CENTRE,
    END_BOTTOM,
    END_LEFT,
    END_RIGHT,
    END_TOP,
    HORIZONTAL,
    VERTICAL
}

/**
 * This class represents the explosion event in the demolition game.Explosion lasts for 2 seconds and 
 * spans up to 2 spaces further from where bomb was located both vertially and horizontally. However, 
 * Explosion does not destroy solid wall or empty tile. It can destroy enemy, player and broken walls.
 */
public class Explosion {
    /**
     * x-coordinate where it should begin explode. Takes from bomb x coordinate
     */
    private int xCoord;
    /**
     * y-coordinate where it should begin explode. Takes from bomb y coordinate
     */
    private int yCoord;
    private boolean canExplode;
    private Map<ExplosionSpriteOrientation, PImage> explosionSprites = new HashMap<>();

    public Explosion(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Explosion() {
        
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setCanExplode(boolean canExplode) {
        this.canExplode = canExplode;
    }
    
    public boolean canExplode() {
        return this.canExplode;
    }
    /**
     * <p>Loads all the explosion sprites from the file system and 
     * stores it in a map container using direction as key and sprite as value.</p>
     * @param pApplet is the instance of {@link App} in order to get access to loadImage method from {@link PApplet}
     */
    public void addExplosionSprites(PApplet pApplet) {
        PImage centerSprite = getSpriteFromFile("centre", pApplet);
        PImage endBottomSprite = getSpriteFromFile("end_bottom", pApplet);
        PImage endLeftSprite = getSpriteFromFile("end_left", pApplet);
        PImage endRightSprite = getSpriteFromFile("end_right", pApplet);
        PImage endTopSprite = getSpriteFromFile("end_top", pApplet);
        PImage horizontalSprite = getSpriteFromFile("horizontal", pApplet);
        PImage verticalSprite = getSpriteFromFile("vertical", pApplet);

        this.explosionSprites.put(ExplosionSpriteOrientation.CENTRE, centerSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.END_BOTTOM, endBottomSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.END_LEFT, endLeftSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.END_RIGHT, endRightSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.END_TOP, endTopSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.HORIZONTAL, horizontalSprite);
        this.explosionSprites.put(ExplosionSpriteOrientation.VERTICAL, verticalSprite);
    }
    /**
     * Helper method for loading image from file system
     * @param fileName is the name of the explosion sprite png file
     * @param pApplet is the instance of {@link App} in order to get access to loadImage method from {@link PApplet}
     * @return sprite as an instance of {@link PImage}
     */
    private PImage getSpriteFromFile(String fileName, PApplet pApplet) {
        return pApplet.loadImage("src/main/resources/explosion/" + fileName + ".png");
    }
    /**
     * <p>Begins explosion. Explosion can expand upto 4 cardianal spaces i.e 2 vertically if there is no wall 
     * and 2 horizontally same as vertical expansion.
     * </p>
     * @param app is an instance of {@link App} in order to render the explosion
     */
    public void explode(App app) {
        Integer[] currentBombLoction = new Integer[] {this.getxCoord(), this.getyCoord()};
        Integer[] leftCoord = new Integer[] {this.xCoord - 1, this.yCoord};
        Integer[] farLeftCoord = new Integer[] {this.xCoord - 2, this.yCoord};
        Integer[] rightCoord = new Integer[] {this.xCoord + 1, this.yCoord};
        Integer[] farRightCoord = new Integer[] {this.xCoord + 2, this.yCoord};
        Integer[] topCoord = new Integer[] {this.xCoord, this.yCoord - 1};
        Integer[] farTopCoord = new Integer[] {this.xCoord, this.yCoord - 2};
        Integer[] bottomCoord = new Integer[]{this.xCoord, this.yCoord + 1};
        Integer[] farBottomCoord = new Integer[]{this.xCoord, this.yCoord + 2};
        String[][] grid = app.getGrid();
        if (canExplodeInCoord(currentBombLoction, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(currentBombLoction, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.HORIZONTAL), currentBombLoction[0] * 32, currentBombLoction[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(currentBombLoction, " ");
            }
        }
        if (canExplodeInCoord(leftCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(leftCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.HORIZONTAL), leftCoord[0] * 32, leftCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(leftCoord, " ");
            }
        }
        if (canExplodeInCoord(farLeftCoord, grid) && canExplodeInCoord(leftCoord, grid) ) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(farLeftCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.END_LEFT), farLeftCoord[0] * 32, farLeftCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(farLeftCoord, " ");
            }
        }
        if (canExplodeInCoord(rightCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(rightCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.HORIZONTAL), rightCoord[0] * 32, rightCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(rightCoord, " ");
            }   
        }
        if (canExplodeInCoord(farRightCoord, grid) && canExplodeInCoord(rightCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(farRightCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.END_RIGHT), farRightCoord[0] * 32, farRightCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(farRightCoord, " ");
            }
        }
        if (canExplodeInCoord(topCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(topCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.VERTICAL), topCoord[0] * 32, topCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(topCoord, " ");
            }
        }
        if (canExplodeInCoord(farTopCoord, grid) && canExplodeInCoord(topCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(farTopCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.END_TOP), farTopCoord[0] * 32, farTopCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(farTopCoord, " ");
            }
        }
        if (canExplodeInCoord(bottomCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(bottomCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.VERTICAL), bottomCoord[0] * 32, bottomCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(bottomCoord, " ");
            }
        }
        if (canExplodeInCoord(farBottomCoord, grid) && canExplodeInCoord(bottomCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(farBottomCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.END_BOTTOM), farBottomCoord[0] * 32, farBottomCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(farBottomCoord, " ");
            }
        }
    }
    /**
     * <p>Helper method to determine if the bomb can explode in the given coord.</p>
     * @param coord is the neighbour coordinate upto 2 spaces further apart from the bomb location
     * @param grid presents the 2D String array from the txt file
     * @return true if it can explode in given coord 
     */
    private boolean canExplodeInCoord(Integer[] coord, String[][] grid) {
        int x = coord[0], y = coord[1];
        if (y > grid.length - 1 || y <= 0) return false;
        if (x > grid[0].length - 1 || x <= 0) return false;
        if (grid[y][x].equals("W")) return false;
        return true;
    }
    /**
     * Helper method for to determine if player, enemy or broken wall exists within the radius of bomb explosion.
     * @param coord is the neighbour cooridnate from the bomb location
     * @param grid is the 2D String array representing the game map
     * @param app is an instance of {@link App}
     * @return true if broken wall or player or enemy are detected in the given coord.
     */
    private boolean detetectPlayersAndBrokenWalls(Integer[] coord, String[][] grid, App app) {
        int x = coord[0], y = coord[1];
        List<RedEnemy> redEnemies = app.getRedEnemies();
        for (RedEnemy redEnemy: redEnemies) {
            int redX = redEnemy.getxCoord();
            int redY = redEnemy.getyCoord();
            if (redX == x && redY == y) {
                redEnemy.setCaughtInExplosion(true);
                return true;
            }
        }
        List<YellowEnemy> yellowEnemies = app.getYellowEnemies();
        for (YellowEnemy yellowEnemy: yellowEnemies) {
            int yellowX = yellowEnemy.getxCoord();
            int yellowY = yellowEnemy.getyCoord();
            if (yellowX == x && yellowY == y) {
                yellowEnemy.setCaughtInExplosion(true);
                return true;
            }
        }
        if (x == app.getBombGuy().getxCoord() && y == app.getBombGuy().getyCoord()) { 
            app.getBombGuy().setCaughtInExplosion(true);
            return true;
        }
        if (grid[y][x].equals("B")) return true;
        if (grid[y][x].equals(" ")) return true;
        return false;
    }
}
