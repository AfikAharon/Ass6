package construction;

import arkanoid.BackgroundColor;
import arkanoid.BackgroundImage;
import core.Background;
import core.Velocity;
import levels.BlocksFromSymbolsFactory;
import levels.LevelDetails;
import levels.LevelInformation;
import useful.CutStringsAndCastObject;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader in charger of read the level file.
 *
 * @author Afik Aharon.
 */
public class LevelSpecificationReader {

    /**
     * The function split the level file and call the function createLevel
     * to create level.
     *
     * @param reader the reader level file
     * @return list of levelInformation object.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<LevelInformation> levels = new ArrayList<>();
        List<List<String>> levelsInfo = new ArrayList<>();
        // split the file
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                List<String> tempLevelsInfo = new ArrayList<>();
                while (line != null && !line.equals("END_LEVEL")) {
                    line = bufferedReader.readLine();
                    if (line != null && !(line.equals(""))) {
                        tempLevelsInfo.add(line);
                    }
                }
                if (!tempLevelsInfo.isEmpty()) {
                    levelsInfo.add(tempLevelsInfo);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < levelsInfo.size(); i++) {
            LevelInformation tempLevel;
            try {
                tempLevel = createLevel(levelsInfo.get(i));
                levels.add(tempLevel);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return levels;
    }

    /**
     * The function create level information object,
     * by cast the information from levelInfo list.
     *
     * @param levelInfo the level info
     * @return the level information
     * @throws Exception the exception
     */
    public LevelInformation createLevel(List<String> levelInfo) throws Exception {
        LevelDetails levelDetails = null;
        // a object for cast the information
        CutStringsAndCastObject cutStringsAndCastObject = new CutStringsAndCastObject();
        String tempS = null;
        String levelName = null;
        String blockDefinitions = null;
        List<Velocity> ballVelocities = null;
        Image img = null;
        Color color = null;
        int paddleSpeed = -1;
        int paddleWidth = -1;
        int blockStartX = -1;
        int blockStartY = -1;
        int rowHeight = -1;
        int numBlocks = -1;
        List<String> blocksPosition = null;
        for (int i = 0; i < levelInfo.size(); i++) {
            tempS = levelInfo.get(i);
            if (tempS.contains("level_name:")) {
                levelName = cutStringsAndCastObject.cutTheString(tempS, "^level_name:.*", "level_name:");
            }
            if (tempS.contains("ball_velocities:")) {
                ballVelocities = cutStringsAndCastObject.cutTheBallsVelocity(tempS);
            }
            if (tempS.contains("background:")) {
                if (tempS.contains("image")) {
                    img = cutTheBackgroundImage(tempS);
                } else {
                    color = castTheBackgroundColor(tempS);
                }
            }
            if (tempS.contains("block_definitions:")) {
                blockDefinitions = cutTheBlocksDefinitions(tempS);
            }
            if (tempS.contains("blocks_start_x:")) {
                blockStartX = cutStringsAndCastObject.castingToNumber(tempS,
                        "^blocks_start_x:\\d+", "^blocks_start_x:");
            }
            if (tempS.contains("blocks_start_y:")) {
                blockStartY = cutStringsAndCastObject.castingToNumber(tempS,
                        "^blocks_start_y:\\d+", "^blocks_start_y:");
            }
            if (tempS.contains("row_height:")) {
                rowHeight = cutStringsAndCastObject.castingToNumber(tempS,
                        "^row_height:\\d+", "row_height:");
            }
            if (tempS.contains("num_blocks:")) {
                numBlocks = cutStringsAndCastObject.castingToNumber(tempS,
                        "^num_blocks:\\d+", "num_blocks:");
            }
            if (tempS.contains("paddle_speed:")) {
                paddleSpeed = cutStringsAndCastObject.castingToNumber(tempS,
                        "^paddle_speed:\\d+", "paddle_speed:");
            }
            if (tempS.contains("paddle_width:")) {
                paddleWidth = cutStringsAndCastObject.castingToNumber(tempS,
                        "^paddle_width:\\d+", "paddle_width:");
            }
            if (tempS.contains("START_BLOCKS")) {
                blocksPosition = cutTheBlocksPosition(levelInfo, i);
                break;
            }
        }
        // check if all the argument not have the initialize value if not throws exception
        if (blocksPosition == null) {
            throw new Exception("something wrong with the blocks position");
        }
        if (levelName == null) {
            throw new Exception("something wrong with the levelDetails name");
        }
        if (blockDefinitions == null) {
            throw new Exception("something wrong with the block definitions");
        }
        if (ballVelocities == null) {
            throw new Exception("something wrong with the ball velocities");
        }
        if (img == null && color == null) {
            throw new Exception("something wrong with the background");
        }
        if (paddleSpeed == -1 || paddleWidth == -1) {
            throw new Exception("something wrong with the paddle speed or width");
        }
        if (blockStartX == -1 || blockStartY == -1) {
            throw new Exception("something wrong with the block start");
        }
        if (rowHeight == -1 || numBlocks == -1) {
            throw new Exception("something wrong with the row height or num blocks");
        }
        try {
            Background background = null;
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions);
            Reader fileReader = new InputStreamReader(is);
            // get the BlocksFromSymbolsFactory for input to the level object.
            BlocksFromSymbolsFactory blocksaInfo = BlocksDefinitionReader.fromReader(fileReader);
            if (color != null) {
                background = new BackgroundColor(color);
            } else if (img != null) {
                background = new BackgroundImage(img);
            }
            levelDetails = new LevelDetails(levelName, paddleSpeed, paddleWidth, rowHeight, numBlocks, background);
            levelDetails.setBlocksInfo(blocksaInfo);
            levelDetails.setBallsVelocity(ballVelocities);
            levelDetails.setBlocksPosition(blocksPosition);
            levelDetails.setStartBlocksPosition(blockStartX, blockStartY);
        } catch (NullPointerException e) {
            System.out.println("something's wrong with the block reader");
        }
        return levelDetails;
    }

    /**
     * Cut the blocks definitions string.
     *
     * @param buffer the buffer line
     * @return the string for cut the path
     * @throws Exception if the path is not illegal.
     */
    public String cutTheBlocksDefinitions(String buffer) throws Exception {
        String path = null;
        path = new CutStringsAndCastObject().cutTheString(buffer,
                "^block_definitions:.*.txt", "block_definitions:");
        if (path != null) {
            if (path.contains("\\") || path.contains("^")
                    || path.contains("\"") || path.contains("<")
                    || path.contains(">") || path.contains("?")
                    || path.contains("*") || path.contains("|")
                    || path.contains(":")) {
                throw new Exception("something wrong with the string path");
            }
        }
        return path;
    }

    /**
     * Cut the background image by  call the function cutTheColorImage.
     *
     * @param buffer the buffer line
     * @return the background image
     * @throws Exception if the path image is not illegal cutTheColorImage throws exception.
     */
    public Image cutTheBackgroundImage(String buffer) throws Exception {
        Image img = null;
        CutStringsAndCastObject cutStringsAndCastObject = new CutStringsAndCastObject();
        String imgPath = null;
        imgPath = cutStringsAndCastObject.cutTheString(buffer, "^background:.*", "^background:");
        if (imgPath != null) {
            imgPath = cutStringsAndCastObject.cutTheColorImage(imgPath);
        }
        img = cutStringsAndCastObject.castToImage(imgPath);
        return img;
    }

    /**
     * Cut the background color by  call the function cutTheColorImage.
     *
     * @param buffer the buffer line
     * @return the background color
     * @throws Exception if the path image is not illegal cutTheColorImage throws exception.
     */
    public Color castTheBackgroundColor(String buffer) throws Exception {
        String colorString = null;
        Color color = null;
        CutStringsAndCastObject cutStringsAndCastObject = new CutStringsAndCastObject();
        colorString = cutStringsAndCastObject.cutTheString(buffer, "^background:.*",
                "^background:");
        colorString = cutStringsAndCastObject.cutTheColorImage(colorString);
        if (colorString != null) {
            color = cutStringsAndCastObject.castTheColor(colorString);
        }
        return color;
    }

    /**
     * Cut the blocks position list.
     *
     * @param levelInfo the level information list
     * @param index     the index of the blocks Position information string start in the list
     * @return the blocks Position string.
     */
    public List<String> cutTheBlocksPosition(List<String> levelInfo, int index) {
        List<String> blocksPostion = new ArrayList<>();
        for (int i = index; i < levelInfo.size(); i++) {
            // the end of the strings block position int the list.
            if (levelInfo.get(i).equals("END_BLOCKS")) {
                break;
            }
            // the start of the strings block position int the list.
            if (!(levelInfo.get(i).contains("START_BLOCKS") || levelInfo.get(i).equals(""))) {
                blocksPostion.add(levelInfo.get(i));
            }
        }
        if (blocksPostion.isEmpty()) {
            return null;
        }
        return blocksPostion;
    }
}
