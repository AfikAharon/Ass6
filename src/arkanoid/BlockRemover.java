package arkanoid;

import core.Counter;
import core.HitListener;

/**
 * a BlockRemover class.
 * BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 *
 * @author Afik Aharon.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor for BlockRemover class.
     *
     * @param game          the given game
     * @param removedBlocks the removed blocks counter.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * The function in charge of remove blocks that are hit and reach 0 hit-points.
     *
     * @param beingHit the hit block.
     * @param hitter   the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() <= 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            // Decrease the number of blocks.
            this.remainingBlocks.decrease(1);
        }
    }
}