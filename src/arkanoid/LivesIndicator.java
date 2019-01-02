package arkanoid;

import biuoop.DrawSurface;
import core.Counter;
import core.Sprite;
import useful.MagN;

import java.awt.Color;

/**
 * a LivesIndicator class.
 *
 * @author Afik Aharon.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Constructor for EndScreen class.
     *
     * @param c the lives counter.
     */
    public LivesIndicator(Counter c) {
        this.lives = c;
    }

    /**
     * The function is in charge of draw the amount lives on the given DrawSurface.
     *
     * @param d a given draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(MagN.POSITION_LIVES_MESSAGE_X, MagN.POSITION_MESSAGE_Y,
                MagN.LIVES_MESSAGE + this.lives.getValue(), MagN.SIZE_INFO_TEXT);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void timePassed(double dt) {
    }

    /**
     * the function return the lives member.
     *
     * @return the counter lives game
     */
    public Counter getLives() {
        return this.lives;
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

