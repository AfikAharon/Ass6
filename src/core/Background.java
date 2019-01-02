package core;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The interface Background for the background block.
 *
 * @author Afik Aharon.
 */
public interface Background {
    /**
     * Draw the background.
     *
     * @param d         the DrawSurface
     * @param rectangle the rectangle block
     */
    void drawOn(DrawSurface d, Rectangle rectangle);
}