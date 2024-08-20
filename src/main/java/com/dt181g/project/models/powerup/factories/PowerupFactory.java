package com.dt181g.project.models.powerup.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.powerup.BallPowerup;
import com.dt181g.project.models.powerup.BasePowerup;
import com.dt181g.project.models.powerup.PaddlePowerup;

/**
 * The {@code PowerupFactory} class is a concrete factory of
 * the {@link IPowerupFactory} interface. It provides a method to create and
 * add a concrete powerups to the game. Shows the Abstract Factory Pattern in
 * action.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class PowerupFactory implements IPowerupFactory {

    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs an {@code PowerupFactory} with a reference to the
     * game model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public PowerupFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates and adds a paddle powerup to the game.
     * @return The created {@link BasePowerup} instance.
     */
    @Override
    public PaddlePowerup addPaddlePowerup() {
        return new PaddlePowerup(model);
    }

    /**
     * Creates and adds a ball powerup to the game.
     * @return The created {@link BasePowerup} instance.
     */
    @Override
    public BallPowerup addBallPowerup() {
        return new BallPowerup(model);
    }

}
