package arkanoid;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import core.Sprite;

/**
 * a SpriteCollection class.
 * holds a list of sprite objects.
 *
 * @author Afik Aharon.
 */
public class SpriteCollection {
    private List<Sprite> spriteObj;
    private double dt;

    /**
     * Constructor for the SpriteCollection class.
     * create a empty List.
     *
     * @param dt It specifies the amount of seconds passed since the last call.
     */
    public SpriteCollection(double dt) {
        this.spriteObj = new ArrayList<Sprite>();
        this.dt = dt;
    }

    /**
     * The function remove Sprite from the spriteObj member.
     *
     * @param s the Sprite that need to remove.
     */
    public void removeSprite(Sprite s) {
        this.spriteObj.remove(s);
    }

    /**
     * The function add the sprite object to the List.
     *
     * @param s a given sprite object.
     */
    public void addSprite(Sprite s) {
        this.spriteObj.add(s);
    }

    /**
     * The function call timePassed() on all sprites
     * and make a copy of the Sprite to avoid an exception.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the Sprite before iterating over them.
        List<Sprite> listeners = new ArrayList<Sprite>(this.spriteObj);
        for (Sprite s : listeners) {
            s.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the draw surface.
     */
    public void drawAllOn(DrawSurface d) {
        // Make a copy of the hitListeners before iterating over them.
        List<Sprite> listeners = new ArrayList<Sprite>(this.spriteObj);
        for (Sprite s : listeners) {
            s.drawOn(d);
        }
    }
}