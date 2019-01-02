package levels;

import arkanoid.Block;
import biuoop.DrawSurface;
import core.Background;
import core.Counter;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;
import useful.MagN;

import java.util.ArrayList;
import java.util.List;

/**
 * a LevelDetails in charge of hold the information level.
 *
 * @author Afik Aharon.
 */
public class LevelDetails implements LevelInformation {
    private String levelName;
    private int paddleSpeed;
    private int paddleWidth;
    private int rowHeight;
    private int startBlocksX;
    private int startBlocksY;
    private List<String> blocksPosition;
    private List<Velocity> ballsVelocity;
    private Counter numberOfBlocksToRemove;
    private BlocksFromSymbolsFactory blocksInfo;
    private Background background;

    /**
     * Instantiates a new Level details.
     *
     * @param levelName   the level name
     * @param paddleSpeed the paddle speed
     * @param paddleWidth the paddle width
     * @param rowHeight   the row height
     * @param numBlocks   the num blocks
     * @param background  the background
     */
    public LevelDetails(String levelName, int paddleSpeed, int paddleWidth, int rowHeight,
                        int numBlocks, Background background) {
        this.levelName = levelName;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.rowHeight = rowHeight;
        this.numberOfBlocksToRemove = new Counter(numBlocks);
        this.ballsVelocity = null;
        this.blocksPosition = null;
        this.blocksInfo = null;
        this.background = background;
    }

    /**
     * Sets blocks info.
     *
     * @param blocksInf the blocks info
     */
    public void setBlocksInfo(BlocksFromSymbolsFactory blocksInf) {
        this.blocksInfo = blocksInf;
    }

    /**
     * Sets start blocks position.
     *
     * @param x the x position
     * @param y the y position
     */
    public void setStartBlocksPosition(int x, int y) {
        this.startBlocksX = x;
        this.startBlocksY = y;
    }

    /**
     * Sets balls velocity.
     *
     * @param velocities the balls velocitie
     */
    public void setBallsVelocity(List<Velocity> velocities) {
        this.ballsVelocity = velocities;
    }

    /**
     * Sets blocks position.
     *
     * @param position the blocks position
     */
    public void setBlocksPosition(List<String> position) {
        this.blocksPosition = position;
    }

    /**
     * @return the amount of the level balls.
     */
    public int numberOfBalls() {
        return this.ballsVelocity.size();
    }

    /**
     * @return the balls velocity list.
     */
    public List<Velocity> initialBallVelocities() {
        return this.ballsVelocity;
    }

    /**
     * @return the paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return the paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return the level name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * The function create a inner class of Sprite and return it.
     *
     * @return a sprite object.
     */
    public Sprite getBackground() {
        Background bagro = this.background;
        Rectangle rectangle = new Rectangle(new Point(0, 0), MagN.GUI_WIDTH, MagN.GUI_HEIGHT);
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                bagro.drawOn(d, rectangle);
            }

            public void timePassed(double dt) {
            }
        };
    }

    /**
     * The function create a blocks list and return it.
     * by use the block position list.
     *
     * @return a list of blocks game.
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        String[] tempS = null;
        Block tempBlock = null;
        int spaceWidth = 0;
        int spaceHeight = 0;
        for (int i = 0; i < this.blocksPosition.size(); i++) {
            tempS = this.blocksPosition.get(i).split("");
            for (int j = 0; j < tempS.length; j++) {
                if (blocksInfo.isBlockSymbol(tempS[j])) {
                    tempBlock = blocksInfo.getBlock(tempS[j], this.startBlocksX + spaceWidth,
                            this.startBlocksY + spaceHeight);
                    spaceWidth += tempBlock.getBlockWidth();
                    blocks.add(tempBlock);
                } else if (blocksInfo.isSpaceSymbol(tempS[j])) {
                    spaceWidth += blocksInfo.getSpaceWidth(tempS[j]);
                }
            }
            spaceWidth = 0;
            spaceHeight += this.rowHeight;
        }
        return blocks;
    }

    /**
     * @return the amount of blocks game.
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove.getValue();
    }

    /**
     * The function create a Counter of the number of the blocks game.
     *
     * @return Counter object.
     */
    public Counter getCounterOfNumberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
}
