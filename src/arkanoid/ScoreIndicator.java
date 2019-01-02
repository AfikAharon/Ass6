package arkanoid;

import biuoop.DrawSurface;
import core.Counter;
import core.Sprite;
import useful.MagN;

import java.awt.Color;

/**
 * a ScoreIndicator class, is in charge of draw the score game.
 *
 * @author Afik Aharon.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor for ScoreIndicator class.
     *
     * @param scoreCounter the score counter
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.score = scoreCounter;
    }

    /**
     * The function is in charge for draw the score game.
     *
     * @param d a given draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(MagN.POSITION_SCORE_MESSAGE_X, MagN.POSITION_MESSAGE_Y,
                MagN.SCORE_MESSAGE + score.getValue(), MagN.SIZE_INFO_TEXT);
    }

    /**
     * The function return the score game.
     *
     * @return the score game.
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void timePassed(double dt) {
    }

    /**
     * The function insert the ScoreIndicator to the given game.
     *
     * @param g a given game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
