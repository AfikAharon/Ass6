package arkanoid;

import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import core.Counter;
import core.HighScoresTable;
import core.ScoreInfo;
import levels.LevelInformation;
import useful.MagN;

import javax.imageio.ImageIO;
import java.awt.Image;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.List;

/**
 * a GameFlow class, the class run the levels game.
 *
 * @author Afik Aharon.
 */
public class GameFlow {
    private ScoreIndicator scoreIndicator;
    private LivesIndicator livesIndicator;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private HighScoresTable highScoresTable;
    private File fileScoresTable;

    /**
     * Constructor for EndScreen class.
     *
     * @param ar              the AnimationRunner
     * @param ks              the KeyboardSensor
     * @param fileScoresTable the file scores table
     * @param highScoresTable the high scores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, File fileScoresTable, HighScoresTable highScoresTable) {
        this.scoreIndicator = new ScoreIndicator(new Counter(0));
        this.livesIndicator = new LivesIndicator(new Counter(7));
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.highScoresTable = highScoresTable;
        this.fileScoresTable = fileScoresTable;
    }

    /**
     * the function run the given levels game.
     *
     * @param levels the levels game
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor,
                    this.scoreIndicator, this.livesIndicator);
            level.initialize();
            // while the player have more lives and have more blocks to removed.
            while (this.livesIndicator.getLives().getValue() > 0 && levelInfo.numberOfBlocksToRemove() > 0) {
                level.playOneTurn();
            }
            // If the player not have more lives game.
            if (this.livesIndicator.getLives().getValue() == 0) {
                break;
            }
        }
        runTheTableAndEndScreen();
        // initialize for reuse
        this.scoreIndicator = new ScoreIndicator(new Counter(0));
        this.livesIndicator = new LivesIndicator(new Counter(7));
    }

    /**
     * The function run the high score table and the end screen.
     */
    public void runTheTableAndEndScreen() {
        KeyPressStoppableAnimation keyPressAnimation = new KeyPressStoppableAnimation(this.keyboardSensor,
                                                                                  "space", null);
        EndScreen endScreen = null;
        // if the player win run a win end screen.
        if (this.livesIndicator.getLives().getValue() > 0) {
            endScreen = new EndScreen(MagN.WIN_MESSAGE, scoreIndicator.getScore());
            // otherwise the player lost run a lost end screen.
        } else {
            endScreen = new EndScreen(MagN.LOST_MESSAGE, scoreIndicator.getScore());
        }
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.END_SCREEN_IMAGE_PATH);
            Image image = ImageIO.read(is);
            endScreen.setBackground(new DrawImage(image));
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
        keyPressAnimation.setAnimation(endScreen);
        this.animationRunner.run(keyPressAnimation);
        if (this.highScoresTable.getRank(this.scoreIndicator.getScore().getValue()) < this.highScoresTable.size()
                                                              && this.scoreIndicator.getScore().getValue() > 0) {
            DialogManager dialog = this.animationRunner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog(MagN.DIALOG_MESSAGE_ONE, MagN.DIALOG_MESSAGE_TOW, "");
            this.highScoresTable.add(new ScoreInfo(name, this.scoreIndicator.getScore().getValue()));
            try {
                this.highScoresTable.save(this.fileScoresTable);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Gets high scores table.
     *
     * @return the high scores table
     */
    public HighScoresTable getHighScoresTable() {
        return this.highScoresTable;
    }
}