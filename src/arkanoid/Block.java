package arkanoid;

import biuoop.DrawSurface;
import core.Collidable;
import core.Sprite;
import core.Counter;
import core.HitListener;
import core.HitNotifier;
import core.Background;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a Block class.
 *
 * @author Afik Aharon.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private Color strokeColor;
    private Counter counterHitsLeft;
    private Map<Integer, Background> backgrounds;
    private List<HitListener> hitListeners;
    private boolean indicationBorderBlock;

    /**
     * Instantiates a new Block.
     *
     * @param r           the Rectangle
     * @param backgrounds the backgrounds block
     * @param strokeColor the stroke color
     * @param hitPoints   the hit points
     */
    public Block(Rectangle r, Map<Integer, Background> backgrounds, Color strokeColor, int hitPoints) {
        this.block = r;
        this.strokeColor = strokeColor;
        this.counterHitsLeft = new Counter(hitPoints);
        hitListeners = new ArrayList<HitListener>();
        this.indicationBorderBlock = false;
        this.backgrounds = backgrounds;
    }

    /**
     * Sets border block.
     */
    public void setBorderBlock() {
        this.indicationBorderBlock = true;
    }

    /**
     * The function increases the counterHits member.
     *
     * @param hits the new number hits.
     */
    public void increaseAmountHits(int hits) {
        this.counterHitsLeft.increase(hits);
    }

    /**
     * The function return the rectangle member.
     *
     * @return the rectangle block.
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * The function draw the block on the surface.
     *
     * @param surface the drawing surface.
     */
    public void drawOn(DrawSurface surface) {
        // if the backgrounds map have the key value of the hit point;
        if (backgrounds.containsKey(this.counterHitsLeft.getValue())) {
            backgrounds.get(counterHitsLeft.getValue()).drawOn(surface, this.block);
        }
        // if the class have stroke color
        if (strokeColor != null) {
            int startX = (int) this.block.getUpperLeft().getX();
            int startY = (int) this.block.getUpperLeft().getY();
            int endX = (int) this.block.getWidth() + startX;
            int endY = (int) this.block.getHeight() + startY;
            surface.setColor(this.strokeColor);
            surface.drawLine(startX, startY, endX, startY);
            surface.drawLine(startX, startY, startX, endY);
            surface.drawLine(endX, startY, endX, endY);
            surface.drawLine(startX, endY, endX, endY);
        }
    }

    /**
     * The function insert the block to the given game.
     *
     * @param g a given game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * The function is in charge of remove the block from the game.
     *
     * @param game the given game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * The function will be realized in the next assignment.
     *
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void timePassed(double dt) {
    }

    /**
     * The function check where the object hit on the borders block,
     * and change the velocity, if the object hit on the bottom or the top
     * borders change the vertical direction, if the object hit on the sides borders
     * change the horizon direction.
     *
     * @param collisionPoint  the collision point.
     * @param currentVelocity the current velocity of the object.
     * @param hitter          the hitter ball.
     * @return the new velocity after the change.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        boolean flagHit = false;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        if (collisionPoint.getY() == this.block.getHeight() + this.block.getUpperLeft().getY()
                || collisionPoint.getY() == this.block.getUpperLeft().getY()) {
            dy = (-1) * dy;
            flagHit = true;
        }
        if (collisionPoint.getX() == this.block.getWidth() + this.block.getUpperLeft().getX()
                || collisionPoint.getX() == this.block.getUpperLeft().getX()) {
            dx = (-1) * dx;
            flagHit = true;
        }
        if (flagHit) {
            if (!indicationBorderBlock) {
                this.counterHitsLeft.decrease(1);
                this.notifyHit(hitter);
            }
        }
        return new Velocity(dx, dy);
    }

    /**
     * The function is in charge of add a HitListener to the
     * hitListeners member.
     *
     * @param hl a given HitListener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * The function is in charge of removed a HitListener
     * from the hitListeners member.
     *
     * @param hl the HitListener to remove.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * The function is in charge of calls the function hitEvent from the hitListeners
     * list, after the hit.
     * The function make a copy of the hitListeners before iterating over them to avoid en exception.
     *
     * @param hitter the hitter ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * The function is in charge of return the remain number hits.
     *
     * @return the remain number hit.
     */
    public int getHitPoints() {
        return this.counterHitsLeft.getValue();
    }

    /**
     * Gets block width.
     *
     * @return the block width
     */
    public int getBlockWidth() {
        return (int) this.block.getWidth();
    }
}

