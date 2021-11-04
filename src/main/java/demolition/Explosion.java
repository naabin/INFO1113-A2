package demolition;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

enum ExplosionSpriteOrientation {
    CENTRE,
    END_BOTTOM,
    END_LEFT,
    END_RIGHT,
    END_TOP,
    HORIZONTAL,
    VERTICAL
}


public class Explosion {
    private int xCoord;
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

    private PImage getSpriteFromFile(String fileName, PApplet pApplet) {
        return pApplet.loadImage("src/main/resources/explosion/" + fileName + ".png");
    }

    public void explode(App app) {
        Integer[] leftCoord = new Integer[] {this.xCoord - 1, this.yCoord};
        Integer[] farLeftCoord = new Integer[] {this.xCoord - 2, this.yCoord};
        Integer[] rightCoord = new Integer[] {this.xCoord + 1, this.yCoord};
        Integer[] farRightCoord = new Integer[] {this.xCoord + 2, this.yCoord};
        Integer[] topCoord = new Integer[] {this.xCoord, this.yCoord - 1};
        Integer[] farTopCoord = new Integer[] {this.xCoord, this.yCoord - 2};
        Integer[] bottomCoord = new Integer[]{this.xCoord, this.yCoord + 1};
        Integer[] farBottomCoord = new Integer[]{this.xCoord, this.yCoord + 2};
        String[][] grid = app.getGrid();
        if (canExplodeInCoord(leftCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(leftCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.HORIZONTAL), leftCoord[0] * 32, leftCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(leftCoord, " ");
            }
        }
        if (canExplodeInCoord(farLeftCoord, grid)) {
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
        if (canExplodeInCoord(farRightCoord, grid)) {
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
        if (canExplodeInCoord(farTopCoord, grid)) {
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
        if (canExplodeInCoord(farBottomCoord, grid)) {
            app.image(this.explosionSprites.get(ExplosionSpriteOrientation.CENTRE), this.xCoord * 32, this.yCoord * 32 + DemolitionGameMap.TOP_OFFSET);
            if (detetectPlayersAndBrokenWalls(farBottomCoord, grid, app)) {
                app.image(this.explosionSprites.get(ExplosionSpriteOrientation.END_BOTTOM), farBottomCoord[0] * 32, farBottomCoord[1] * 32 + DemolitionGameMap.TOP_OFFSET);
                app.mutateGrid(farBottomCoord, " ");
            }
        }
    }

    private boolean canExplodeInCoord(Integer[] coords, String[][] grid) {
        int x = coords[0], y = coords[1];
        if (y > grid.length) return false;
        if (x > grid[0].length) return false;
        if (grid[x][y].equals("W")) return false;
        return true;
    }

    private boolean detetectPlayersAndBrokenWalls(Integer[] coords, String[][] grid, App app) {
        int x = coords[0], y = coords[1];
        if (grid[x][y].equals("R")) {
            app.getRedEnemy().setCaughtInExplosion(true);
            return true;
        } 
        if (grid[x][y].equals("Y")) {
            app.getYellowEnemy().setCaughtInExplosion(true);
            return true;
        }
        if (grid[x][y].equals("P")) {
            app.getBombGuy().setCaughtInExplosion(true);
            return true;
        }
        if (grid[x][y].equals("B")) return true;
        if (grid[x][y].equals(" ")) return true;
        return false;
    }
}
