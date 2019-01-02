package arkanoid;

import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import construction.LevelSetsReader;
import construction.LevelSpecificationReader;
import core.HighScoresTable;
import core.Task;
import levels.LevelInformation;
import useful.MagN;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


/**
 * The class is in charge of sets a game and run it.
 *
 * @author Afik Aharon.
 */
public class GameSetUpAndRun {
    private KeyboardSensor sensor;
    private AnimationRunner runner;
    private GameFlow game;
    private HighScoresTable highScoresTable;
    private KeyPressStoppableAnimation highScoresAnimation;
    private MenuAnimation<Task<Void>> menu;
    private LevelSpecificationReader levelSpecificationReader;
    private Reader setsReader;
    private LevelSetsReader levelSetsReader;


    /**
     * Instantiates a new Game set up and run.
     *
     * @param runner     the animation runner
     * @param game       the game flow object
     * @param sensor     the keyboard sensor
     * @param setsReader the reader for the level sets file.
     */
    public GameSetUpAndRun(AnimationRunner runner, GameFlow game, KeyboardSensor sensor, Reader setsReader) {
        this.game = game;
        this.runner = runner;
        this.sensor = sensor;
        this.highScoresTable = game.getHighScoresTable();
        this.setsReader = setsReader;
        this.levelSetsReader = new LevelSetsReader();
    }

    /**
     * The function is in charge of create a game tasks and sub menu.
     */
    public void initialize() {
        HighScoresAnimation highScoreA = new HighScoresAnimation(this.highScoresTable, MagN.PRESS_BUTTON_STRING);
        this.levelSetsReader.readSetsFile(this.setsReader);
        this.levelSpecificationReader = new LevelSpecificationReader();
        MenuAnimation<Task<Void>> subMenuAnimation = new MenuAnimation<Task<Void>>(MagN.SUB_MENU_TITLE, this.sensor);
        this.highScoresAnimation = new KeyPressStoppableAnimation(this.sensor, MagN.PRESS_BUTTON_STRING, highScoreA);
        this.menu = new MenuAnimation<Task<Void>>(MagN.GAME_NAME, this.runner.getGui().getKeyboardSensor());
        InputStream is = null;
        // try to load a image Background for the high score table,menu and the submenu.
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.HIGH_SCORES_IMAGE_PATH);
            Image image = ImageIO.read(is);
            highScoreA.setBackground(new DrawImage(image));
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.MENU_IMAGE_PATH);
            image = ImageIO.read(is);
            this.menu.setBackground(new DrawImage(image));
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.SUB_MENU_IMAGE_PATH);
            image = ImageIO.read(is);
            subMenuAnimation.setBackground(new DrawImage(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
        // add thr sub menu to the main menu.
        this.menu.addSubMenu("s", MagN.SUB_MENU_TITLE, subMenuAnimation);
        // get a list of symbols that are keys for the pressing options.
        List<String> symbols = this.levelSetsReader.getSymbols();
        for (String symbol : symbols) {
            subMenuAnimation.addSelection(symbol, this.levelSetsReader.getSetsName().get(symbol), new Task<Void>() {
                @Override
                public Void run() {
                    // get the path level sets file
                    runTheGame(levelSetsReader.getLevelPath().get(symbol));
                    return null;
                }
            });
        }
        // task for the menu game
        Task<Void> runMenuGame = new Task<Void>() {
            @Override
            public Void run() {
                runner.run(menu.getSubMenu("s"));
                Task<Void> task = menu.getSubMenu("s").getStatus();
                task.run();
                return null;
            }
        };
        // task for the high score table animation
        Task<Void> runHighScores = new Task<Void>() {
            @Override
            public Void run() {
                runTheHighScoresTable();
                return null;
            }
        };
        // task for the quit option
        Task<Void> quitGame = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };
        this.menu.addSelection("s", "Start Game", runMenuGame);
        this.menu.addSelection("h", "High Scores", runHighScores);
        this.menu.addSelection("q", "Quit", quitGame);
    }

    /**
     * The function run the game menu.
     */
    public void runGameMenu() {
        while (true) {
            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    /**
     * The function create a list of levels by call the
     * object member levelSpecificationReader and run the game.
     *
     * @param filePath the file path levels
     */
    public void runTheGame(String filePath) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
        Reader fileReader = new InputStreamReader(is);
        List<LevelInformation> levels = this.levelSpecificationReader.fromReader(fileReader);
        this.game.runLevels(levels);
        runTheHighScoresTable();
    }

    /**
     * Run the high scores table.
     */
    public void runTheHighScoresTable() {
        this.runner.run(this.highScoresAnimation);
        this.highScoresAnimation.setStop();
    }
}
