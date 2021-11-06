package demolition;

import java.util.ArrayList;
import java.util.List;


import processing.core.PApplet;
import processing.core.PImage;
/**
 * This class reprsents the bomb for the demolition game. Bomb can be placed by 
 * the bomb guy i.e. and instance of {@link BombGuy} anywhere in the empty tile.
 * It waits for 2 seconds to detonate and create the explosion by an instance of {@link Explosion}
 */
public class Bomb {
    private int xCoord;
    private int yCoord;
    private boolean canPlaceBomb;

    private List<PImage> bombSprites = new ArrayList<>();
    private int bombSpriteIndex;

    public Bomb(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.bombSpriteIndex = 0;
        this.canPlaceBomb = false;
    }

    public Bomb() {
        this.bombSpriteIndex = 0;
    }

    public int getxCoord() {
        return xCoord;
    }
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
    /**
     * Takes x and y coordinate where player leaves the bomb i.e. player's last location
     * @param xCoord - x-coordinate of where player leaves the bomb
     * @param yCoord - y-coordinate of where player leaves the bomb
     */
    public void placeBomb(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.canPlaceBomb = true;
    }
    public boolean canPlaceBomb() {
        return canPlaceBomb;
    }

    public void setCanPlaceBomb(boolean canPlaceBomb) {
        this.canPlaceBomb = canPlaceBomb;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setBombSpriteIndex(int bombSpriteIndex) {
        this.bombSpriteIndex = bombSpriteIndex;
    }

    public int getBombSpriteIndex() {
        return bombSpriteIndex;
    }
    /**
     * This method is called in the setup method in App only once
     * @param folderName : name of the folder name under the src/main/resources direcotry
     * @param pApplet : instance of the {@link PApplet}
     */
    public void addBombSprites(String folderName, PApplet pApplet) {
        for (int i = 1; i <= 8; i++) {
            PImage image = pApplet.loadImage("src/main/resources/" +  folderName + "/" + folderName + i + ".png");
            this.bombSprites.add(image);
        }
    }
    /**
     * Draws the bomb to the window frame. Each time the method is called bombSprite index is incremented in order
     * to show the animation
     * @param pApplet
     */
    public void draw(PApplet pApplet) {
        PImage image = this.bombSprites.get(this.bombSpriteIndex % this.bombSprites.size());
        pApplet.image(image, this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
    }
}
