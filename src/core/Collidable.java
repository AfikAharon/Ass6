package core;

import arkanoid.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 * a Collidable interface.
 *
 * The Collidable interface used for things that can be collided with.
 *
 * @author Afik Aharon.
 */
public interface Collidable {

    /**
     * The function return the "collision shape" of the object.
     *
     * @return the collision geometry.Rectangle.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint the collision point on the rectangle.
     * @param currentVelocity the current velocity of the ball.
     * @param hitter the hitter ball game
     * @return the new velocity after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}