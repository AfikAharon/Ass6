package core;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 *
 * @author Afik Aharon.
 */
public interface Task<T> {
    /**
     * Run the task.
     *
     * @return the T type.
     */
    T run();
}
