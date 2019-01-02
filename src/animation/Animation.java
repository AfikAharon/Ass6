package animation;

import biuoop.DrawSurface;

/**
 * a Animation interface.
 *
 * @author Afik Aharon.
 */
public interface Animation {
    /**
     * The function is in charge of draw the surface in every class that implement
     * of Animation interface.
     * @param d the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * The function is in charge of stopping condition for the animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    boolean shouldStop();
}