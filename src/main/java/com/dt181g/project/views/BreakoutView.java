package com.dt181g.project.views;

import com.dt181g.project.listeners.ViewListener;
import com.dt181g.project.support.Appconfig;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code BreakoutView} class represents the main game view in the
 * Breakout game. It extends the {@link JFrame} and contains and
 * {@link GamePanel} as well as an {@link InfoPanel} to display the game
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class BreakoutView extends JFrame implements KeyListener {

    /**
     * Instance field variables
     */
    private final Dimension gameDim;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;

    private List<Rectangle> bricks;
    private List<Rectangle> powerups;
    private final List<ViewListener> viewListeners = new LinkedList<>();
    private Rectangle ball;
    private Rectangle paddle;
    private Color paddleColor;
    private Color ballColor;
    private List<Color> brickColors;
    private List<Color> powerupcolors;


    /**
     * Constructs a new {@code BreakoutView} with the specified game dimension.
     * @param gameDim The dimension of the game area.
     */
    public BreakoutView(final Dimension gameDim){
        this.gameDim = gameDim;
        this.bricks = new ArrayList<>();
        initUI();
    }

    /**
     * Initializes the components, frame configuration,
     * game panels, and layout.
     */
    private void initUI(){
        configFrame();
        initializePanels();
        buildFrame();
        this.gamePanel.addKeyListener(this);
    }

    /**
     * Configures the frame settings, such as resizing, default close operation,
     * and title.
     */
    private void configFrame() {
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(Appconfig.GAME_TITLE);
    }

    /**
     * Initializes the game panels and sets the focus on the game panel.
     */
    private void initializePanels(){
        this.infoPanel = new InfoPanel();
        this.gamePanel = new GamePanel(this);
        this.gamePanel.setFocusable(true);
        this.gamePanel.requestFocus();
    }

    /**
     * Builds the frame by adding game and information panels and displaying it.
     */
    private void buildFrame(){
        this.add(this.gamePanel, BorderLayout.NORTH);
        this.add(this.infoPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Updates the bricks in the model. This method is used by controller to
     * allow separation of concerns between model-view.
     * @param bricks The renderable bricks in the game(not null)
     */
    public void updateBricks(final List<Rectangle> bricks){
        this.bricks = bricks;
    }

    /**
     * Updates the ball object, used to get updates on position as well as size.
     * @param ball The BallModel to update
     */
    public void updateBall(final Rectangle ball){
        this.ball = ball;
    }

    /**
     * Updates the paddle object, used to get updates on position as well as
     * size.
     * @param paddle The PaddleModel to update
     */
    public void updatePaddle(final Rectangle paddle){
        this.paddle = paddle;
    }

    /**1
     * Updates all active powerups in the game that should be rendered onto
     * the view.
     * @param powerups The powerups currently active in the model.
     */
    public void updatePowerups(final List<Rectangle> powerups){
        this.powerups = powerups;
    }

    /**
     * Updates the game timer from the model to display the correct elapsed
     * time in view.
     * @param elapsedTime The elapsed time since the game started.
     */
    public void updateTime(final int elapsedTime){
        this.infoPanel.setTimeElapsed(String.valueOf(elapsedTime));
    }

    /**
     * Updates the score from the model to display the correct score in view.
     * @param score The score to update
     */
    public void updateScore(final int score){
        this.infoPanel.setScore(String.valueOf(score));
    }

    /**
     * Updates the current amount of bricks (excluding broken bricks)
     * @param brickAmount The amount of "alive" bricks left
     */
    public void updateBrickAmount(final int brickAmount){
        this.infoPanel.setBlocksLeft(String.valueOf(brickAmount));
    }

    public void setBallColor(Color ballColor) {
        this.ballColor = ballColor;
    }

    public void setBrickColors(final List<Color> brickColors) {
        this.brickColors = brickColors;
    }

    public void setPaddleColor(final Color paddleColor) {
        this.paddleColor = paddleColor;
    }

    public void setPowerupcolors(final List<Color> powerupcolors) {
        this.powerupcolors = powerupcolors;
    }

    /**
     * Gets the paddle used in the game, used by {@link GamePanel} to display
     * the paddle
     * @return The current paddle used in game
     */
    public Rectangle getPaddle() {
        return paddle;
    }

    /**
     * Gets the ball used in game, used by {@link GamePanel} to display the ball
     *
     * @return The current ball used in game
     */
    public Rectangle getBall() {
        return ball;
    }

    /**
     * Gets all active powerups in game, it is used by {@link GamePanel} to
     * display all active powerups.
     * @return The active powerups in game
     */
    public List<Rectangle> getPowerups(){
        return powerups;
    }

    /**
     * Gets the renderable bricks in the game, it is used by
     * {@link GamePanel} to display the renderable bricks.
     * @return The bricks renderable
     */
    public List<Rectangle> getBricks() {
        return bricks;
    }

    /**
     * Gets the dimension of the game, used by {@link GamePanel} in order to
     * set its preferable size.
     * @return The game Dimension
     */
    public Dimension getGameDim(){
        return gameDim;
    }

    /**
     * Repaint the {@link GamePanel}, this method is used by controller after
     * each game-tick to correctly display each object in the game.
     */
    public void repaintPanels(){
        this.gamePanel.repaint();
    }

    /**
     * Displays the game outcome message, this is used by the
     * {@link GamePanel} to display a message on screen if a user wins/loses
     * a game.
     * @param message The outcome message to display.
     */
    public void displayOutcomeMessage(final String message) {
        gamePanel.setOutcomeLabel(message);
    }

    public Color getPaddleColor() {
        return this.paddleColor;
    }

    public List<Color> getBrickColors() {
        return this.brickColors;
    }

    public List<Color> getPowerupcolors() {
        return this.powerupcolors;
    }

    public Color getBallColor() {
        return this.ballColor;
    }

    private void movePaddleLeft() {
        for (ViewListener viewListener : viewListeners) {
            viewListener.onLeftPressed();
        }
    }

    private void movePaddleRight() {
        for (ViewListener viewListener : viewListeners) {
            viewListener.onRightPressed();
        }
    }

    private void onPause() {
        for (ViewListener viewListener : viewListeners) {
            viewListener.onPausePressed();
        }
    }

    private void onGameStart() {
        for (ViewListener viewListener : viewListeners) {
            viewListener.onSpacePressed();
        }
    }

    private void onStopPaddle() {
        for (ViewListener viewListener : viewListeners) {
            viewListener.stopPaddleMovement();
        }
    }

    public void setViewListener(ViewListener listener) {
        this.viewListeners.add(listener);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(final KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                movePaddleLeft();
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                movePaddleRight();
            }
            case KeyEvent.VK_SPACE -> {
                onGameStart();
            }
            case KeyEvent.VK_P -> onPause();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT, KeyEvent.VK_D,
                    KeyEvent.VK_RIGHT ->  onStopPaddle();
        }
    }
}
