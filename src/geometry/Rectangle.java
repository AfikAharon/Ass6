package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * a Rectangle class.
 * <p>
 * the rectangle is build by upperLeft geometry.Point height and width.
 *
 * @author Afik Aharon.
 */
public class Rectangle {
    private double width;
    private double height;
    private Point upperLeft;

    /**
     * Construct a rectangle by given upperLeft geometry.Point height and width.
     *
     * @param upperLeft start of the rectangle.
     * @param width     the rectangle width.
     * @param height    the rectangle height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.height = height;
        this.width = width;
        this.upperLeft = upperLeft;
    }

    /**
     * The function create the lines of the rectangle and
     * return a List of intersection points with a given line.
     * <p>
     * The list can be empty, if there is not a intersection points.
     *
     * @param line a given line
     * @return List of the intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interPoints = new ArrayList<Point>();
        Point lowerRight = new Point(this.width + this.upperLeft.getX(), this.height + this.upperLeft.getY());
        Point upperRight = new Point(this.width + this.upperLeft.getX(), this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.height + this.upperLeft.getY());
        Line upperLine = new Line(this.upperLeft, upperRight);
        Line bottomLine = new Line(lowerLeft, lowerRight);
        Line leftLine = new Line(lowerLeft, this.upperLeft);
        Line rightLine = new Line(lowerRight, upperRight);
        // check if the given line intersection with the upperLine.
        if (line.isIntersecting(upperLine)) {
            interPoints.add(line.intersectionWith(upperLine));
        }
        // // check if the given line intersection with the bottomLine.
        if (line.isIntersecting(bottomLine)) {
            interPoints.add(line.intersectionWith(bottomLine));
        }
        // check if the given line intersection with the leftLine.
        if (line.isIntersecting(leftLine)) {
            interPoints.add(line.intersectionWith(leftLine));
        }
        // check if the given line intersection with the rightLine.
        if (line.isIntersecting(rightLine)) {
            interPoints.add(line.intersectionWith(rightLine));
        }
        return interPoints;
    }

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}