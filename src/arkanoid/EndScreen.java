package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import core.Counter;
import core.Sprite;
import useful.MagN;

import java.awt.Color;

/**
 * a EndScreen class, the class is in charge of draw the end screen:
 * win or lost.
 *
 * @author Afik Aharon.
 */
public class EndScreen implements Animation {
    private boolean stop;
    private Counter score;
    private String endString;
    private Sprite background;

    /**
     * Constructor for EndScreen class.
     *
     * @param endString the end screen message
     * @param score     the score game
     */
    public EndScreen(String endString, Counter score) {
        this.stop = false;
        this.score = score;
        this.endString = endString;
        this.background = null;
    }

    /**
     * Sets background.
     *
     * @param backg the background
     */
    public void setBackground(Sprite backg) {
        this.background = backg;
    }

    /**
     * The function is in charge for draw the end screen.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(Color.decode("#E8B7B7"));
            d.fillRectangle(0, 0, MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        }
        d.setColor(Color.BLACK);
        d.drawText(201, d.getHeight() / 2 + 1, this.endString + this.score.getValue(), MagN.SIZE_SCREEN_TEXT);
        d.setColor(Color.decode("#6CFF85"));
        d.drawText(200, d.getHeight() / 2, this.endString + this.score.getValue(), MagN.SIZE_SCREEN_TEXT);
        d.setColor(Color.BLACK);
        d.drawText(201, 551, "Press space to continue", 35);
        d.setColor(Color.decode("#A3FA83"));
        d.drawText(200, 550, "Press space to continue", 35);
        d.setColor(Color.decode("#95CAE8"));
    }

    /**
     * The function is in charge of stopping condition animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}