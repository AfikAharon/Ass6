package core;

/**
 * a Counter class.
 *
 * @author Afik Aharon.
 */
public class Counter {
    private int counter;

    /**
     * Constructor for Counter class.
     *
     * @param num the starting num count.
     */
    public Counter(int num) {
        this.counter = num;
    }

    /**
     * The function add number to current count.
     *
     * @param number the amount of increase.
     */
    public void increase(int number) {
        this.counter = this.counter + number;
    }

    /**
     * The function subtract number from current count.
     *
     * @param number the amount of decrease.
     */
    public void decrease(int number) {
        this.counter = this.counter - number;
    }

    /**
     * gets current count.
     *
     * @return the value counter.
     */
    public int getValue() {
        return this.counter;
    }
}