package com.dt181g.project.controllers;

import com.dt181g.project.handlers.GameOutcomeHandler;
import com.dt181g.project.listeners.ViewListener;
import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.observation.Observer;
import com.dt181g.project.support.Appconfig;
import com.dt181g.project.handlers.GameLostHandler;
import com.dt181g.project.handlers.GameWonHandler;
import com.dt181g.project.support.sound.Sound;
import com.dt181g.project.support.sound.SoundHandler;
import com.dt181g.project.views.BreakoutView;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * The {@code BreakoutController} class is responsible for controlling the
 * game logic and user interactions in the Breakout game.
 * @author Daniel Jönsson
 * @version 1.1
 * @see KeyListener
 * @see ActionListener
 * @see Observer
 */
public final class BreakoutController implements ActionListener,
        Observer, ViewListener {

    /**
     * Instance Field Variables.
     */
    private final BreakoutModel model;
    private final BreakoutView view;
    private final Timer actionTimer;

    /**
     * Constructs a new Breakout controller with the specified game difficulty.
     *
     * @param difficulty The difficulty level of the game (0 for easy, 1 for
     *                   medium, 2 for hard(impossible) ).
     * @param musicVol The music volume set by the user (defaults as 50%)
     */
    public BreakoutController(final int difficulty, final float musicVol) {
        this.model = new BreakoutModel(Appconfig.FRAME_SIZE, difficulty);
        this.view = new BreakoutView(Appconfig.FRAME_SIZE);
        this.actionTimer = new Timer(Appconfig.GAME_TICK_MS, this);
        this.view.setViewListener(this);
        view.updatePowerups(model.getActivePowerups());
        view.updateBall(model.getBall().getBounds());
        view.updatePaddle(model.getPaddle().getBounds());
        setObjectColors();
        model.addObserver(this);
        actionTimer.start();
        SoundHandler.setVolumeForSounds(musicVol);
        model.getThreadPoolManager().executeSound(()->{
            try {
                Thread.sleep(500);
                SoundHandler.playSound(Sound.SOUNDTRACK, true);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        view.updateScore(model.getScore().get());
        view.updateTime(model.getElapsedTime());
        view.updateBrickAmount(model.getBrickField().getTotalAmountBricks());
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if(!model.isGameStopped()){
            if (model.isGameWon()){
                gameWon();
            } else if (model.isGameLost()) {
                gameLost();
            }
            model.updateGameState();
            view.updateBall(model.getBall().getBounds());
            view.updatePaddle(model.getPaddle().getBounds());
            view.updateBricks(model.getBrickField().getRenderableBricks());
            view.setBrickColors(model.getBrickField().getRenderableBricksColor());
            view.updatePowerups(model.getActivePowerups());
            view.setPowerupcolors(model.getActivePowerupsColor());
            view.repaintPanels();
        }
    }

    /**
     * Used when game is won, stops the game from updating and removes
     * observer. The idea is to continue developing this game so a
     * TO-DO notation is in place to add more functionality later on.
     */
    private void gameWon(){
        this.actionTimer.stop();
        this.model.removeObserver(this);
        GameWonHandler gameWonHandler = new GameWonHandler();
        gameWonHandler.handleGameSound();
        view.displayOutcomeMessage(gameWonHandler.handleGameMessage());
    }

    /**
     * Used when game is lost, stops the game from updating and removes
     * observer. Also creates a new {@link GameWonHandler} which incorporates
     * the template methods from
     * {@link GameOutcomeHandler}.
     */
    private void gameLost(){
        this.actionTimer.stop();
        this.model.removeObserver(this);
        GameLostHandler gameLostHandler = new GameLostHandler();
        gameLostHandler.handleGameSound();
        view.displayOutcomeMessage(gameLostHandler.handleGameMessage());
    }

    private void setObjectColors() {
        view.setBallColor(model.getBall().getColor());
        view.setPaddleColor(model.getPaddle().getColor());
        view.setBrickColors(model.getBrickField().getRenderableBricksColor());
    }

    @Override
    public void onLeftPressed() {
        model.getPaddle().setXVelocity(-model.getPaddle().getPaddleSpeed());
    }

    @Override
    public void onRightPressed() {
        model.getPaddle().setXVelocity(model.getPaddle().getPaddleSpeed());
    }

    @Override
    public void stopPaddleMovement() {
        model.getPaddle().setXVelocity(0);
    }

    @Override
    public void onSpacePressed() {
        if(!model.isGameActive()) {
            model.startGameTimer();
            model.getBall().startBallMovement();
        }
    }

    @Override
    public void onPausePressed() {
        model.pauseGame();
    }
}
