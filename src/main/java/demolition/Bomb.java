package demolition;

import java.util.ArrayList;
import java.util.List;


import processing.core.PApplet;
import processing.core.PImage;

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

    public void addBombSprites(String folderName, PApplet pApplet) {
        for (int i = 1; i <= 8; i++) {
            PImage image = pApplet.loadImage("src/main/resources/" +  folderName + "/" + folderName + i + ".png");
            this.bombSprites.add(image);
        }
    }

    public void draw(PApplet pApplet) {
        PImage image = this.bombSprites.get(this.bombSpriteIndex % this.bombSprites.size());
        pApplet.image(image, this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
    }
}
