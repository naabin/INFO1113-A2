package demolition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class GamePlayer {

    protected Integer xCoord;

    protected Integer yCoord;

    protected Orientation orientation;

    protected boolean caughtInExplosion;

    private int imageIndex;

    private Map<Orientation, List<PImage>> playerSprites = new HashMap<>();

    public GamePlayer(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public GamePlayer() {
    }

    public abstract void setCoord(String[][] grid);
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

    public void setPlayerSprite(Orientation direction, PImage image) {
        image.resize(32, 32);
        List<PImage> images = this.playerSprites.get(direction);
        if (images == null) {
            images = new ArrayList<>();
            images.add(image);
            this.playerSprites.put(direction, images);
        } else {
            this.playerSprites.get(direction).add(image);
        }
    }

    public void addPlayerSprites(String folderName, String fileName, App app) {
        for (int i = 1; i <= 4; i++) {
            if (this instanceof BombGuy) {
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

    public abstract void move(String[][] grid);

    public void draw(PApplet pApp, Orientation direction) {
        List<PImage> images = this.playerSprites.get(direction);
        PImage image = images.get(this.imageIndex % images.size());
        if (!(this instanceof BombGuy) && this.isCaughtInExplosion()) return;
        if (this.xCoord == null || this.yCoord == null) return;
        pApp.image(image, this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
    }

    public boolean canMoveTo(int xCoord, int yCoord, String[][] grid) {
        if (xCoord > grid.length) {
            return false;
        }
        if (yCoord > grid[0].length) {
            return false;
        }
        if (grid[yCoord][xCoord].equals("W")) return false;
        if (grid[yCoord][xCoord].equals("B")) return false;
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
