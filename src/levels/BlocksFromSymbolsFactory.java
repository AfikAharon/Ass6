package levels;

import arkanoid.Block;
import core.BlockCreator;

import java.util.Map;

/**
 * a BlocksFromSymbolsFactory class.
 *
 * @author Afik Aharon.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     *
     * @param s    the s
     * @param xPos the blok x axis position
     * @param yPos the blok y axis position
     * @return the block
     */
    public Block getBlock(String s, int xPos, int yPos) {
        return this.blockCreators.get(s).create(xPos, yPos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s the spacer character
     * @return the spacer value
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
