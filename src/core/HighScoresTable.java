package core;

import useful.MagN;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private int sizeTable;
    private List<ScoreInfo> scores;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the table size
     */
    public HighScoresTable(int size) {
        this.sizeTable = size;
        this.scores = new ArrayList<ScoreInfo>();
    }

    /**
     * the function add a high-score and insert the scores sort.
     *
     * @param score the score that need to be add
     */
    public void add(ScoreInfo score) {
        int rank = getRank(score.getScore());
        if (rank > this.scores.size() && rank < this.sizeTable) {
            this.scores.add(score);
        } else {
            this.scores.add(rank - 1, score);
        }
        // if the table size big the original size remove the last score.
        if (this.scores.size() > this.sizeTable) {
            for (int i = this.scores.size() - 1; i > this.sizeTable - 1; i--) {
                this.scores.remove(i);
            }
        }
    }

    /**
     * @return the table size.
     */
    public int size() {
        return this.sizeTable;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     *
     * @param score the given score
     * @return the rank of the score in the table
     */
    public int getRank(int score) {
        if (this.getHighScores().isEmpty()) {
            return 1;
        }
        for (int i = this.scores.size() - 1; i >= 0; i--) {
            if (score < this.scores.get(i).getScore()) {
                return i + 2;
            }
        }
        for (int i = 0; i < this.scores.size(); i++) {
            if (score >= this.scores.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.sizeTable + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores = new ArrayList<ScoreInfo>();
    }

    // Load table data from file.
    // Current table data is cleared.

    /**
     * the function load table data from file.
     * Current table data is cleared.
     *
     * @param fileName the file.
     * @throws IOException if the function failed load the file.
     */
    public void load(File fileName) throws IOException {
        this.clear();
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(fileName));
            HighScoresTable highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.scores = highScoresTable.getHighScores();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + fileName);
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + fileName);
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + fileName);
            }
        }

    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the file for save the information.
     * @throws IOException if the function failed saving in the file.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the file for save the information.
     * @return HighScoresTable object.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable scoresTable = new HighScoresTable(MagN.TABLE_SIZE);
        try {
            scoresTable.load(filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (scoresTable.getHighScores().isEmpty()) {
            return new HighScoresTable(MagN.TABLE_SIZE);
        }
        return scoresTable;
    }
}
