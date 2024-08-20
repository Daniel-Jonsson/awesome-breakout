package com.dt181g.project.models.powerup;

/**
 * The {@code IPowerup} interface defines the methods that a powerup in the
 * game should implement.
 * @author Daniel Jönsson
 * @version 1.0
 */
public interface IPowerup {

    /**
     * Moves the powerup, updating its position.
     */
    void move();

    /**
     * Applies the effect of the powerup.
     */
    void applyPowerup();

    /**
     * Checks for collisions between the powerup and the paddle.
     */
    void collidesWithPaddle();

    /**
     * Handles "collisions" with the bottom of the game frame.
     */
    void checkBottomCollision();
}
