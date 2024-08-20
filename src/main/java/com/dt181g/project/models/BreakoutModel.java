package com.dt181g.project.models;

import com.dt181g.project.models.ball.factories.EasyBallFactory;
import com.dt181g.project.models.ball.factories.HardBallFactory;
import com.dt181g.project.models.ball.factories.MediumBallFactory;
import com.dt181g.project.observation.Observer;
import com.dt181g.project.models.ball.BallModel;
import com.dt181g.project.models.bricks.BrickField;
import com.dt181g.project.models.paddle.factories.EasyPaddleFactory;
import com.dt181g.project.models.paddle.factories.HardPaddleFactory;
import com.dt181g.project.models.paddle.factories.MediumPaddleFactory;
import com.dt181g.project.models.paddle.PaddleModel;
import com.dt181g.project.models.powerup.BasePowerup;
import com.dt181g.project.models.powerup.objpool.PowerupPool;
import com.dt181g.project.concurrentprocessing.Producer;
import com.dt181g.project.observation.Subject;
import com.dt181g.project.concurrentprocessing.ThreadPoolManager;
import com.dt181g.project.support.Appconfig;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The {@code BreakoutModel} class represents the core model of the Breakout game.
 * It manages the game's state, including the game objects, scores, power-ups, and
 * interactions between different objects of the game.
 * @author Daniel Jönsson
 * @version 1.0
 * @see Subject
 */
public final class BreakoutModel implements Subject{
    /**
     * Instance field variables
     */
    private final int height;
    private final int width;
    private BallModel ball;
    private boolean isGameStopped = false;
    private boolean isGameActive = false;
    private PaddleModel paddle;
    private final PowerupPool powerupPool;

    private final AtomicInteger score;

    private final ArrayList<Observer> observers;
    private int elapsedTime;
    private long breakoutTimer;
    private final BlockingQueue<BasePowerup> activePowerups;
    private final ThreadPoolManager threadPoolManager;
    private final BrickField brickField;

    /**
     * Constructs a {@code BreakoutModel} with the specified game
     * dimensions and game difficulty.
     * @param screenDim The screen dimensions for the game.
     * @param difficulty The game's difficulty level (0: Easy, 1: Medium, 2:
     *                   Hard(More like impossible atm)).
     */
    public BreakoutModel(final Dimension screenDim, final int difficulty){
        this.height = screenDim.height;
        this.width = screenDim.width;
        this.powerupPool = new PowerupPool(this);
        this.observers = new ArrayList<>();
        this.activePowerups = new ArrayBlockingQueue<>(Appconfig.BLOCK_AMOUNT);
        this.threadPoolManager = new ThreadPoolManager();
        this.brickField = new BrickField();
        this.score = new AtomicInteger(0);
        switch (difficulty){
            case 0 -> initEasyGameObjects();
            case 1 -> initMediumGameObjects();
            case 2 -> initHardGameObjects();
        }
        setInitGameState();
    }

    /**
     * Starts the game timer, marking the game as active.
     */
    public void startGameTimer(){
        this.breakoutTimer = System.currentTimeMillis();
        this.isGameActive = !isGameActive;
    }

    /**
     * Checks the game timer and updates the elapsed time. Notifies observers
     * about the elapsed time change.
     */
    public void checkGameTimer(){
        if (breakoutTimer != 0){
            int currentTime = (int) (System.currentTimeMillis() - breakoutTimer)/1000;
            if (elapsedTime != currentTime){
                elapsedTime = currentTime;
                notifyObserver();
            }
        }
    }

    /**
     * Gets the elapsed time since the game started.
     * @return The elapsed time in seconds.
     */
    public int getElapsedTime(){
        return elapsedTime;
    }

    /**
     * Sets the game state to its starting state, positions the paddle and
     * checks  powerup-supply.
     */
    public void setInitGameState() {
        checkPowerupSupply();
        this.paddle.setPos(new Point(getWidth()/2,
                getHeight()-paddle.getBounds().y));
    }

    /**
     * Initializes game objects for an easy difficulty level.
     */
    private void initEasyGameObjects(){
        EasyBallFactory easyBallFactory = new EasyBallFactory(this);
        EasyPaddleFactory easyPaddleFactory = new EasyPaddleFactory(this);
        this.ball = easyBallFactory.createBall();
        this.paddle = easyPaddleFactory.createPaddle();
    }

    /**
     * Initializes game objects for a medium difficulty level.
     */
    private void initMediumGameObjects(){
        MediumBallFactory mediumBallFactory = new MediumBallFactory(this);
        MediumPaddleFactory mediumPaddleFactory = new MediumPaddleFactory(this);
        this.ball = mediumBallFactory.createBall();
        this.paddle = mediumPaddleFactory.createPaddle();
    }

    /**
     * Initializes game objects for a hard difficulty level.
     */
    private void initHardGameObjects(){
        HardBallFactory hardBallFactory = new HardBallFactory(this);
        HardPaddleFactory hardPaddleFactory = new HardPaddleFactory(this);
        this.ball = hardBallFactory.createBall();
        this.paddle = hardPaddleFactory.createPaddle();
    }

    /**
     * Checks whether the game is lost (e.g., the ball is below the game
     * bounds).
     * @return True if the game is lost, otherwise false.
     */
    public boolean isGameLost() {
        return this.ball.getPos().y > (getHeight() + ball.getDim().height);
    }

    /**
     * Checks whether the game is won (e.g., all bricks are destroyed).
     * @return True if the game is won, otherwise false.
     */
    public boolean isGameWon(){
        return this.brickField.getTotalAmountBricks() == 0;
    }

    /**
     * Retrieves the current game score.
     * @return The current game score.
     */
    public AtomicInteger getScore(){
        return score;
    }

    /**
     * Resets the game score to zero. May be used in future development where
     * users can restart the game.
     */
    public void resetScore() {
        this.score.set(0);
    }

    /**
     * Adds points to the game score.
     * @param amount The number of points to add to the score.
     */
    public void addScore(final int amount){
        this.score.addAndGet(amount);
    }

    /**
     * Updates the game state by moving game objects (e.g., ball, powerups and
     * paddle) as well as checking the game timer and powerup pool supply.
     */
    public void updateGameState(){
        checkPowerupSupply();
        ball.move();
        paddle.movePaddle();
        movePowerups();
        checkGameTimer();
    }

    /**
     * Move each powerup in the game and returns them to the pool if the
     * powerup is not caught and travels below the screen. If the powerup is
     * caught it should be removed from the list as active powerups in the game.
     */
    private void movePowerups() {
        Iterator<BasePowerup> iterator = activePowerups.iterator();
        while (iterator.hasNext()) {
            BasePowerup powerup = iterator.next();
            powerup.move();
            if (powerup.isShouldRemove()) {
                iterator.remove();
                returnPowerup(powerup);
            } else if (powerup.isShouldActivate()) {
                iterator.remove();
            }
        }
    }

    /**
     * Checks the current supply of powerups in the powerup pool and starts
     * production of new powerups if the pool dwindles. This
     * method ensures that the powerup pool maintains a minimum number of
     * powerups. If the supply is below the minimum, it schedules producer
     * tasks to add new powerups to the pool. The method utilizes
     * synchronization to avoid race conditions.
     * */
    private void checkPowerupSupply() {
        synchronized (powerupPool.getPowerupPool()) {
            while (powerupPool.getPowerupPool().size() < 10) {
                threadPoolManager.execute(new Producer(powerupPool));
                try {
                    powerupPool.getPowerupPool().wait(); // Releases lock, waits
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Returns the height of the game screen.
     * @return The height of the game screen.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the game screen.
     * @return The width of the game screen.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the brick field containing all bricks in the game.
     * @return The brick field.
     */
    public BrickField getBrickField() {
        return brickField;
    }

    /**
     * Returns the games ball object.
     * @return The ball object.
     */
    public BallModel getBall() {
        return ball;
    }

    /**
     * Returns the active powerups in the game.
     *
     * @return The active powerups.
     */
    public List<Rectangle> getActivePowerups() {
        return activePowerups.stream()
                .map(BasePowerup::getBounds)
                .collect(Collectors.toList());
    }

    public List<Color> getActivePowerupsColor() {
        return activePowerups.stream()
                .map(BasePowerup::getColor)
                .collect(Collectors.toList());
    }
    /**
     * Adds an active power-up to the game.
     * @param powerup The active power-up to add.
     * @throws InterruptedException If interrupted while adding the power-up.
     */
    public void addPowerup(final BasePowerup powerup) throws InterruptedException {
        synchronized (activePowerups){
            if (powerup != null){
                this.activePowerups.put(powerup);
            }
        }
    }

    /**
     * Returns the games paddle object.
     * @return The paddle object.
     */
    public PaddleModel getPaddle() {
        return paddle;
    }

    /**
     * Pauses or resumes the game by toggling the game state.
     */
    public void pauseGame(){
        isGameStopped = !isGameStopped;
    }

    /**
     * Checks if the game is stopped (paused).
     * @return True if the game is paused, otherwise false.
     */
    public boolean isGameStopped() {
        return isGameStopped;
    }

    /**
     * Returns the powerup pool used in the game.
     * @return The powerup pool.
     */
    public PowerupPool getPowerupPool() {
        return powerupPool;
    }

    /**
     * Returns the thread pool manager used for executing game tasks
     * concurrently.
     * @return The thread pool manager.
     */
    public ThreadPoolManager getThreadPoolManager() {
        return threadPoolManager;
    }

    /**
     * Returns a powerup back to the powerup pool.
     *
     * @param powerup The powerup to return.
     */
    public void returnPowerup(final BasePowerup powerup){
        powerup.resetState();
        powerupPool.returnPowerup(powerup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Observer observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObserver() {
        for (Observer observer : observers){
            observer.update();
        }
    }

    /**
     * Checks if the game is active
     * @return true if the game is running, else false
     */
    public boolean isGameActive() {
        return isGameActive;
    }
}
