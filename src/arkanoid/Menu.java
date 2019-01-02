package arkanoid;

import animation.Animation;

/**
 * The interface Menu.
 *
 * @author Afik Aharon.
 *
 * @param <T> the type parameter
 */
public interface Menu<T> extends Animation {
    /**
     * Add selection to the map member that hold a object of kind T.
     *
     * @param key       the key object
     * @param message   the message
     * @param returnVal the return val
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Gets status object.
     *
     * @return the status object
     */
    T getStatus();

    /**
     * Add sub menu.
     *
     * @param key     the key for the object sub menu
     * @param message the title of th sub menu
     * @param subMenu the sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}
