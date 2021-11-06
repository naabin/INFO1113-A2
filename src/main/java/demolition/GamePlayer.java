package demolition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * This is an abstract class represeting players including enemies and controlled player(Bomb Guy).
 */
public abstract class GamePlayer {

    protected Integer xCoord;

    protected Integer yCoord;

    protected Orientation orientation;

    protected boolean caughtInExplosion;

    private int imageIndex;

    private Integer[] originalPosition;

    private Map<Orientation, List<PImage>> playerSprites = new HashMap<>();

    public GamePlayer(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public GamePlayer() {
    }
    /**
     * sets the x and y coordinate of the game character according to txt file
     * @param grid is read from text file
     */
    public abstract void setCoord(String[][] grid);
    /**
     * sets the x and y coordinate of the game character 
     * @param coords x and y coordinated in a integer array
     */
    public abstract void setCoord(Integer[] coords);

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }

    public void setyCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }

    public void setCaughtInExplosion(boolean caughtInExplosion) {
        this.caughtInExplosion = caughtInExplosion;
    }

    public boolean isCaughtInExplosion() {
        return caughtInExplosion;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOriginalPosition(Integer[] originalPosition) {
        this.originalPosition = originalPosition;
    }

    public Integer[] getOriginalPosition() {
        return originalPosition;
    }
    /**
     * <p>Sets the player sprite with given orientation as key and add it into a list of sprites</p>
     * @param orientation : takes the direction({@link Orientation}) of the particular sprites and stored as key in the map data structire.
     * @param image : takes the image read from file system and puts in the map container with give key
     */
    private void setPlayerSprite(Orientation orientation, PImage image) {
        image.resize(32, 32);
        List<PImage> images = this.playerSprites.get(orientation);
        if (images == null) {
            images = new ArrayList<>();
            images.add(image);
            this.playerSprites.put(orientation, images);
        } else {
            this.playerSprites.get(orientation).add(image);
        }
    }
    /**
     * Loads player sprites from the given folder and file.
     * @param folderName : name of the folder under src/main/resources
     * @param fileName : name of the sprite to be stored in. Eg. Bomb Guy, Yellow Enemy, Red Enemy
     * @param app : an instance of {@link App} in order to get access to method for loading image from file system
     */
    public void addPlayerSprites(String folderName, String fileName, App app) {
        for (int i = 1; i <= 4; i++) {
            if (this instanceof BombGuy) {
                //Bomb guy does not have _down suffix in the resourses folder, so just take file name
                setPlayerSprite(Orientation.STRAIGHT, this.getImageFromFile(folderName, fileName + i + ".png", app));
            } else {
                setPlayerSprite(Orientation.DOWN, this.getImageFromFile(folderName, fileName + "_down" + i + ".png", app));
            }
        }
        for (int i = 1; i <= 4; i++) {
            setPlayerSprite(Orientation.LEFT, this.getImageFromFile(folderName, fileName + "_left" + i + ".png", app));
        }
        for (int i = 1; i <= 4; i++) {
            setPlayerSprite(Orientation.RIGHT, this.getImageFromFile(folderName, fileName + "_right" + i + ".png", app));
        }
        for (int i = 1; i <= 4; i++) {
            setPlayerSprite(Orientation.UP, this.getImageFromFile(folderName, fileName + "_up" + i + ".png", app));
        }
    }

    public List<PImage> getPlayerImage(Orientation direction) {
        return this.playerSprites.get(direction);
    }
    /**
     * This is an abstract method implemented by each player with distinct move behaviour.
     * @param grid presents the txt file into 2D String array 
     */
    public abstract void move(String[][] grid);
    /**
     * <p> Draws the sprite into the app window with given direction. It takes the orientation as key and gets the list of sprites 
     * as values from the map container. Then on each time draw method is called index of the sprites list gets incremented in order to 
     * show the transition in the animation process.
     * </p>
     * @param pApp is an instance of {@link App}
     * @param direction is the orientation of type {@link Orientation}
     */
    public void draw(PApplet pApp, Orientation direction) {
        List<PImage> images = this.playerSprites.get(direction);
        PImage image = images.get(this.imageIndex % images.size());
        if (!(this instanceof BombGuy) && this.isCaughtInExplosion()) return;
        if (this.xCoord == null || this.yCoord == null) return;
        pApp.image(image, this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
    }
    /**
     * 
     * @param coord neighbour x and y coordinates of the current player
     * @param grid presents the txt file into 2D String array
     * @return {@value true} if player can move arround the empty tile
     */
    protected boolean canMoveTo(Integer[] coord, String[][] grid) {
        int x = coord[0];
        int y = coord[1];
        if (x > grid.length) {
            return false;
        }
        if (y > grid[0].length) {
            return false;
        }
        if (grid[y][x].equals("W")) return false;
        if (grid[y][x].equals("B")) return false;
        return true;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public int getImageIndex() {
        return imageIndex;
    }
    private PImage getImageFromFile(String folderName, String fileName, PApplet pApplet) {
        return pApplet.loadImage("src/main/resources/" +  folderName + "/" + fileName);
    }
    /**
     * Sets the x and y coordinate to null
     */
    public void resetCoord() {
        this.xCoord = null;
        this.yCoord = null;
    }

    public Map<Orientation, List<PImage>> getPlayerSprites() {
        return playerSprites;
    }

    public void setPlayerSprites(Map<Orientation, List<PImage>> playerSprites) {
        this.playerSprites = playerSprites;
    }
}
