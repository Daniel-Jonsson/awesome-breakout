package com.dt181g.project.controllers;

import com.dt181g.project.listeners.SplashScreenListener;
import com.dt181g.project.views.SplashScreen;

import javax.swing.SwingUtilities;

/**
 * The {@code SplashScreenController} is responsible for managing the {@link SplashScreen}
 * It initializes the view and implements a special interface used to notify
 * the controller that the SplashScreen component is done.
 *
 * @author Daniel Jönsson
 * @version 1.0
 * @see SplashScreenListener
 * @see SplashScreen
 */
public class SplashScreenController implements SplashScreenListener {
    /**
     * Instance field variable
     */
    private final SplashScreen splashScreen;

    /**
     * Constructs a new {@code SplashScreenController} and instatiates a new
     * {@link SplashScreen} while also adding itself as a listener to the
     * SplashScreen.
     */
    public SplashScreenController() {
        this.splashScreen = new SplashScreen();
        initSplashScreen();
    }

    /**
     * Adds the controller as a listener for change in the {@link SplashScreen} views
     * class
     */
    private void initSplashScreen() {
        splashScreen.addSplashScreenListener(this);
    }

    /**
     * Invoked once the {@link SplashScreen} finishes, constructs a new
     * {@link SetupScreenController}
     */
    @Override
    public void onComplete() {
        SwingUtilities.invokeLater(SetupScreenController::new);
    }
}
