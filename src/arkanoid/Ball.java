package arkanoid;

import biuoop.DrawSurface;
import core.Collidable;
import core.CollisionInfo;
import core.Sprite;
import core.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * a Ball class.
 *
 * @author Afik Aharon.
 */
public class Ball implements Sprite {
    private Point p;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment gameE;

    /**
     * Constructor for ball class.
     *
     * @param center the center of the ball
     * @param r      the size of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.p = center;
        this.radius = r;
        this.color = color;
        gameE = new GameEnvironment();
    }

    /**
     * The function return the GameEnvironment member.
     *
     * @return the GameEnvironment member
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameE;
    }

    /**
     * The function return the position of the ball
     * in the x axis.
     *
     * @return The ball position in the x axis
     */
    public int getX() {
        return (int) this.p.getX();
    }

    /**
     * The function return the position of the ball
     * in the y axis.
     *
     * @return The ball position in the y axis.
     */
    public int getY() {
        return (int) this.p.getY();
    }

    /**
     * The function return the size of the ball radius.
     *
     * @return the radius value.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * The function return the ball color.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * The function set the arkanoid.GameEnvironment member.
     *
     * @param gameEnvironment the arkanoid.GameEnvironment.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameE = gameEnvironment;
    }

    /**
     * The function sets the member velocity.
     *
     * @param v velocity
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * The function sets the member velocity.
     *
     * @param dx the change in the x coordinate
     * @param dy the change in the y coordinate
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    /**
     * The function sets the coordinates of the ball.
     *
     * @param newLocation new point location
     */
    public void setLocation(Point newLocation) {
        this.p = newLocation;
    }

    /**
     * The function return the member velocity.
     *
     * @return the velocity member of the class.
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * The function calculate line (start current location and end the next location)
     * , create an object core.CollisionInfo and call to the function getClosestCollision from the
     * object for search a collision point with blocks from the game.
     * If there is a collision point change the location almost to the collision point
     * (the object doesn't equal to null), else take the ball to the next step.
     * If there is a collision point the function deal with 5 cases:
     * 1. if the ball hit on one of the corners of the block.
     * 2. if the ball hit on the left border of the block.
     * 3. if the ball hit on the right border of the block.
     * 4. if the ball hit on the top border of the block.
     * 5. if the ball hit on the bottom border of the block.
     *
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void moveOneStep(double dt) {
        double movementX = this.p.getX() + dt * this.vel.getDx();
        double movementY = this.p.getY() + dt * this.vel.getDy();
        Line trajectory = new Line(this.p, new Point(movementX, movementY));
        CollisionInfo collInfo = gameE.getClosestCollision(trajectory);
        // if there is a collision point collInfo doesn't equal to null.
        if (collInfo != null) {
            double x = this.getX();
            double y = this.getY();
            Point collisionPoint = collInfo.collisionPoint();
            Collidable obj = collInfo.collisionObject();
            Rectangle r = obj.getCollisionRectangle();
            // Check if the ball hit on one of the corners block.
            if (collisionPoint.getY() == collisionPoint.getX()) {
                // If the ball hit on the top corners.
                if (collisionPoint.getY() == r.getUpperLeft().getY()
                        || collisionPoint.getX() == r.getUpperLeft().getX()) {
                    x = collisionPoint.getX() - this.getSize() - 0.001;
                    y = collisionPoint.getY() - this.getSize() - 0.001;
                    // else the ball hit on the bottom corners.
                } else {
                    x = collisionPoint.getX() + this.getSize() + 0.001;
                    y = collisionPoint.getY() + this.getSize() + 0.001;
                }
                // Set a new velocity.
                this.setVelocity(obj.hit(this, collInfo.collisionPoint(), this.getVelocity()));
                this.p = new Point(x, y);
                return;
            }

            // If the ball hit on the left border block.
            if (collisionPoint.getX() == r.getUpperLeft().getX()) {
                x = collisionPoint.getX() - this.getSize() - 0.001;
                y = collisionPoint.getY();
                // If the ball hit on the right border block.
            } else if (collisionPoint.getX() == r.getWidth() + r.getUpperLeft().getX()) {
                x = collisionPoint.getX() + this.getSize() + 0.001;
                y = collisionPoint.getY();

            }
            // If the ball hit on the top border block.
            if (collisionPoint.getY() == r.getUpperLeft().getY()) {
                y = collisionPoint.getY() - this.getSize() - 0.001;
                x = collisionPoint.getX();
                // If the ball hit on the bottom border block.
            } else if (collisionPoint.getY() == r.getHeight() + r.getUpperLeft().getY()) {
                y = collisionPoint.getY() + this.getSize() + 0.001;
                x = collisionPoint.getX();
            }
            // set a new velocity.
            this.setVelocity(obj.hit(this, collInfo.collisionPoint(), this.getVelocity()));
            this.p = new Point(x, y);
            // else move the ball one step.
        } else {
            this.p = this.getVelocity().applyToPoint(this.p, dt);
        }
    }

    /**
     * The function insert the ball to the given game.
     *
     * @param g a given game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * The function call to the function moveOneStep.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * The function drawing the ball on the given drawSurface.
     *
     * @param surface the given drawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) this.p.getX(), (int) this.p.getY(), this.radius);
        surface.setColor(Color.RED);
        surface.fillCircle((int) this.p.getX(), (int) this.p.getY(), 1);
    }

    /**
     * The function is in charge for remove the ball from the game.
     *
     * @param g the game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}
