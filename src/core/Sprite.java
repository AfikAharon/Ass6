package core;

import biuoop.DrawSurface;

/**
 * a sprite interface.
 *
 * a core.Sprite is a game object that can be drawn on a given surface
 * and can be notify the sprite that time has passed.
 *
 *
 * @author @author Afik Aharon.
 */
public interface Sprite {
    /**
     * draw the sprite on the surface.
     *
     * @param d a given draw surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    void timePassed(double dt);
}