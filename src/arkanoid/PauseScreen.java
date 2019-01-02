package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import core.Sprite;
import useful.MagN;

import java.awt.Color;

/**
 * a PauseScreen class is in charge of pause the screen.
 *
 * @author Afik Aharon.
 */
public class PauseScreen implements Animation {
    private boolean stop;
    private Sprite background;

    /**
     * Constructor for PauseScreen class.
     */
    public PauseScreen() {
        this.stop = false;
        this.background = null;
    }

    /**
     * The function check if the user press the space KeyboardSensor (if yes change the boolean
     * member to true for return the game) and draw the pause screen.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(Color.decode("#67489E"));
            d.fillRectangle(0, 0, MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        }
        d.setColor(Color.WHITE);
        d.drawText(180 + 1, d.getHeight() / 2 + 1, MagN.PAUSE_MESSAGE, 32);
        d.setColor(Color.decode("#808ACA"));
        d.drawText(180, d.getHeight() / 2, MagN.PAUSE_MESSAGE, 32);
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
     * The function is in charge of stopping condition animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}