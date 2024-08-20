package com.dt181g.project.models.powerup;

import com.dt181g.project.models.BreakoutModel;

/**
 * The {@code BallPowerup} class represents a powerup that affects the
 * behavior of the ball in the game. It is a concrete type of powerup that
 * can be caught and activated during gameplay.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class BallPowerup extends BasePowerup {

    /**
     * Constructs a {@code BallPowerup} with a reference to the game model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public BallPowerup(final BreakoutModel model) {
        super(model);
    }
}
