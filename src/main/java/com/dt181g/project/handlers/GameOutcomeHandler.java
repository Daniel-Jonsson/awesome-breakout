package com.dt181g.project.handlers;

/**
 * A template method class for handling game outcomes that involve displaying
 * messages and playing sounds when a game is won/lost. Subclasses should
 * implement the specific behavior for displaying messages and playing sounds.
 * @author Daniel Jönsson
 */
public abstract class GameOutcomeHandler {

    /**
     * Plays the speicifc outcome sound
     */
    public void handleGameSound(){
        playSound();
    }

    /**
     * Returns the String to be displayed
     * @return The String to display
     */
    public String handleGameMessage(){
        return displayMessage();
    }

    /**
     * Handles the game outcome by showing a String.
     * Concrete outcome classes implements this method to specify the specific
     * String to be shown.
     * @return The String to display
     */
    protected abstract String displayMessage();

    /**
     * Handles the game outcome by playing a sound.
     * Concrete outcome classes implements this method to specify the specific
     * sound to be played.
     */
    protected abstract void playSound();
}
