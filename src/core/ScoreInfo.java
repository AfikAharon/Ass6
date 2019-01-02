package core;

import java.io.Serializable;

/**
 * a ScoreInfo class.
 *
 * @author Afik Aharon.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets name score.
     *
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the score value.
     *
     * @return the score value.
     */
    public int getScore() {
        return this.score;
    }
}
