package demolition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import demolition.util.GameConfig;
import demolition.util.GameLevel;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
/**
 * This class is the main class for the game. Gradle load this class when the program begins.
 */
public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;
    private int level = 0;
    private int playerMoveInterval = 0;
    private int playerAnimationInterval = 0;
    private int bombAnimationInterval = 0;
    private int explosionInterval = 0;
    private int bombDetonationTime = 0;

    private final GameConfig gameConfig;
    private final DemolitionGameMap gameMap;

    private GameLevel currentGameLevel;
    private String[][] grid;

    //Icon for the levels and clock
    private PImage clockIcon;
    private PImage playerLevelIcon;

    //Store enemy images for later levels
    Map<Orientation, List<PImage>> redEnemySprites;
    Map<Orientation, List<PImage>> yellowEnemySprites;

    //Game characters
    private BombGuy bombGuy;
    private List<RedEnemy> redEnemies = new ArrayList<>();
    private List<YellowEnemy> yellowEnemies = new ArrayList<>();

    private Bomb bomb;

    private Explosion explosion;

    public App() {
        this.gameConfig = new GameConfig();
        this.gameMap = new DemolitionGameMap();
        this.bombGuy = new BombGuy();
        this.bomb = new Bomb();
        this.explosion = new Explosion();
        loadConfig(gameConfig);
        this.currentGameLevel= this.gameConfig.getLevels().get(this.level);
        this.grid = this.currentGameLevel.getGridFromFile();
        this.bombGuy.setCoord(grid);
        this.redEnemies = this.gameMap.getRedEnemies(this.grid);
        this.yellowEnemies = this.gameMap.getYellowEnemies(this.grid);
    }

    public void settings() {
        this.size(WIDTH, HEIGHT);
    }

    public void setup() {
        // Load images during setup
        this.frameRate(FPS);
        this.gameMap.setBrokenWall(this.loadImage("src/main/resources/broken/broken.png"));
        this.gameMap.setEmptyTile(this.loadImage("src/main/resources/empty/empty.png"));
        this.gameMap.setGoalTile(this.loadImage("src/main/resources/goal/goal.png"));
        this.gameMap.setSolidWall(this.loadImage("src/main/resources/wall/solid.png"));
        PFont font = this.createFont("src/main/resources/PressStart2P-Regular.ttf", 24, false);
        this.textFont(font);
        //Load images of bomb guy
        this.bombGuy.addPlayerSprites("player", "player", this);
        //Load images for the red enemy
        this.redEnemies.forEach(redEnemy -> {
            redEnemy.addPlayerSprites("red_enemy", "red", this);
            if (this.redEnemySprites == null) {
                this.redEnemySprites = redEnemy.getPlayerSprites();
            }
        });
        //Load images for the yellow enemy
        this.yellowEnemies.forEach(yellowEnemy -> {
            yellowEnemy.addPlayerSprites("yellow_enemy", "yellow", this);
            if (this.yellowEnemySprites == null) {
                this.yellowEnemySprites = yellowEnemy.getPlayerSprites();
            }
        });
        //Load the bomb sprites
        this.bomb.addBombSprites("bomb", this);
        //Load explosion sprites
        this.explosion.addExplosionSprites(this);
        // Initialize players
        loadICons();
    }

    public void draw() {
        this.background(239, 129, 0);
        if (this.currentGameLevel.getTime() <= 0 || this.gameConfig.getLives() <= 0) {
            this.text("GAME OVER", 120, 240);
            return;
        }
        if (this.gameConfig.getLevels().size() - 1 == this.level && this.reachedGoal()) {
            this.text("YOU WIN", 120, 240);
            return;
        }
        //Draw icons and text for the time and remaining lives
        this.image(this.clockIcon, 260, 20);
        this.image(this.playerLevelIcon, 140, 20);
        this.text(this.gameConfig.getLives(), 180, 50);
        int time = this.currentGameLevel.getTime();
        this.text(this.currentGameLevel.getTime(), 290, 50);
        // Draw map
        this.gameMap.buildMap(grid, this);
        if (bombGuy.isCaughtInExplosion()) {
            this.gameConfig.setLives(this.gameConfig.getLives() - 1);
            this.bombGuy.setCoord(this.bombGuy.getOriginalPosition());
            this.bombGuy.setCaughtInExplosion(false);
        }
        //Possible collison with enemies
        if (this.isEnemyInTheSameSpace(this.redEnemies) || this.isEnemyInTheSameSpace(this.yellowEnemies)) {
            this.gameConfig.setLives(this.gameConfig.getLives() - 1);
            this.bombGuy.setCoord(this.bombGuy.getOriginalPosition());
            this.redEnemies.forEach(rE -> rE.setCoord(rE.getOriginalPosition()));
            this.yellowEnemies.forEach(yE -> yE.setCoord(yE.getOriginalPosition()));
        }
        //Draw players
        this.bombGuy.draw(this, this.bombGuy.getOrientation());
        //Filtering the red enemies caught in explosion
        this.redEnemies = this.redEnemies.stream().filter(rE -> !rE.isCaughtInExplosion()).collect(Collectors.toList());
        for (RedEnemy redEnemy: this.redEnemies) {
            redEnemy.draw(this, redEnemy.getOrientation());
        }
        //Filtering the yellow enemies caught in explosion
        this.yellowEnemies = this.yellowEnemies.stream().filter(yE -> !yE.isCaughtInExplosion()).collect(Collectors.toList());
        this.yellowEnemies.forEach((yellowEnemy) -> {
            yellowEnemy.draw(this, yellowEnemy.getOrientation());
        });
        // game character animation transition
        if (this.millis() > playerAnimationInterval + 200) {
            this.bombGuy.setImageIndex(this.bombGuy.getImageIndex() + 1);
            this.redEnemies.forEach((redEnemy) -> redEnemy.setImageIndex(redEnemy.getImageIndex() + 1));
            this.yellowEnemies.forEach((yellowEnemy) -> yellowEnemy.setImageIndex(yellowEnemy.getImageIndex() + 1));
            playerAnimationInterval = this.millis();
        }
        // if the goal is reached reset characters and move to the next level
        if (this.reachedGoal() && this.level < this.gameConfig.getLevels().size() - 1) {
            this.level += 1;
            this.bombGuy.resetCoord();
            this.redEnemies.forEach(e -> e.resetCoord());
            this.yellowEnemies.forEach(e -> e.resetCoord());
            this.currentGameLevel = this.gameConfig.getLevels().get(this.level);
            this.grid = this.currentGameLevel.getGridFromFile();
            this.bombGuy.setCoord(grid);
            this.bombGuy.setOrientation(Orientation.STRAIGHT);
            this.redEnemies = this.gameMap.getRedEnemies(this.grid);
            //set the enemies sprites as the game moves for the next level
            this.redEnemies.forEach((redEnemy) -> {
                if (redEnemy.getPlayerSprites().size() <= 0) {
                    redEnemy.setPlayerSprites(this.redEnemySprites);
                }
            });
            this.yellowEnemies = this.gameMap.getYellowEnemies(this.grid);
            this.yellowEnemies.forEach((yellowEnemy) -> {
                if (yellowEnemy.getPlayerSprites().size() <= 0) {
                    yellowEnemy.setPlayerSprites(this.yellowEnemySprites);
                }
            });
        }
        //Update enemies location
        if (this.millis() > playerMoveInterval + 1000) {
            this.yellowEnemies.forEach(yellowEnemy -> yellowEnemy.move(grid));
            this.redEnemies.forEach(redEnemy -> redEnemy.move(grid));
            this.currentGameLevel.setTime(--time);
            playerMoveInterval = this.millis();
        }
        // Bomb explosion
        if (this.bomb.canPlaceBomb()) {
            this.bomb.draw(this);
            if (this.millis() > this.bombDetonationTime + 2000) {
                this.bomb.setCanPlaceBomb(false);
                this.explosion.setxCoord(this.bomb.getxCoord());
                this.explosion.setyCoord(this.bomb.getyCoord());
                this.explosion.setCanExplode(true);
                this.bombDetonationTime = this.millis();
            }
            if (this.millis() > this.bombAnimationInterval + 250) {
                this.bomb.setBombSpriteIndex(this.bomb.getBombSpriteIndex() + 1);
                this.bombAnimationInterval = this.millis();
            }
        }
        // explode the bomb
        if (this.explosion.canExplode()) {
            this.explosion.explode(this);
        }
        // remain the explosion for 2 seconds
        if (this.millis() > this.explosionInterval + 2000) {
            this.explosion.setCanExplode(false);
            this.explosionInterval = millis();
        }
    }
    /**
     * reads the config.json from the file system and loads in the program
     * @param config is an instance of {@link GameConfig} where it stores the data coming 
     * from config.json
     */
    public void loadConfig(GameConfig config) {
        JSONObject jsonObject = PApplet.loadJSONObject(new File("config.json"));
        int lives  = (int)jsonObject.get("lives");
        JSONArray jsonArray =  jsonObject.getJSONArray("levels");
        for (int i = 0; i < jsonArray.size(); i++) {
            String path = (String)jsonArray.getJSONObject(i).get("path");
            int time = (Integer)jsonArray.getJSONObject(i).get("time");
            GameLevel gameLevel = new GameLevel(path, time);
            config.addLevel(gameLevel);
        }
        config.setLives(lives);
    }
    private void loadICons() {
        this.clockIcon = this.loadImage("src/main/resources/icons/clock.png");
        this.playerLevelIcon = this.loadImage("src/main/resources/icons/player.png");
    }

    @Override
    public void keyPressed() {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40
        int xCoord = this.bombGuy.getxCoord();
        int yCoord = this.bombGuy.getyCoord();
        Integer[] leftCoord = new Integer[] {xCoord - 1, yCoord};
        Integer[] rightCoord = new Integer[] {xCoord + 1, yCoord};
        Integer[] topCoord = new Integer[] {xCoord, yCoord - 1};
        Integer[] bottomCoord = new Integer[]{xCoord, yCoord + 1};
        if (this.keyCode == 37) {
            if (this.bombGuy.canMoveTo(leftCoord, grid)) {
                this.bombGuy.setCoord(leftCoord);
                this.bombGuy.setOrientation(Orientation.LEFT);
            }
        }
        if (this.keyCode == 39) {
            if (this.bombGuy.canMoveTo(rightCoord, grid)) {
                this.bombGuy.setCoord(rightCoord);
                this.bombGuy.setOrientation(Orientation.RIGHT);
            }
        }
        if (this.keyCode == 38) {
            if (this.bombGuy.canMoveTo(topCoord, grid)) {
                this.bombGuy.setCoord(topCoord);
                this.bombGuy.setOrientation(Orientation.UP);
            }
        }
        if (this.keyCode == 40) {
            if (this.bombGuy.canMoveTo(bottomCoord, grid)) {
                this.bombGuy.setCoord(bottomCoord);
                this.bombGuy.setOrientation(Orientation.STRAIGHT);
            }
        }
        if (this.keyCode == 32) {
            this.bomb.placeBomb(this.bombGuy.getxCoord(), this.bombGuy.getyCoord());
            this.bombDetonationTime = this.millis();
        }
    }

    private boolean reachedGoal() {
        return this.bombGuy.getxCoord() == this.gameMap.getGoalCoord()[1] && this.bombGuy.getyCoord() == this.gameMap.getGoalCoord()[0];
    }

    public void mutateGrid(Integer[] coord, String destruction) {
        int x = coord[0], y = coord[1];
        if (x > this.grid.length || y > this.grid[0].length) return;
        this.grid[y][x] = destruction;
    }

    public String[][] getGrid() {
        return grid;
    }

    public List<RedEnemy> getRedEnemies() {
        return redEnemies;
    }

    public List<YellowEnemy> getYellowEnemies() {
        return yellowEnemies;
    }

    public BombGuy getBombGuy() {
        return bombGuy;
    }

    private boolean isEnemyInTheSameSpace(List<? extends GamePlayer> enemies) {
        for(GamePlayer enemy: enemies) {
            int rX = enemy.getxCoord();
            int rY = enemy.getyCoord();
            int bX = this.bombGuy.getxCoord();
            int bY = this.bombGuy.getyCoord();
            if (rX == bX && rY == bY) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
