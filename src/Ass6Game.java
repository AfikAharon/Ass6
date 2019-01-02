
import animation.AnimationRunner;
import arkanoid.GameFlow;
import arkanoid.GameSetUpAndRun;
import biuoop.GUI;
import biuoop.Sleeper;

import core.HighScoresTable;
import useful.MagN;

import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * a Ass6Game class, is in charge of get a path level sets
 * create GameSetUpAndRun object that run the game,
 * if there is not a level sets path send a default level sets path.
 *
 * @author Afik Aharon.
 */
public class Ass6Game {
    /**
     * The main function create GameSetUpAndRun object, load high score table
     * create a game flow object and animation runner  initialize the menu and
     * run it.
     *
     * @param args a level sets path
     */
    public static void main(String[] args) {
        boolean flag = true;
        File fileHighScore = new File(MagN.HIGH_SCORES_PATH);
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(fileHighScore);
        GUI gui = new GUI(MagN.GAME_NAME, MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui, new Sleeper(), MagN.FRAME_RATE);
        GameFlow game = new GameFlow(runner, gui.getKeyboardSensor(), fileHighScore, highScoresTable);
        Reader fileReader = null;
        // try to load a path from the user
        if (args.length != 0) {
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
                fileReader = new InputStreamReader(is);
                flag = false;
            } catch (NullPointerException e) {
                System.out.println("A problem with given level sets path");
            }
        }
        if (flag) {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.LEVEL_SETS_PATH);
            fileReader = new InputStreamReader(is);
        }
        GameSetUpAndRun menuGame = new GameSetUpAndRun(runner, game, gui.getKeyboardSensor(), fileReader);
        menuGame.initialize();
        menuGame.runGameMenu();
    }
}