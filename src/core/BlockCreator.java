package core;

import arkanoid.Block;

/**
 * The interface Block creator.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xPos the x pos
     * @param yPos the y pos
     * @return the block
     */
    Block create(int xPos, int yPos);
}
