package com.dt181g.project.models.ball.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.ball.HardBallModel;
import com.dt181g.project.support.Appconfig;

/**
 * The {@code HardBallFactory} class is a concrete implementation of the {@link BallFactory}
 * interface, representing a factory that creates hard difficulty balls for the game.
 * It defines the specific logic for creating hard balls, which include their characteristics
 * such as color.
 * @author Daniel Jönsson
 * @version 1.0
 * @see BallFactory
 */
public final class HardBallFactory implements BallFactory {

    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs a {@code HardBallFactory} with a reference to the game model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public HardBallFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates and returns a new hard difficulty ball with the specified color.
     * @return A new instance of {@link HardBallModel} representing a hard
     * difficulty ball.
     */
    @Override
    public HardBallModel createBall() {
        return new HardBallModel(this.model, Appconfig.HARD_BALL_COLOR);
    }
}
