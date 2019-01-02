package core;

import geometry.Point;

/**
 * a Velocity class.
 *
 * @author Afik Aharon.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructors for the class.
     *
     * @param dx the change in the x coordinate
     * @param dy the change in the y coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * The function return the change in the x coordinate.
     *
     * @return the member dx value
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * The function return the change in the y coordinate.
     *
     * @return the member dy value
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * The function calculate the dx and dy, with a speed and angle.
     *
     * @param angle the angle of the line
     * @param speed the speed of the line
     * @return new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = (-1) * speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }

    /**
     * The function calculate the new position of the ball
     * with the change in the x y axis.
     *
     * @param p The position of the ball
     * @param dt It specifies the amount of seconds passed since the last call.
     * @return The new position after the change on the x y axis.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + dt * this.dx,
                p.getY() + dt * this.dy);
    }
}