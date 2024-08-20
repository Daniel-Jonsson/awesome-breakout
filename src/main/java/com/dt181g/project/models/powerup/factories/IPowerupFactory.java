package com.dt181g.project.models.powerup.factories;

import com.dt181g.project.models.powerup.BasePowerup;

/**
 * The {@code PowerupFactory} interface defines the contract for creating
 * powerups in the game. Implementing factories must provide a method to add
 * and create a specific type of powerup. Adheres to the Abstract Factory
 * Pattern.
 * @author Daniel Jönsson
 * @version 1.0
 */
public interface IPowerupFactory {

    /**
     * Creates and adds a specific type of powerup to the game.
     * @return The created {@link BasePowerup} instance.
     */
    BasePowerup addPaddlePowerup();
    BasePowerup addBallPowerup();
}
