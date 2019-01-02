package core;

/**
 * The interface Hit notifier.
 *
 * @author Afik Aharon.
 */
public interface HitNotifier {
    /**
     * the function add hl as a listener to hit events.
     *
     * @param hl HitListener object.
     */
    void addHitListener(HitListener hl);

    /**
     * The function removed hl from the list of listeners to hit events.
     *
     * @param hl HitListener object.
     */
    void removeHitListener(HitListener hl);
}