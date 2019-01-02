package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The class is a decorator pattern for the
 * pause high scores and end  screen game animation.
 *
 * @author Afik Aharon.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.animation = animation;
        this.key = key;
        this.sensor = sensor;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     *
     * The function call the function doOneFrame of the animation member,
     * and check if the user press the keyboard.
     *
     * @param d the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else if (!this.sensor.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * Sets stop.
     */
    public void setStop() {
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * Sets animation.
     *
     * @param anim the animation
     */
    public void setAnimation(Animation anim) {
        this.animation = anim;
    }

    /**
     * The function is in charge of stopping condition animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() { return this.stop; }
}
