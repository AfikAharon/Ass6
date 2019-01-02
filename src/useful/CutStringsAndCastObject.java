package useful;

import arkanoid.BackgroundColor;
import arkanoid.BackgroundImage;
import construction.BlockDetails;
import core.Background;
import core.BlockCreator;
import core.Velocity;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a CutStringsAndCastObject is in charge of cut strings
 * and to cast string to specify object.
 *
 * @author Afik Aharon.
 */
public class CutStringsAndCastObject {

    /**
     * The function cut a string line with key string and information , check if the
     * line are illegal and return the information string.
     *
     * @param buffer the buffer line to cut
     * @param reg    the regular expression
     * @param cut    the cut string pattern
     * @return if the buffer illegal the information string, if not null.
     */
    public String cutTheString(String buffer, String reg, String cut) {
        String tempS = null;
        Pattern pattern = Pattern.compile(reg);
        Matcher mattcher = pattern.matcher(buffer);
        String[] tempSplit = null;
        if (mattcher.matches()) {
            tempSplit = buffer.split(cut);
            // check if the line is not empty
            pattern = Pattern.compile("[ ]*");
            mattcher = pattern.matcher(tempSplit[1]);
            if (!mattcher.matches()) {
                tempS = tempSplit[1];
            }
        }
        return tempS;
    }

    /**
     * The function get a line with a color string or image string
     * check if the line are illegal cut the name color or the path image
     * and return it.
     *
     * @param buffer the information line
     * @return the string color or image path or null if the string is not illegal
     * @throws Exception if the path image is not illegal.
     */
    public String cutTheColorImage(String buffer) throws Exception {
        boolean flag = true;
        String color = null;
        String tempS = null;
        String[] tempL = null;
        Pattern pattern = Pattern.compile(MagN.RGB_REG);
        Matcher mattcher = pattern.matcher(buffer);
        if (mattcher.matches()) {
            tempL = buffer.split("^color");
            tempL = tempL[1].split("[(]");
            tempL = tempL[2].split("[))]");
            color = tempL[0];
            flag = false;
        }
        if (flag) {
            pattern = Pattern.compile(MagN.COLOR_REG);
            mattcher = pattern.matcher(buffer);
            if (mattcher.matches()) {
                tempL = buffer.split("^color");
                tempL = tempL[1].split("[(]");
                tempL = tempL[1].split("[)]");
                color = tempL[0];
                flag = false;
            }
        }
        if (flag) {
            pattern = Pattern.compile(MagN.IMAGE_REG);
            mattcher = pattern.matcher(buffer);
            if (mattcher.matches()) {
                tempL = buffer.split("^image");
                tempL = tempL[1].split("[(]");
                tempL = tempL[1].split("[)]");
                // check if the image path are illegal
                if (tempL[0].contains("\\") || tempL[0].contains("^")
                        || tempL[0].contains("\"") || tempL[0].contains("<")
                        || tempL[0].contains(">") || tempL[0].contains("?")
                        || tempL[0].contains("*") || tempL[0].contains("|")
                        || tempL[0].contains(":")) {
                    throw new Exception("something wrong with the string path image");
                }
                color = tempL[0];
            }
        }
        return color;
    }

    /**
     * Cut the fill string to the color string or image string.
     *
     * @param buffer the buffer line to cut.
     * @return the color string, null if the line are not illegal
     */
    public String cutThefill(String buffer) {
        String tempS = null;
        String fillNumber = null;
        String[] tempL = null;
        Pattern pattern = Pattern.compile(MagN.FILL_REG_ONE);
        Matcher mattcher = pattern.matcher(buffer);
        try {
            if (mattcher.matches()) {
                tempL = buffer.split("^fill:");
                tempS = cutTheColorImage(tempL[1]);
            }
            pattern = Pattern.compile(MagN.FILL_REG_TWO);
            mattcher = pattern.matcher(buffer);
            if (mattcher.matches()) {
                tempL = buffer.split("^fill-");
                fillNumber = tempL[1].substring(0, 1);
                tempL = tempL[1].split("^\\d+:");
                // Mar the fill number for future use
                tempS = cutTheColorImage(tempL[1]) + MagN.FILL_MARK + fillNumber;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tempS;
    }

    /**
     * the function cut the ball velocity information and.
     * cast th number and return a list of velocity
     *
     * @param buffer the buffer line to cut
     * @return list of velocity
     * @throws Exception if there is a error with the velocity arguments.
     */
    public List<Velocity> cutTheBallsVelocity(String buffer) throws Exception {
        String[] tempL = null;
        Pattern pattern = Pattern.compile("^ball_velocities:.*");
        Matcher mattcher = pattern.matcher(buffer);
        if (mattcher.matches()) {
            List<Velocity> ballVelocities = new ArrayList<Velocity>();
            tempL = buffer.split("ball_velocities:");
            tempL = tempL[1].split(" ");
            String[] strinNum = null;
            for (int j = 0; j < tempL.length; j++) {
                strinNum = tempL[j].split(",");
                try {
                    ballVelocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(strinNum[0]),
                            Double.parseDouble(strinNum[1])));
                } catch (Exception e) {
                    throw new Exception("Error with the Velocity arguments");
                }
            }
            return ballVelocities;
        }
        return null;
    }

    /**
     * the function check if the buffer line are illegal by the
     * regular expression anf cast the strings to number int.
     *
     * @param buffer the buffer line
     * @param reg    the regular Expression to work with.
     * @param cut    the part to cut from the string
     * @return the int number value
     */
    public int castingToNumber(String buffer, String reg, String cut) {
        int spWid = -1;
        String tempS = null;
        tempS = cutTheString(buffer, reg, cut);
        if (tempS != null) {
            spWid = Integer.parseInt(tempS);
        }
        return spWid;
    }

    /**
     * The function cast a string color to Color object.
     *
     * @param buffer the string color
     * @return the color
     */
    public Color castTheColor(String buffer) {
        Color color = null;
        String[] tempL;
        try {
            // check if is a RGB color.
            if (buffer.contains(",")) {
                tempL = buffer.split(",");
                int coloring1 = Integer.parseInt(tempL[0]);
                int coloring2 = Integer.parseInt(tempL[1]);
                int coloring3 = Integer.parseInt(tempL[2]);
                color = new Color(coloring1, coloring2, coloring3);
            } else {
                color = new ColorsParser().colorFromString(buffer);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return color;
    }

    /**
     * The function load a Image.
     *
     * @param path the image path
     * @return the image
     */
    public Image castToImage(String path) {
        Image image = null;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
        return image;
    }

    /**
     * The function get a block line information cut the information by
     * call the correct function and return a tree map with block information.
     *
     * @param infoToSplit the block information to split
     * @param reg         the regular Expression to work with.
     * @param cut         the part to cut from the line.
     * @return the final information that store in Treemap.
     * @throws Exception if the function cutTheColorImage find a error with the image path.
     */
    public TreeMap<String, String> cutTheBlockInformation(String infoToSplit, String reg, String cut) throws Exception {
        TreeMap<String, String> splitString = new TreeMap<>();
        String[] tempL = null;
        String[] temp = null;
        String tempS = null;
        int counterFill = 0;
        Pattern pattern = Pattern.compile(reg);
        Matcher mattcher = pattern.matcher(infoToSplit);
        if (mattcher.matches()) {
            tempL = infoToSplit.split(cut);
            tempL = tempL[1].split(" ");
            for (int j = 0; j < tempL.length; j++) {
                if (tempL[j].matches("[A-Za-z]")) {
                    splitString.put("symbol", tempL[j]);
                } else if (tempL[j].contains("hit_points:")) {
                    tempS = cutTheString(tempL[j], MagN.HIT_POINTS_REG, "^hit_points:");
                    if (tempS != null) {
                        splitString.put("hit_points", tempS);
                    }
                } else if (tempL[j].contains("width:")) {
                    tempS = cutTheString(tempL[j], MagN.WIDTH_REG, "^width:");
                    if (tempS != null) {
                        splitString.put("width", tempS);
                    }
                } else if (tempL[j].contains("height:")) {
                    tempS = cutTheString(tempL[j], MagN.HEGHT_REG, "^height:");
                    if (tempS != null) {
                        splitString.put("height", tempS);
                    }
                } else if (tempL[j].contains("stroke:")) {
                    temp = tempL[j].split("stroke:");
                    tempS = cutTheColorImage(temp[1]);
                    if (tempS != null) {
                        splitString.put("color", tempS);
                    }
                } else if (tempL[j].contains("fill")) {
                    tempS = cutThefill(tempL[j]);
                    if (tempS != null) {
                        counterFill++;
                        splitString.put("fill-" + Integer.toString(counterFill), tempS);
                    }
                }
            }
        }
        return splitString;
    }

    /**
     * The function cut the spacer information to a tree map and return it.
     *
     * @param infoSpacer the info spacer
     * @return tree map with the spacer information
     */
    public TreeMap<String, Integer> cutTheBlockSpacer(List<String> infoSpacer) {
        TreeMap<String, Integer> spacers = new TreeMap<>();
        String tempS = null;
        String tempSpacer = null;
        String[] tempL = null;
        Pattern pattern = Pattern.compile(MagN.SPACER_REG);
        Matcher matcher = null;
        for (int i = 0; i < infoSpacer.size(); i++) {
            tempS = cutTheString(infoSpacer.get(i), MagN.SDEF_REG, "sdef symbol:");
            matcher = pattern.matcher(tempS);
            if (matcher.matches()) {
                tempL = tempS.split(" ");
                tempSpacer = tempL[0];
                tempL = tempL[1].split("width:");
                spacers.put(tempSpacer, Integer.parseInt(tempL[1]));
            }
        }
        return spacers;

    }

    /**
     * The function get a fill string cast to a color or image
     * create a object background and store on a map with the key of the number of hit block.
     *
     * @param backgrounds the backgrounds map to store.
     * @param fillToCast  the fill string to cast
     */
    public void castFill(Map<Integer, Background> backgrounds, String fillToCast) {
        Image img = null;
        Color col = null;
        int counter = 1;
        // check if the fill string is a kind of (color name or image)**number
        if (!fillToCast.contains(MagN.FILL_MARK)) {
            // check if the the fill is a image path
            if (fillToCast.contains(".")) {
                img = castToImage(fillToCast);
                if (img != null) {
                    backgrounds.put(1, new BackgroundImage(img));
                }
            } else {
                col = castTheColor(fillToCast);
                if (col != null) {
                    backgrounds.put(1, new BackgroundColor(col));
                }
            }
        } else {
            String[] tempArr = fillToCast.split(MagN.MARK_FILL_REG);
            while (tempArr[counter].equals("")) {
                counter++;
            }
            // check if the the fill is a image path
            if (tempArr[0].contains(".")) {
                img = castToImage(tempArr[0]);
                if (img != null) {
                    backgrounds.put(Integer.parseInt(tempArr[counter]), new BackgroundImage(img));
                }
            } else {
                col = castTheColor(tempArr[0]);
                if (col != null) {
                    backgrounds.put(Integer.parseInt(tempArr[counter]), new BackgroundColor(col));
                }
            }
        }
    }

    /**
     * The function get a map of string (the key is the block and the default information)
     * and map of string that hold the information block, cast the information bay ball the correct function
     * and return a Map of BlockCreator.
     *
     * @param info the blocks information
     * @return map of BlockCreator.
     * @throws Exception if one of the block details are missing
     */
    public Map<String, BlockCreator> createBlocksCreator(Map<String, Map<String, String>> info) throws Exception {
        BlockDetails tempBlockDe = null;
        CutStringsAndCastObject cutStringsAndCastObject = new CutStringsAndCastObject();
        Map<String, String> defu = info.get("default");
        info.remove("default");
        String symbol = null;
        int height = -1;
        int width = -1;
        Color strokeColor = null;
        int hitPoints = -1;
        Map<String, String> temp = null;
        Map<Integer, Background> backgrounds = new TreeMap<>();
        Map<String, BlockCreator> blocks = new TreeMap<>();
        for (int i = 0; i < info.size(); i++) {
            temp = info.get("block-" + Integer.toString(i + 1));
            // check if the information store in the default information or in the block information
            if (defu != null && defu.containsKey("hit_points")) {
                hitPoints = Integer.parseInt(defu.get("hit_points"));
            } else if (temp != null && temp.containsKey("hit_points")) {
                hitPoints = Integer.parseInt(temp.get("hit_points"));
            }
            if (defu != null && defu.containsKey("width")) {
                width = Integer.parseInt(defu.get("width"));
            } else if (temp != null && temp.containsKey("width")) {
                width = Integer.parseInt(temp.get("width"));
            }
            if (defu != null && defu.containsKey("height")) {
                height = Integer.parseInt(defu.get("height"));
            } else if (temp != null && temp.containsKey("height")) {
                height = Integer.parseInt(temp.get("height"));
            }
            if (defu != null && defu.containsKey("color")) {
                strokeColor = castTheColor(defu.get("color"));
            } else if (temp != null && temp.containsKey("color")) {
                strokeColor = castTheColor(temp.get("color"));
            }
            // cast the fill blocks
            for (int j = 0; j < hitPoints; j++) {
                if (defu != null && defu.containsKey("fill-" + Integer.toString(j + 1))) {
                    castFill(backgrounds, defu.get("fill-" + Integer.toString(j + 1)));
                } else if (temp != null && temp.containsKey("fill-" + Integer.toString(j + 1))) {
                    castFill(backgrounds, temp.get("fill-" + Integer.toString(j + 1)));
                }
            }
            if (defu != null && defu.containsKey("symbol")) {
                symbol = defu.get("symbol");
            } else if (temp != null && temp.containsKey("symbol")) {
                symbol = temp.get("symbol");
            }
            // check if not missing a information block.
            if (height == -1 || width == -1 || hitPoints == -1) {
                throw new Exception("missing height, width or hitPoints value");
            }
            if (backgrounds.isEmpty()) {
                throw new Exception("missing background block");
            }
            if (symbol == null) {
                throw new Exception("missing symbol block");
            }
            // reset the variables for reuse for the next block.
            tempBlockDe = new BlockDetails(backgrounds, strokeColor, hitPoints, height, width);
            blocks.put(symbol, tempBlockDe);
            height = -1;
            width = -1;
            strokeColor = null;
            symbol = null;
            hitPoints = -1;
            backgrounds = new TreeMap<>();
        }
        return blocks;
    }

    /**
     * The function read from file a block information insert the information to
     * a Map of List(string) that hold the block information and spacer information.
     *
     * @param reader the reader object with the file to read
     * @return map of list(String) null if there is a problem with reading.
     */
    public Map<String, List<String>> cutTheBlockDefinitionFile(java.io.Reader reader) {
        Map<String, List<String>> infoBlocks = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> tempList = new ArrayList<>();
            infoBlocks = new TreeMap<>();
            String buffer = bufferedReader.readLine();
            //if the buffer line is default values for blocks
            if (buffer != null && buffer.contains("# default values for blocks")) {
                while (buffer != null) {
                    buffer = bufferedReader.readLine();
                    // if the buffer line arrived to the string block definitions stop
                    if (buffer != null && buffer.contains("# block definitions")) {
                        infoBlocks.put("default", tempList);
                        break;
                    }
                    tempList.add(buffer);
                }
            }
            tempList = new ArrayList<>();
            //if the buffer line is block definitions blocks
            if (buffer != null && buffer.contains("# block definitions")) {
                while (buffer != null) {
                    buffer = bufferedReader.readLine();
                    // if the buffer line arrived to the string spacers definitions stop
                    if (buffer != null && buffer.contains("# spacers definitions")) {
                        infoBlocks.put("block definitions", tempList);
                        break;
                    }
                    tempList.add(buffer);
                }
            }
            tempList = new ArrayList<>();
            //if the buffer line is spacers definitions blocks
            if (buffer != null && buffer.contains("# spacers definitions")) {
                // if buffer equal to null stop reading
                while (buffer != null) {
                    buffer = bufferedReader.readLine();
                    if (buffer == null) {
                        infoBlocks.put("spacers definitions", tempList);
                        break;
                    }
                    tempList.add(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return infoBlocks;
    }
}