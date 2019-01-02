package arkanoid;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import core.Collidable;
import core.Counter;
import core.HitListener;
import core.Sprite;
import core.Velocity;
import core.Background;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import useful.MagN;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.InputStream;
import java.io.IOException;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a Game class.
 * the class is in charge of initializing and running the game.
 *
 * @author Afik Aharon.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter counterBalls;
    private ScoreTrackingListener scoreNum;
    private AnimationRunner runner;
    private ScoreIndicator scoreIndicator;
    private LivesIndicator livesIndicator;
    private boolean running;
    private Paddle paddle;
    private LevelInformation levelInformation;
    private KeyboardSensor keyboard;
    private DrawLevelName levelName;

    /**
     * Constructor for the game class.
     *
     * @param levelInformation the level information game
     * @param runner           the AnimationRunner
     * @param keyboard         the KeyboardSensor game.
     * @param score            the score game.
     * @param lives            the amount lives game.
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner runner,
                     KeyboardSensor keyboard, ScoreIndicator score, LivesIndicator lives) {
        this.sprites = new SpriteCollection(1.0 / runner.getFramesPerSecond());
        this.environment = new GameEnvironment();
        this.levelInformation = levelInformation;
        this.scoreIndicator = score;
        this.livesIndicator = lives;
        this.runner = runner;
        this.keyboard = keyboard;
    }

    /**
     * The function is in charge of removed Collidable from the game.
     *
     * @param c the Collidable for remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * The function is in charge of removed Sprite from the game.
     *
     * @param s the Sprite for remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * The function insert a Collidable object to the game.
     *
     * @param c a given Collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * The function insert a Sprite object to the game.
     *
     * @param s a given Sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * The function initialize the game.
     */
    public void initialize() {
        this.counterBalls = new Counter(0);
        this.scoreNum = new ScoreTrackingListener(this.scoreIndicator.getScore());
        this.levelName = new DrawLevelName(this.levelInformation.levelName());
        BlockRemover listenerBlocks = new BlockRemover(this,
                this.levelInformation.getCounterOfNumberOfBlocksToRemove());
        BallRemover listenerBalls = new BallRemover(this, this.counterBalls);
        Rectangle recPaddle = new Rectangle(
                new Point(MagN.PADDLE_START_POSITION_X - this.levelInformation.paddleWidth() / 2,
                        MagN.PADDLE_START_POSITION_Y), this.levelInformation.paddleWidth(),
                MagN.PADDLE_HEIGHT);
        this.addSprite(this.levelInformation.getBackground());
        this.paddle = new Paddle(recPaddle, this.keyboard, Color.YELLOW, this.levelInformation.paddleSpeed());
        paddle.addToGame(this);
        this.createBordersGame(listenerBalls);
        this.addBlocksToGame(listenerBlocks);
        this.addSprite(this.levelName);
        this.scoreIndicator.addToGame(this);
        this.livesIndicator.addToGame(this);
    }

    /**
     * The function is in charge of stopping condition to the animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * The function is in charge of draw the all sprites game and check 3 things :
     * 1. if the user want to pause the game.
     * 2. if the player not have more lives, if yes change the boolean member to false for indication to the function
     * shouldStop.
     * 3. if the player removed the all blocks, if yes increase the score (100 points) and change the boolean member
     * to false for indication to the function shouldStop.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.levelInformation.numberOfBlocksToRemove() == 0 || this.counterBalls.getValue() == 0) {
            // if the player removed the all blocks
            if (this.levelInformation.numberOfBlocksToRemove() == 0) {
                this.scoreNum.getCurrentScore().increase(MagN.ALL_BALLS_REMOVED_POINTS);
            }
            // if the player not have more lives.
            if (this.counterBalls.getValue() == 0) {
                this.livesIndicator.getLives().decrease(1);
            }
            this.running = false;
        }
        // if the user pause the game
        if (this.keyboard.isPressed("p")) {
            PauseScreen pauseScreen = new PauseScreen();
            InputStream is = null;
            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(MagN.PAUSE_SCREEN_IMAGE_PATH);
                Image image = ImageIO.read(is);
                pauseScreen.setBackground(new DrawImage(image));
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
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", pauseScreen));
        }
    }

    /**
     * The function run the game one turn each time, until the player
     * removed the all blocks or lost the balls game.
     */
    public void playOneTurn() {
        // move the paddle to the middle.
        this.paddle.setRectangle(
                new Rectangle(
                        new Point(MagN.PADDLE_START_POSITION_X - this.levelInformation.paddleWidth() / 2,
                                MagN.PADDLE_START_POSITION_Y), this.levelInformation.paddleWidth(),
                        MagN.PADDLE_HEIGHT));
        // create the balls game.
        this.createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * The function is in charge of add the blocks to the game.
     * and add the blockRemover to the blocks.
     *
     * @param listenerBlocks the blockRemover
     */
    public void addBlocksToGame(HitListener listenerBlocks) {
        List<Block> blocks = this.levelInformation.blocks();
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).addToGame(this);
            blocks.get(i).addHitListener(listenerBlocks);
            blocks.get(i).addHitListener(this.scoreNum);
        }
    }

    /**
     * The function is in charge of create the balls game on the top paddle,
     * by the information from the levelInformation member.
     */
    public void createBallsOnTopOfPaddle() {
        Ball ball = null;
        // get the Velocity from the levelInformation member.
        List<Velocity> velocities = this.levelInformation.initialBallVelocities();
        for (int i = 0; i < velocities.size(); i++) {
            ball = new Ball(new Point(MagN.BALL_START_POSITION_X, MagN.BALL_START_POSITION_Y),
                    MagN.BALL_RADIUS, Color.decode("#CBB4B4"));
            ball.setVelocity(velocities.get(i));
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }
        this.counterBalls.increase(this.levelInformation.numberOfBalls());
    }

    /**
     * the function create the game borders and the death region ball and add them to the game.
     *
     * @param listenerBalls the BallRemover.
     */
    public void createBordersGame(HitListener listenerBalls) {
        int width = MagN.GUI_WIDTH;
        int height = MagN.GUI_HEIGHT;
        Map<Integer, Background> background = new TreeMap<>();
        Map<Integer, Background> titleBlockBackground = new TreeMap<>();
        titleBlockBackground.put(1, new BackgroundColor(Color.decode("#EAEBE4")));
        background.put(1, new BackgroundColor(Color.GRAY));
        Block titleBlock = new Block(new Rectangle(new Point(0, 0), MagN.GUI_WIDTH, MagN.WID_HEI_BLOCKS),
                titleBlockBackground, null, 1);
        Block deathBlock = new Block(new Rectangle(new Point(0, height + MagN.WID_HEI_BLOCKS),
                width, MagN.WID_HEI_BLOCKS), background, null, 1);
        Block[] borders = new Block[3];
        borders[0] = new Block(new Rectangle(new Point(0, MagN.WID_HEI_BLOCKS),
                MagN.WID_HEI_BLOCKS, height), background, null, 1);
        borders[1] = new Block(new Rectangle(new Point(width - MagN.WID_HEI_BLOCKS,
                MagN.WID_HEI_BLOCKS), MagN.WID_HEI_BLOCKS, height), background, null, 1);
        borders[2] = new Block(new Rectangle(new Point(0, MagN.WID_HEI_BLOCKS),
                width, MagN.WID_HEI_BLOCKS), background, null, 1);
        for (int i = 0; i < borders.length; i++) {
            borders[i].setBorderBlock();
            borders[i].addToGame(this);
        }
        // add the ball remover to deathBlock
        deathBlock.addHitListener(listenerBalls);
        deathBlock.addToGame(this);
        // add a block title fill for the information level
        titleBlock.setBorderBlock();
        titleBlock.addToGame(this);
    }
}