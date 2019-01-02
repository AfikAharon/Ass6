package construction;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a Level sets reader class that in charge of store the information level
 * sets file.
 *
 * @author Afik Aharon.
 */
public class LevelSetsReader {
    private Map<String, String> levelPath;
    private Map<String, String> setsName;
    private List<String> symbols;

    /**
     * Instantiates a new Level sets reader.
     */
    public LevelSetsReader() {
        this.levelPath = new TreeMap<>();
        this.setsName = new TreeMap<>();
        this.symbols = new ArrayList<>();
    }

    /**
     * Read sets file.
     *
     * @param reader the reader
     */
    public void readSetsFile(Reader reader) {
        String symbol = null;
        String tempString = null;
        String[] tempList = null;
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        try {
            while ((tempString = lineNumberReader.readLine()) != null) {
                if (tempString.length() != 0) {
                    if (lineNumberReader.getLineNumber() % 2 == 0) {
                        this.levelPath.put(symbol, tempString);
                    } else {
                        tempList = tempString.split(":");
                        this.symbols.add(tempList[0]);
                        this.setsName.put(tempList[0], tempList[1]);
                        symbol = tempList[0];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets symbols.
     *
     * @return the symbols
     */
    public List<String> getSymbols() {
        return symbols;
    }

    /**
     * Gets level path.
     *
     * @return the level path
     */
    public Map<String, String> getLevelPath() {
        return levelPath;
    }

    /**
     * Gets sets name.
     *
     * @return the sets name
     */
    public Map<String, String> getSetsName() {
        return setsName;
    }
}
