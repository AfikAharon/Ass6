package levels;

import core.Counter;
import core.Sprite;
import core.Velocity;
import arkanoid.Block;

import java.util.List;

/**
 * The interface LevelDetails information.
 *
 * @author Afik Aharon.
 */
public interface LevelInformation {
    /**
     * the function return the amount of balls game.
     *
     * @return the number of balls game.
     */
    int numberOfBalls();

    /**
     * The function initialize the Velocity of each ball in the game.
     *
     * @return the list of Velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * The function return the Paddle speed.
     *
     * @return the Paddle speed
     */
    int paddleSpeed();

    /**
     * The function return the Paddle width.
     *
     * @return the Paddle width.
     */
    int paddleWidth();

    /**
     * the function return the level name string.
     *
     * @return the level name string
     */
    String levelName();

    /**
     * The function return a Sprite that draw the background level.
     *
     * @return the background Sprite.
     */
    Sprite getBackground();

    /**
     * The function create a list of blocks that make up this level.
     *
     * @return the list blocks
     */
    List<Block> blocks();

    /**
     * The function return the remain blocks to remove.
     *
     * @return remain blocks to remove.
     */
    int numberOfBlocksToRemove();

    /**
     * The function return the counter of number of blocks to remove.
     *
     * @return the counter of number of blocks to remove
     */
    Counter getCounterOfNumberOfBlocksToRemove();
}