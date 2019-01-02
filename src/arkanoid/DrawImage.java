package arkanoid;

import biuoop.DrawSurface;
import core.Sprite;

import java.awt.Image;

/**
 * The class is implements sprite , and in charge
 * of draw the level background image.
 *
 * @author Afik Aharon.
 */
public class DrawImage implements Sprite {
    private Image image;

    /**
     * Instantiates a new Draw image.
     *
     * @param img the image
     */
    public DrawImage(Image img) {
        this.image = img;
    }

    /**
     * The function draw the image on the given DrawSurface.
     *
     * @param d a given draw surface
     */
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, this.image);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void timePassed(double dt) {

    }
}
