package com.dt181g.project.models;

/**
 * The {@code SetupModel} class represents the model for the game setup- It
 * stores values such as the game volume and also contains a boolean value to
 * check if the game is started. It uses the default constructor.
 *
 * @author Daniel Jönsson
 * @version 1.0
 */
public class SetupModel {

    /**
     * Instance field variables
     */
    private float storedVolume = 0;
    private boolean isGameStarted = false;

    /**
     * Getter method for checking if the game is started or not.
     * @return The boolean value of isGameStarted
     */
    public boolean isGameStarted() {
        return isGameStarted;
    }

    /**
     * Gets the stored music volume
     * @return The current music volume
     */
    public float getStoredVolume() {
        return storedVolume;
    }

    /**
     * Sets the current music volume
     * @param storedVolume The float value to store as current volume
     */
    public void setStoredVolume(float storedVolume) {
        this.storedVolume = storedVolume;
    }

    /**
     * Sets the game to started
     * @param gameStarted The boolean value of a started game(true)
     */
    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }
}
