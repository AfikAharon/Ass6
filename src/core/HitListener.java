package core;

import arkanoid.Ball;
import arkanoid.Block;

/**
 * The interface Hit listener.
 *
 * @author Afik Aharon.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the beinghit object.
     * @param hitter   the ball that's doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}