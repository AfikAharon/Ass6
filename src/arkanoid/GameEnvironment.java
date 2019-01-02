package arkanoid;

import core.Collidable;
import core.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * a GameEnvironment class.
 * <p>
 * The Class is a collection of Collidable objects that the ball can collide with.
 *
 * @author Afik Aharon.
 */
public class GameEnvironment {
    private List<Collidable> collidObj;

    /**
     * Constructor for the GameEnvironment class,
     * that create a empty Collidable objects List.
     */
    public GameEnvironment() {
        this.collidObj = new ArrayList<Collidable>();
    }

    /**
     * The function is in charge of removed collidable from collidObj member.
     *
     * @param c the Collidable for remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidObj.remove(c);
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c a given core.Collidable object
     */
    public void addCollidable(Collidable c) {
        this.collidObj.add(c);
    }

    /**
     * The function check if there is a collision with any objects in the core.Collidable List, if there is
     * return the closet collision else return null.
     * The function create a List of collision objects, and search the closet collision by call the
     * function searchClosetCollision.
     *
     * @param trajectory the trajectory of the object.
     * @return information about the closet collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // check if there is a core.Collidable objects.
        if (this.collidObj.isEmpty()) {
            return null;
        }
        List<Collidable> collisobj = new ArrayList<Collidable>();
        int counter = 0;
        // Make a copy of the Sprite before iterating over them.
        List<Collidable> collidables = new ArrayList<Collidable>(this.collidObj);
        // create a List of collision Colidable.
        for (Collidable c : collidables) {
            if (trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle()) != null) {
                collisobj.add(c);
            }
        }
        if (collisobj.size() == 0) {
            return null;
            // if there is just one collision.
        } else if (collisobj.size() == 1) {
            Rectangle r = (collisobj.get(0)).getCollisionRectangle();
            return new CollisionInfo(trajectory.closestIntersectionToStartOfLine(r), collisobj.get(0));
            // else search the closet collision.
        } else {
            return searchClosetCollision(collisobj, trajectory);
        }
    }

    /**
     * The function search the closet collision to the start of the line.
     *
     * @param arr List of collision collidable objects.
     * @param l   the trajectory of the object.
     * @return information about the closet collision.
     */
    public CollisionInfo searchClosetCollision(List arr, Line l) {
        Point closetP = l.closestIntersectionToStartOfLine(((Collidable) arr.get(0)).getCollisionRectangle());
        Point tempP;
        int counter = 0;
        double minDist = closetP.distance(l.start());
        double tempDist;
        for (int i = 0; i < arr.size(); i++) {
            tempP = l.closestIntersectionToStartOfLine(((Collidable) arr.get(i)).getCollisionRectangle());
            tempDist = tempP.distance(l.start());
            // If the collision object is closet than the prev collision object.
            if (minDist > tempDist) {
                closetP = tempP;
                counter++;
            }
        }
        return new CollisionInfo(closetP, (Collidable) arr.get(counter));
    }

}