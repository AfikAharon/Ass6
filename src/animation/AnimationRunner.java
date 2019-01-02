package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * a AnimationRunner class.
 * The class is in charge to run the animation lop.
 *
 * @author Afik Aharon.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructor for AnimationRunner class.
     *
     * @param g               the given gui
     * @param s               the given sleeper
     * @param framesPerSecond the given framesPerSecond.
     */
    public AnimationRunner(GUI g, Sleeper s, int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
        this.gui = g;
        this.sleeper = s;
    }


    /**
     * The function is in charge of run the animation lop.
     *
     * @param animation the given animation class for run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, 1.0 / this.framesPerSecond);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Gets frames per second.
     *
     * @return the frames per second
     */
    public int getFramesPerSecond() {
        return this.framesPerSecond;
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    public GUI getGui() {
        return this.gui;
    }
}