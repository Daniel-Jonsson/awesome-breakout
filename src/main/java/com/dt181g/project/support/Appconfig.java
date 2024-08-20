package com.dt181g.project.support;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Color;

/**
 * The {@code Appconfig} class provides various configuration constants and
 * attributes for the Breakout game. It stores constants related to the game.
 * @author Daniel Jönsson
 */
public final class Appconfig {

    private Appconfig() {
        throw new IllegalStateException("Utility class");
    }

    /* -------------------------
       BREAKOUTVIEW ATTRIBUTES
    ------------------------- */

    /** The frame size of the game window. */
    public static final Dimension FRAME_SIZE = new Dimension(1280, 620);

    /** The title of the Breakout game. */
    public static final String GAME_TITLE = "EPIC BREAKOUT";

    /* -------------------------
       SETUP SCREEN ATTRIBUTES
    ------------------------- */

    /** The font for the game title. */
    public static final Font TITLE_FONT = new Font("STSong", Font.BOLD, 48);

    /** The font for buttons in the setup screen. */
    public static final Font BUTTON_FONT = new Font("Ubuntu", Font.BOLD, 22);

    /** Easy button text. */
    public static final String EASY_BUTTON_TEXT = "Easy";

    /** Medium button text. */
    public static final String MEDIUM_BUTTON_TEXT = "Medium";

    /** Impossible button text. */
    public static final String IMPOSSIBLE_BUTTON_TEXT = "Impossible";

    /** Checkbox text. */
    public static final String CHECKBOX_TEXT = "I like hotdogs!";

    /** Start button text. */
    public static final String START_BUTTON_TEXT = "Start Game";

    /** Game control button text. */
    public static final String GAME_CONTROL_BUTTON_TEXT = "Game Controls";

    /* -------------------------
      SPLASH SCREEN ATTRIBUTES
    ------------------------- */

    /** Small text font. **/
    public static final Font SMALL_TEXT = new Font("Arial Bold", Font.ITALIC,
            15);

    /** Big text font. **/
    public static final Font BIG_TEXT = new Font("Arial Bold", Font.BOLD, 25);

    /** Percent text font. **/
    public static final Font PERCENT_TEXT = new Font("Arial", Font.ITALIC, 30);

    /** Welcome text. **/
    public static final String WELCOME_TEXT = "Welcome to";

    /** General info text. **/
    public static final String GENERAL_INFO_TEXT = "version 1.0    DT181G(HT23)    Daniel Jönsson";

    /** Loading text. **/
    public static final String LOADING_TEXT = "loading.....";

    /** JWindow dimension. **/
    public static final Dimension WINDOW_DIMENSION = new Dimension(550, 300);

    /** JWindow position. **/
    public static final Point WINDOW_POSITION = new Point(500, 285);

    /* -------------------------
      INFO PANEL ATTRIBUTES
    ------------------------- */

    /** Score text. **/
    public static final String SCORE_TEXT = "Score: ";

    /** Time text. **/
    public static final String TIME_TEXT = "Time: ";

    /** Blocks left text. **/
    public static final String BLOCK_TEXT = "Block left: ";

    /* -------------------------
        EASY PADDLE ATTRIBUTES
    ------------------------- */

    /** The offset for the paddle's initial position. */
    public static final Point PADDLE_POS_OFFSET = new Point(25, 50);

    /** The size of the easy paddle. */
    public static final Dimension EASY_PADDLE_SIZE = new Dimension(140, 6);

    /**
     * The movement speed of the easy paddle.
     */
    public static final int EASY_PADDLE_SPEED = 7;

    /** The color of the easy paddle. */
    public static final Color PADDLE_COLOR = new Color(189, 151, 164);

    /* -------------------------
        MEDIUM PADDLE ATTRIBUTES
    ------------------------- */

    /** The size of the medium paddle. */
    public static final Dimension MEDIUM_PADDLE_SIZE = new Dimension(100, 6);

    /**
     * The movement speed of the medium paddle.
     */
    public static final int MEDIUM_PADDLE_SPEED = 7;

    /* -------------------------
        HARD PADDLE ATTRIBUTES
    ------------------------- */

    /** The size of the hard paddle. */
    public static final Dimension HARD_PADDLE_SIZE = new Dimension(120, 6);

    /**
     * The movement speed of the hard paddle.
     */
    public static final int HARD_PADDLE_SPEED = 9;

    /* -------------------------
        EASY BALL ATTRIBUTES
    ------------------------- */

    /** The size of the easy ball. */
    public static final Dimension EASY_BALL_SIZE = new Dimension(30, 30);

    /** The initial position of the ball. */
    public static final Point BALL_INIT_POS = new Point(400, 400);

    /**
     * The movement speed of the easy ball.
     */
    public static final int EASY_BALL_SPEED = 3;

    /** The color of the easy ball. */
    public static final Color EASY_BALL_COLOR = new Color(0, 191, 255);

    /* -------------------------
        MEDIUM BALL ATTRIBUTES
    ------------------------- */

    /** The size of the medium ball. */
    public static final Dimension MEDIUM_BALL_SIZE = new Dimension(30, 30);

    /**
     * The movement speed of the medium ball.
     */
    public static final int MEDIUM_BALL_SPEED = 5;

    public static final Color MEDIUM_BALL_COLOR = new Color(0, 0, 139);

    /* -------------------------
        HARD BALL ATTRIBUTES
    ------------------------- */

    /** The size of the hard ball. */
    public static final Dimension HARD_BALL_SIZE = new Dimension(30, 30);

    /**
     * The movement speed of the hard ball.
     */
    public static final int HARD_BALL_SPEED = 7;

    public static final Color HARD_BALL_COLOR = new Color(32, 20, 205);

    /* -------------------------
        POWERUP ATTRIBUTES
    ------------------------- */

    /** The size of power-ups. */
    public static final Dimension POWERUP_SIZE = new Dimension(10, 10);

    /**
     * The movement speed of power-ups.
     */
    public static final int POWERUP_SPEED = 1;

    /** The chance of an easy power-up spawning. */
    public static final int EASY_POWERUP_CHANCE = 40;

    /** The chance of a medium power-up spawning. */
    public static final int MEDIUM_POWERUP_CHANCE = 30;

    /** The chance of a hard power-up spawning. */
    public static final int HARD_POWERUP_CHANCE = 20;

    /* -------------------------
        BRICK ATTRIBUTES
    ------------------------- */

    /** The starting position for bricks. */
    public static final Point BLOCK_POS_START = new Point(20, 15);

    /** The spacing between brick positions. */
    public static final Point BLOCK_POS_SPACING = new Point(20, 60);

    /** The total number of bricks in the game. */
    public static final int BLOCK_AMOUNT = 84;

    /** The size of each brick. */
    public static final Dimension BLOCK_SIZE = new Dimension(40, 40);

    /** The luminance of brick colors. */
    public static final float COLOR_LUMINANCE = 0.75f;

    /** The saturation of brick colors. */
    public static final float COLOR_SATURATION = 110.0f;


    /* --------------------
         GAME TICK
    -------------------- */

    /** The delay in Milliseconds representing a tick in the game. */
    public static final int GAME_TICK_MS = 16;

    /* --------------------
         GENERAL INFO
    -------------------- */

    /** The banner text displayed when the game is won. */
    public static final String WON_BANNER = "Game Won!";

    /** The minimum size of threads in the thread pool. */
    public static final int THREAD_POOL_MIN_SIZE = 4;

    /** The maximum size of threads in the thread pool. */
    public static final int THREAD_POOL_MAX_SIZE = 8;

    /** The banner text displayed when the game is lost. */
    public static final String LOST_BANNER = "Game Lost!";

    /** Information text for the game, including controls and objectives. */
    public static final String GAME_INFO = """
            Game Controls:
             - Use 'A/Left arrow' and 'D/Right arrow' to move the paddle left and right.
             - Press 'Space' to start the game.
             - Press 'P' to pause/resume the game.
            General Information:
             - Power-ups may spawn when a brick is destroyed; try to catch them!
             Game Objective:
             - Destroy all bricks as soon as possible!""";

    /** The duration of power-up effects in seconds. */
    public static final int EFFECT_TIME_SECONDS = 7;

    /** The font for the labels. */
    public static final Font LABLE_FONT = new Font("STSong", Font.BOLD, 26);
}
