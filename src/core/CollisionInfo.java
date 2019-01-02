package core;

import geometry.Point;

/**
 * a CollisionInfo class.
 * <p>
 * the CollisionInfo class holds information about the point at which the collision occurs,
 * and the collidable object involved in the collision.
 *
 * @author Afik Aharon.
 */
public class CollisionInfo {
    private Point collPoint;
    private Collidable c;

    /**
     * Constructor for CollisionInfo class.
     *
     * @param collP the collision point.
     * @param coll  the collidable object
     */
    public CollisionInfo(Point collP, Collidable coll) {
        this.collPoint = collP;
        this.c = coll;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * @return The collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.c;
    }
}