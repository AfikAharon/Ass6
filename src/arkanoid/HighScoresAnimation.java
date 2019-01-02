package arkanoid;

import animation.Animation;
import biuoop.DrawSurface;
import core.HighScoresTable;
import core.Sprite;
import useful.MagN;

import java.awt.Color;

/**
 * a HighScoresAnimation class that in charge of draw the high score table.
 *
 * @author Afik Aharon.
 */
public class HighScoresAnimation implements Animation {
    private boolean stop;
    private HighScoresTable scores;
    private String endKey;
    private Sprite background;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     * @param endKey the end key
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey) {
        this.scores = scores;
        this.endKey = endKey;
        this.stop = false;
        this.background = null;
    }

    /**
     * The function draw the high score table.
     *
     * @param d  the given DrawSurface.
     * @param dt It specifies the amount of seconds passed since the last cal
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(Color.decode("#E8B7B7"));
            d.fillRectangle(0, 0, MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        }
        d.setColor(Color.BLACK);
        d.drawText(101, 81, "High Scores", 35);
        d.setColor(Color.decode("#FCFF90"));
        d.drawText(100, 80, "High Scores", 35);
        d.setColor(Color.BLACK);
        d.drawText(141, 551, "Press " + this.endKey + " to continue", 35);
        d.setColor(Color.decode("#A3FA83"));
        d.drawText(140, 550, "Press " + this.endKey + " to continue", 35);
        d.setColor(Color.decode("#95CAE8"));
        d.fillRectangle(100, 150, 450, 3);
        d.setColor(Color.BLACK);
        d.drawLine(100, 150, 550, 150);
        d.drawLine(100, 153, 550, 153);
        d.setColor(Color.BLACK);
        d.drawText(101, 141, "Player", 30);
        d.setColor(Color.decode("#909FFF"));
        d.drawText(100, 140, "Player", 30);
        d.setColor(Color.BLACK);
        d.drawText(451, 141, "Score", 30);
        d.setColor(Color.decode("#909FFF"));
        d.drawText(450, 140, "Score", 30);
        int moveDown = 0;
        for (int i = 0; i < this.scores.getHighScores().size(); i++) {
            d.setColor(Color.BLACK);
            d.drawText(101, 181 + moveDown, this.scores.getHighScores().get(i).getName(), 25);
            d.drawText(451, 181 + moveDown, Integer.toString(this.scores.getHighScores().get(i).getScore()), 25);
            d.setColor(Color.decode("#90E2FF"));
            d.drawText(100, 180 + moveDown, this.scores.getHighScores().get(i).getName(), 25);
            d.drawText(450, 180 + moveDown, Integer.toString(this.scores.getHighScores().get(i).getScore()), 25);
            moveDown += 35;
        }
    }

    /**
     * Sets background.
     *
     * @param backg the background
     */
    public void setBackground(Sprite backg) {
        this.background = backg;
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
