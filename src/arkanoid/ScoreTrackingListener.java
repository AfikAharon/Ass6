package arkanoid;

import core.Counter;
import core.HitListener;
import useful.MagN;

/**
 * a ScoreTrackingListener class, is in charge of increase the score game.
 *
 * @author Afik Aharon.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor for ScoreTrackingListener class..
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }


    /**
     * Gets current score Counter.
     *
     * @return the current score Counter.
     */
    public Counter getCurrentScore() {
        return currentScore;
    }


    /**
     * The function check if the balls have more hit and accordingly increase
     * the score :
     * 1. if the block not have more hit increase 10 points
     * otherwise increase 5 point
     *
     * @param beingHit the being hit block
     * @param hitter   the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(MagN.REMOVED_HIT_POINTS);
        }
        this.currentScore.increase(MagN.HIT_POINTS);
    }
}