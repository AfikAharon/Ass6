package geometry;

import java.util.List;

/**
 * a Line class.
 *
 * @author Afik Aharon.
 */
public class Line {
    private Point p1;
    private Point p2;
    private double gradient;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;

    /**
     * Constructor for the Line class.
     *
     * @param start the start of the line
     * @param end   the end of the line
     */
    public Line(Point start, Point end) {
        this.p1 = start;
        this.p2 = end;
        if (start.getX() != end.getX()) {
            this.gradient = (start.getY() - end.getY()) / (start.getX() - end.getX());
        }
        // calculate the min and max values
        maxX = Math.max(start.getX(), end.getX());
        minX = Math.min(start.getX(), end.getX());
        maxY = Math.max(start.getY(), end.getY());
        minY = Math.min(start.getY(), end.getY());
    }

    /**
     * Constructor for the Line class.
     *
     * @param x1 the x value of the start point
     * @param y1 the y value of the start point
     * @param x2 the x value of the end point
     * @param y2 the y value of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
        // check if the line are parallel to y axis
        if (x1 != x2) {
            this.gradient = (y1 - y2) / (x1 - x2);
        }
        // calculate the min and max values
        maxX = Math.max(x1, x2);
        minX = Math.min(x1, x2);
        maxY = Math.max(y1, y2);
        minY = Math.min(y1, y2);
    }

    /**
     * The function return the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return this.p1.distance(this.p2);
    }

    /**
     * The function calculate the middle of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double xTemp = (this.p1.getX() + this.p2.getX()) / 2;
        double yTemp = (this.p1.getY() + this.p2.getY()) / 2;
        return new Point(xTemp, yTemp);
    }

    /**
     * The function return the start point.
     *
     * @return the start point
     */
    public Point start() {
        return this.p1;
    }

    /**
     * The function return the end point.
     *
     * @return the end point
     */
    public Point end() {
        return this.p2;
    }

    /**
     * The function return the gradient of the line.
     *
     * @return the gradient value
     */
    public double getGradient() {
        return this.gradient;
    }

    /**
     * The function return the max x value of the line.
     *
     * @return the max x value
     */
    public double getMaxX() {
        return this.maxX;
    }

    /**
     * The function return the max y value of the line.
     *
     * @return the max x value
     */
    public double getMaxY() {
        return this.maxY;
    }

    /**
     * The function return the min x value of the line.
     *
     * @return the min x value
     */
    public double getMinX() {
        return this.minX;
    }

    /**
     * The function return the min y value of the line.
     *
     * @return the min y value
     */
    public double getMinY() {
        return this.minY;
    }

    /**
     * The function calculate the intersection point of two lines
     * and deal with 5 cases:
     * 1. if the lines are parallel
     * 2. if one of the lines are parallel to x axis
     * 3. if one of the lines are parallel to y axis
     * 4. if one of the lines are parallel to y axis and the other to x axis.
     * otherwise calculate the intersection pint regular.
     *
     * @param other the other line for the check.
     * @return return the intersection point , otherwise return null
     */
    public Point calculateIntersectionPoint(Line other) {
        Point intersectionP;
        double yInter;
        double xInter;
        // check if one of the lines are parallel to x axis and the other to y axis
        if (this.start().getX() == this.end().getX() && other.start().getY() == other.end().getY()) {
            yInter = other.start().getY();
            xInter = this.start().getX();
            return new Point(xInter, yInter);
        }
        // check if one of the lines are parallel to x axis and the other to y axis
        if (other.start().getX() == other.end().getX() && this.start().getY() == this.end().getY()) {
            yInter = this.start().getY();
            xInter = other.start().getX();
            return new Point(xInter, yInter);
        }
        // check if the lines are parallel.
        if (this.gradient == other.gradient) {
            return null;
        }
        // check if one of the lines are parallel to x axis
        if ((this.start().getY() == this.end().getY()) && (other.start().getY() == other.end().getY())) {
            return null;
        }

        // check if one of the lines are parallel to y axis
        if (this.start().getX() == this.end().getX()) {
            xInter = this.start().getX();
            double bL = other.start().getY() - other.gradient * other.start().getX();
            yInter = other.gradient * xInter + bL;
            intersectionP = new Point(xInter, yInter);
            return intersectionP;

        }
        // check if one of the lines are parallel to y axis
        if (other.start().getX() == other.end().getX()) {
            xInter = other.start().getX();
            double bL = this.start().getY() - this.gradient * this.start().getX();
            yInter = this.gradient * xInter + bL;
            intersectionP = new Point(xInter, yInter);
            return intersectionP;

        }
        // otherwise calculate the intersecting point normal
        double bL1 = this.start().getY() - this.gradient * this.start().getX();
        double bL2 = other.start().getY() - other.gradient * other.start().getX();
        xInter = (bL1 - bL2) / (other.gradient - this.gradient);
        // check if one of the geometry.Line parallel to x axis
        if (this.start().getY() == this.end().getY()) {
            yInter = this.start().getY();
        } else if (other.start().getY() == other.end().getY()) {
            yInter = other.start().getY();
        } else {
            yInter = this.gradient * xInter + bL1;
        }
        intersectionP = new Point(xInter, yInter);
        return intersectionP;
    }

    /**
     * The function check if two line are intersecting.
     * the function uses with the function calculateIntersectionPoint and get
     * the intersection point if the point are equal to null the lines are not
     * intersecting ,after that check if the point between the the two points of
     * each line.
     *
     * @param other the other line for the check
     * @return true if the line are intersection otherwise return false
     */
    public boolean isIntersecting(Line other) {
        Point intersectionP = calculateIntersectionPoint(other);
        if (intersectionP == null) {
            return false;
        }
        // check if the point between the first line
        if ((this.maxX >= intersectionP.getX())
                && (this.minX <= intersectionP.getX())
                && (this.maxY >= intersectionP.getY())
                && (this.minY <= intersectionP.getY())) {
            if ((other.getMaxX() >= intersectionP.getX())
                    && (other.getMinX() <= intersectionP.getX())
                    && (other.getMaxY() >= intersectionP.getY())
                    && (other.getMinY() <= intersectionP.getY())) {
                return true;
            }
        }
        // if the line are not intersecting.
        return false;
    }

    /**
     * The function uses with the function isIntersecting to
     * get a indication if the lines are intersecting and with
     * the function calculateIntersectionPoint to return
     * the intersection point.
     *
     * @param other the other line for the check.
     * @return the intersection point.
     */
    public Point intersectionWith(Line other) {
        // if the function isIntersecting return true return the intersection point.
        if (isIntersecting(other)) {
            return calculateIntersectionPoint(other);
        } else {
            return null;
        }
    }

    /**
     * The function call the the function intersectionPoints from the
     * object rect, for get a List of intersection Points, and search the
     * closets geometry.Point to the start of the line.
     *
     * @param rect the borders object.
     * @return the closest intersection geometry.Point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List interPoints = rect.intersectionPoints(this);
        // if there is more than 2 intersection points.
        if (interPoints.size() >= 2) {
            Point closetPoint = (Point) interPoints.get(0);
            double minDidtanse = this.start().distance(closetPoint);
            double tempDistanse;
            for (int i = 1; i < interPoints.size(); i++) {
                tempDistanse = this.start().distance((Point) interPoints.get(i));
                // if the current geometry.Point is closets than the prev geometry.Point.
                if (tempDistanse < minDidtanse) {
                    minDidtanse = tempDistanse;
                    closetPoint = (Point) interPoints.get(i);
                }
            }
            return closetPoint;
        } else if (interPoints.size() == 1) {
            return ((Point) interPoints.get(0));
        } else {
            return null;
        }
    }

    /**
     * The function check if two line are equals, and deal with 2 cases:
     * 1. if the start point of the first line are equal to the start point
     * of the other line same for the end point.
     * 2. if the start point of the first line are equal to the
     * end point of the other line same for the end.
     *
     * @param other the other line for the check
     * @return true if the lines are equals otherwise false.
     */
    public boolean equals(Line other) {
        // check id the points are equals
        if ((this.start().equals(other.start())) && (this.end().equals(other.end()))) {
            return true;
        }
        //// check id the points are equals
        if ((this.start().equals(other.end())) && (this.end().equals(other.start()))) {
            return true;
        }
        return false;
    }
}