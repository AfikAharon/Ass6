package useful;

/**
 * a MagN class.
 *
 * class for magic numbers for easy reading code.
 *
 * @author Afik Aharon.
 */
public class MagN {
    public static final int FRAME_RATE = 60;
    public static final int GUI_WIDTH = 800;
    public static final int GUI_HEIGHT = 600;
    public static final int WID_HEI_BLOCKS = 25;
    public static final int PADDLE_START_POSITION_X = 400;
    public static final int PADDLE_START_POSITION_Y =  570;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_START_POSITION_X = 400;
    public static final int BALL_START_POSITION_Y =  560;
    public static final int BALL_RADIUS = 5;
    public static final int POSITION_SCORE_MESSAGE_X = 320;
    public static final int POSITION_LEVEL_MESSAGE_X = 520;
    public static final int POSITION_LIVES_MESSAGE_X = 50;
    public static final int POSITION_MESSAGE_Y = 15;
    public static final int ALL_BALLS_REMOVED_POINTS = 100;
    public static final int SIZE_INFO_TEXT = 15;
    public static final int SIZE_COUNT_TEXT = 50;
    public static final int SIZE_SCREEN_TEXT = 35;
    public static final int REMOVED_HIT_POINTS = 10;
    public static final int HIT_POINTS = 5;
    public static final int TABLE_SIZE = 10;
    public static final String LOST_MESSAGE = "Game Over. Your score is ";
    public static final String SUB_MENU_TITLE = "Level Set";
    public static final String WIN_MESSAGE = "You Win! Your score is ";
    public static final String PAUSE_MESSAGE = "paused -- press space to continue";
    public static final String LIVES_MESSAGE = "Lives: ";
    public static final String SCORE_MESSAGE = "Score: ";
    public static final String LEVEL_MESSAGE = "Level Name: ";
    public static final String PRESS_BUTTON_STRING = "space";
    public static final String GAME_NAME = "Arkanoid";
    public static final String LEVEL_SETS_PATH = "level_sets.txt";
    public static final String HIGH_SCORES_PATH = "highscores.txt";
    public static final String HIGH_SCORES_IMAGE_PATH = "background_images/scores.jpg";
    public static final String MENU_IMAGE_PATH = "background_images/menu.jpg";
    public static final String SUB_MENU_IMAGE_PATH = "background_images/submenu.jpg";
    public static final String END_SCREEN_IMAGE_PATH = "background_images/endscreen.jpg";
    public static final String PAUSE_SCREEN_IMAGE_PATH = "background_images/pause.jpg";
    public static final String DIALOG_MESSAGE_ONE = "Name";
    public static final String DIALOG_MESSAGE_TOW = "What is your name?";
    public static final String SPACER_KEY = "spacers definitions";
    public static final String BLOCK_DEFINITIONS_KEY = "block definitions";
    public static final String BLOCK_REG = "^bdef symbol:.*";
    public static final String BLOCK_CUT = "bdef symbol:";
    public static final String DEFAULT_KEY = "default";
    public static final String DEFAULT_REG = "^default.*";
    public static final String DEFAULT_CUT = "default ";
    public static final String RGB_REG = "^color[(]RGB[(]\\d+,\\d+,\\d+[)][)]$";
    public static final String COLOR_REG = "^color[(].*[)]$";
    public static final String IMAGE_REG = "^image[(].*(.png|.jpg)[)]$";
    public static final String FILL_REG_ONE = "^fill:.*";
    public static final String FILL_REG_TWO = "^fill-\\d+:.*";
    public static final String FILL_MARK = "***";
    public static final String HIT_POINTS_REG = "^hit_points:\\d+";
    public static final String HEGHT_REG = "^height:\\d+";
    public static final String WIDTH_REG = "^width:\\d+";
    public static final String SPACER_REG = "(\\W|_) width:\\d+";
    public static final String SDEF_REG = "^sdef symbol:.*";
    public static final String MARK_FILL_REG = "[***]";
}
