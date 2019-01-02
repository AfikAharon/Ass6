package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import core.Counter;
import useful.MagN;

import java.awt.Color;

/**
 * a CountdownAnimation class.
 * <p>
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 * <p>
 *
 * @author Afik Aharon.
 */
public class CountdownAnimation implements Animation {
    private Counter countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private long pauseFor;
    private boolean flag;
    private boolean stopDraw;

    /**
     * Constructor for CountdownAnimation class.
     *
     * @param numOfSeconds num of seconds of the count.
     * @param countFrom    the number that the count start
     * @param gameScreen   the screen game
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = new Counter(countFrom);
        this.gameScreen = gameScreen;
        this.pauseFor = (long) (1000 * numOfSeconds / countFrom);
        this.stop = false;
        this.flag = false;
        this.stopDraw = true;
    }

    /**
     * The function is in charge for the countDown animation (count from a given number)
     * every call the function decrease the counter, sleep the screen for num of seconds
     * and if the countDown arrived to 0 change the the boolean member to true for indication
     * that the countDown finish.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        long presentTime = System.currentTimeMillis();
        long stopTime = presentTime + this.pauseFor;
        this.gameScreen.drawAllOn(d);
        if (countFrom.getValue() < 0) {
            this.stop = true;
            // stop the draw
            this.stopDraw = false;
        }
        while ((presentTime < stopTime) && flag) {
            presentTime = System.currentTimeMillis();
        }
        // draw while stopDraw equal to true
        if (stopDraw) {
            if (this.countFrom.getValue() > 0) {
                d.setColor(Color.BLACK);
                d.drawText(d.getWidth() / 2 + 1, d.getHeight() / 2 + 1,
                        Integer.toString(countFrom.getValue()), MagN.SIZE_COUNT_TEXT);
                d.setColor(Color.decode("#8FD098"));
                d.drawText(d.getWidth() / 2, d.getHeight() / 2,
                        Integer.toString(countFrom.getValue()), MagN.SIZE_COUNT_TEXT);
            } else {
                d.setColor(Color.BLACK);
                d.drawText(d.getWidth() / 2 - 15 + 1, d.getHeight() / 2 + 1, "GO", MagN.SIZE_COUNT_TEXT);
                d.setColor(Color.decode("#8FD098"));
                d.drawText(d.getWidth() / 2 - 15, d.getHeight() / 2, "GO", MagN.SIZE_COUNT_TEXT);
            }
            this.countFrom.decrease(1);
        }
        // change the boolean member to get in the sleeper loop just from the second call.
        flag = true;
    }

    /**
     * The function is in charge of stopping condition animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}