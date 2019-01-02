package arkanoid;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import core.Sprite;
import useful.MagN;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a MenuAnimation. Class
 *
 * @param <T> the type parameter
 * @author Afik Aharon.
 */
public class MenuAnimation<T> implements Menu<T> {
    private boolean stop;
    private String menuTitle;
    private KeyboardSensor sensor;
    private T status;
    private List<String> keys;
    private List<T> returnValues;
    private List<String> messages;
    private Sprite background;
    private Map<String, String> subMenuName;
    private Map<String, Menu<T>> subMenu;

    /**
     * Instantiates a new Menu animation.
     *
     * @param menuTitle the menu title
     * @param sensor    the keyboard sensor
     */
    public MenuAnimation(String menuTitle, KeyboardSensor sensor) {
        this.menuTitle = menuTitle;
        this.sensor = sensor;
        this.stop = false;
        this.returnValues = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.background = null;
        this.subMenu = new TreeMap<>();
        this.subMenuName = new TreeMap<>();
    }

    /**
     * Sets background.
     *
     * @param backg the background class
     */
    public void setBackground(Sprite backg) {
        this.background = backg;
    }

    /**
     * the function draw thw menu animation on the given DrawSurface.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // check if the background is a color or image.
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(Color.decode("#E8B7B7"));
            d.fillRectangle(0, 0, MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        }
        d.setColor(Color.BLACK);
        d.drawText(101, 81, this.menuTitle, 35);
        d.setColor(Color.decode("#FFFF00"));
        d.drawText(100, 80, this.menuTitle, 35);
        int moveDown = 0;
        // draw the ket option and the name option
        for (int i = 0; i < this.keys.size(); i++) {
            d.setColor(Color.BLACK);
            d.drawText(171, 151 + moveDown, "(" + this.keys.get(i) + ") " + this.messages.get(i), 25);
            d.setColor(Color.decode("#60ABF5"));
            d.drawText(170, 150 + moveDown, "(" + this.keys.get(i) + ") " + this.messages.get(i), 25);
            moveDown += 35;
        }
        // check if the user press on one of the keys
        for (int i = 0; i < this.keys.size(); i++) {
            if (sensor.isPressed(this.keys.get(i))) {
                this.status = this.returnValues.get(i);
                this.stop = true;
            }
        }
    }

    /**
     * The function add selection to the member maps.
     *
     * @param key       the key object
     * @param message   the message
     * @param returnVal the return val
     */
    public void addSelection(String key, String message, T returnVal) {
        this.messages.add(message);
        this.keys.add(key);
        this.returnValues.add(returnVal);
    }

    /**
     * The function return the current status.
     *
     * @return the current status after the user pressing
     */
    public T getStatus() {
        // when we call this function the animation stop so we sets the animation indication.
        this.stop = false;
        return this.status;
    }

    /**
     * Sets menu animation.
     */
    public void setMenuAnimation() {
        this.stop = false;
        this.status = null;
    }

    /**
     * The function is in charge of stopping condition animation lop.
     *
     * @return boolean true for stop the animation lop and false for continue.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * The function add a sub menu.
     *
     * @param key     the key for the object sub menu
     * @param mess the title of the sub menu
     * @param subM the sub menu
     */
    public void addSubMenu(String key, String mess, Menu<T> subM) {
        this.subMenuName.put(key, mess);
        this.subMenu.put(key, subM);
    }

    /**
     * Gets sub menu.
     *
     * @param key the key
     * @return the sub menu
     */
    public Menu<T> getSubMenu(String key) {
        return this.subMenu.get(key);
    }

    /**
     * Gets sub menu name.
     *
     * @param key the key
     * @return the sub menu name
     */
    public String getSubMenuName(String key) {
        return this.subMenuName.get(key);
    }
}
