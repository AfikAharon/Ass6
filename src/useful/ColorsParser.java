package useful;

import java.awt.Color;

/**
 * a class ColorsParser for cast color from string to java color.
 *
 * @author Afik Aharon.
 */
public class ColorsParser {
    /**
     * parse color definition and return the specified color.
     *
     * @param s the string color to cast
     * @return the Color
     */
    public java.awt.Color colorFromString(String s) {
        Color color = null;
        if (s.equals("black")) {
            color = Color.BLACK;
        }
        if (s.equals("blue")) {
            color = Color.BLUE;
        }
        if (s.equals("cyan")) {
            color = Color.CYAN;
        }
        if (s.equals("gray")) {
            color = Color.GRAY;
        }
        if (s.equals("lightGray")) {
            color = Color.lightGray;
        }
        if (s.equals("green")) {
            color = Color.green;
        }
        if (s.equals("orange")) {
            color = Color.orange;
        }
        if (s.equals("pink")) {
            color = Color.pink;
        }
        if (s.equals("red")) {
            color = Color.red;
        }
        if (s.equals("white")) {
            color = Color.white;
        }
        if (s.equals("yellow")) {
            color = Color.yellow;
        }
        return color;
    }
}
