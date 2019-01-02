package arkanoid;

import core.Counter;
import core.HitListener;

/**
 * a BallRemover class is in charge of removing balls from the game.
 *
 * @author Afik Aharon.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructor for BallRemover class.
     *
     * @param game         the given game.
     * @param removedBalls the counter balls.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * If the ball arrived to the death region the function is being call
     * and the ball removed from the game.
     *
     * @param beingHit the death region block.
     * @param hitter   the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBalls.decrease(1);
        hitter.removeFromGame(this.game);
    }
}
