package construction;

import arkanoid.Block;
import core.Background;
import core.BlockCreator;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.Map;

/**
 * The class store the Block details and create a blocks.
 *
 * @author Afik Aharon.
 */
public class BlockDetails implements BlockCreator {
    private int height;
    private int width;
    private Color strokeColor;
    private Map<Integer, Background> backgrounds;
    private int hitPoints;

    /**
     * Constructor for BlockDetails class.
     *
     * @param backgorunds the backgorunds
     * @param strokeColor the stroke color
     * @param hitPoints   the hit points
     * @param height      the height
     * @param width       the width
     */
    public BlockDetails(Map<Integer, Background> backgorunds, Color strokeColor, int hitPoints, int height, int width) {
        this.strokeColor = strokeColor;
        this.backgrounds = backgorunds;
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
    }

    /**
     * The function create a block object.
     *
     * @param xPos the x position of the block in c axis
     * @param yPos the t position of the block in c axis
     * @return Block object
     */
    public Block create(int xPos, int yPos) {
        Rectangle rectangle = new Rectangle(new Point(xPos, yPos), width, height);
        return new Block(rectangle, this.backgrounds, this.strokeColor, this.hitPoints);
    }
}
