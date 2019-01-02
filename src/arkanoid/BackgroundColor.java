package arkanoid;

import biuoop.DrawSurface;
import core.Background;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The class implements Background interface that in charge
 * of draw the block background color.
 *
 * @author Afik Aharon.
 */
public class BackgroundColor implements Background {
    private Color color;

    /**
     * Instantiates a new Background color.
     *
     * @param color the color
     */
    public BackgroundColor(Color color) {
        this.color = color;
    }

    /**
     * the function draw the background block.
     *
     * @param d         the given DrawSurface
     * @param rectangle the Rectangle block
     */
    public void drawOn(DrawSurface d, Rectangle rectangle) {
        int startX = (int) rectangle.getUpperLeft().getX();
        int startY = (int) rectangle.getUpperLeft().getY();
        int endX = (int) rectangle.getWidth() + startX;
        int endY = (int) rectangle.getHeight() + startY;
        d.setColor(this.color);
        d.fillRectangle(startX, startY, (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }
}
