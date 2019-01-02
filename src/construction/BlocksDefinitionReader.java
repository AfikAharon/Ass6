package construction;

import core.BlockCreator;
import levels.BlocksFromSymbolsFactory;
import useful.CutStringsAndCastObject;
import useful.MagN;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a Blocks definition reader class.
 *
 * @author Afik Aharon.
 */
public class BlocksDefinitionReader {

    /**
     * The function create BlocksFromSymbolsFactory object,
     * call the function cutTheBlockDefinitionFile to split the file.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        CutStringsAndCastObject cutStrings = new CutStringsAndCastObject();
        // split the file
        Map<String, List<String>> infoBlocks = cutStrings.cutTheBlockDefinitionFile(reader);
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
        Map<String, Map<String, String>> finalInfo = new TreeMap<>();
        Map<String, String> temp;
        Map<String, Integer> spacers = null;
        Map<String, BlockCreator> blocks = null;
        if (infoBlocks.containsKey(MagN.DEFAULT_KEY)) {
            try {
                // cut the defualt information block to the final info list
                temp = cutStrings.cutTheBlockInformation(infoBlocks.get(MagN.DEFAULT_KEY).get(0),
                        MagN.DEFAULT_REG, MagN.DEFAULT_CUT);
                finalInfo.put(MagN.DEFAULT_KEY, temp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (infoBlocks.containsKey(MagN.BLOCK_DEFINITIONS_KEY)) {
            for (int i = 0; i < infoBlocks.get(MagN.BLOCK_DEFINITIONS_KEY).size(); i++) {
                try {
                    // cut the information block to the final info List
                    temp = cutStrings.cutTheBlockInformation(infoBlocks.get(MagN.BLOCK_DEFINITIONS_KEY).get(i),
                            MagN.BLOCK_REG, MagN.BLOCK_CUT);
                    if (!temp.isEmpty()) {
                        finalInfo.put("block-" + Integer.toString(i + 1), temp);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (infoBlocks.containsKey(MagN.SPACER_KEY)) {
            spacers = cutStrings.cutTheBlockSpacer(infoBlocks.get(MagN.SPACER_KEY));
        }
        try {
            // create the blocks creator
            blocks = cutStrings.createBlocksCreator(finalInfo);
            blocksFromSymbolsFactory = new BlocksFromSymbolsFactory(spacers, blocks);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blocksFromSymbolsFactory;
    }
}
