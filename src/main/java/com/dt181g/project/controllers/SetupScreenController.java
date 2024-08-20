package com.dt181g.project.controllers;

import com.dt181g.project.listeners.SetupScreenListener;
import com.dt181g.project.models.SetupModel;
import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.Util;
import com.dt181g.project.support.sound.Sound;
import com.dt181g.project.support.sound.SoundHandler;
import com.dt181g.project.views.SetupScreen;
import java.awt.Color;
import java.awt.Container;


/**
 * The {@code SetupScreenController} is used to manage the setup screen in
 * the game. It initializes a model as well as a view, it checks for changes
 * in the view and adjusts data in the model accordingly. It also manages the
 * start of the game and constructs a {@link BreakoutController}.
 *
 * @author Daniel Jönsson
 * @version 1.0
 * @see BreakoutController
 * @see SetupModel
 * @see SetupScreen
 */
public class SetupScreenController implements SetupScreenListener {

    /**
     * Instance field variables for view and model
     */
    private final SetupScreen setupScreen;
    private final SetupModel setupModel;

    /**
     * Constructs a new {@code SetupScreenController} and instantiates the
     * view/model for the setup screen. It also starts playing a sound and
     * changing the background color.
     */
    public SetupScreenController() {
        this.setupScreen = new SetupScreen();
        this.setupModel = new SetupModel();
        setupScreen.addListener(this);
        playSound();
        changeBackgroundColor();
    }

    /**
     * Initiates the game with the selected difficulty when the start button
     * is clicked.
     */
    private void startGame() {
        if (setupScreen.getSelectedDifficultyButton() != null){
            int difficulty = -1;
            String selectedButtonText = setupScreen.getSelectedDifficultyButton().getText();
            switch (selectedButtonText) {
                case "Easy" -> difficulty = 0;
                case "Medium" -> difficulty = 1;
                case "Impossible" -> difficulty = 2;
            }
            if (difficulty >= 0){
                new BreakoutController(difficulty, setupScreen.getjSlider().getValue()/100f);
                SoundHandler.stopSound(Sound.SETUPSOUND);
                setupScreen.dispose();
                setupModel.setGameStarted(true);
            }
        }
    }

    /**
     * Used for playing sounds in the game, creates a new thread that
     */
    private void playSound(){
        setupModel.setStoredVolume(setupScreen.getjSlider().getValue());
        float storedVolume = setupModel.getStoredVolume();
        SoundHandler.playSound(Sound.SETUPSOUND, true, storedVolume/100f);
        Thread volumeThread = new Thread(() ->{
            while (!Thread.currentThread().isInterrupted() && !setupModel.isGameStarted()){
                if (setupModel.getStoredVolume() != setupScreen.getjSlider().getValue()){
                    SoundHandler.changeVolume(Sound.SETUPSOUND,
                            setupModel.getStoredVolume()/100f);
                    setupModel.setStoredVolume(setupScreen.getjSlider().getValue());
                }
            }
        });
        volumeThread.start();
    }

    /**
     * Creates a new Thread and uses lambda expression to construct a
     * Runnable task which changes the background color every two seconds by
     * utilizing the {@code generateRandomColor} method inside the
     * {@link Util} enum.
     */
    private void changeBackgroundColor(){
        Thread backgroundThread = new Thread(() ->{
            try {
                while (!Thread.currentThread().isInterrupted() && !setupModel.isGameStarted()){
                    Container container = setupScreen.getContentPane();
                    Color color = Util.INSTANCE.generateRandomColor(Appconfig.COLOR_LUMINANCE, Appconfig.COLOR_SATURATION);
                    container.setBackground(color);
                    setupScreen.getButtonPanel().setBackground(color);
                    setupScreen.getAgreeButton().setBackground(color);
                    setupScreen.getRadioButton().setBackground(color);
                    setupScreen.getjSlider().setBackground(color);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        backgroundThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        startGame();
    }
}
