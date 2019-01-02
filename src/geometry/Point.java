package geometry;

/**
 * a Point class.
 *
 * @author Afik Aharon.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor for the point class.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The function calculate the distance between two point's.
     *
     * @param other the other line
     * @return the distance between the two point's
     */
    public double distance(Point other) {
        double temp;
        temp = Math.pow((other.x - this.x), 2) + Math.pow((other.y - this.y), 2);
        return Math.sqrt(temp);
    }

    /**
     * The function check if two points are equal.
     *
     * @param other other point
     * @return true if the point's are equal otherwise false
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        if ((this.x == other.getX()) && (this.y == other.getY())) {
            return true;
        }
        return false;
    }

    /**
     * the function return the member value.
     *
     * @return the member x value
     */
    public double getX() {
        return this.x;
    }

    /**
     * the function return the member value.
     *
     * @return the member y value
     */
    public double getY() {
        return this.y;
    }
}