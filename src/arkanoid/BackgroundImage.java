package arkanoid;

import biuoop.DrawSurface;
import core.Background;
import geometry.Rectangle;

import java.awt.Image;

/**
 * The class implements Background interface that in charge
 * of draw the block background color.
 *
 * @author Afik Aharon.
 */
public class BackgroundImage implements Background {
    private Image image;

    /**
     * Instantiates a new Background image.
     *
     * @param img the image
     */
    public BackgroundImage(Image img) {
        this.image = img;
    }

    /**
     * the function draw the background block.
     *
     * @param d         the given DrawSurface
     * @param rectangle the Rectangle block
     */
    public void drawOn(DrawSurface d, Rectangle rectangle) {
        d.drawImage((int) rectangle.getUpperLeft().getX(),
                (int) rectangle.getUpperLeft().getY(), this.image);
    }
}
