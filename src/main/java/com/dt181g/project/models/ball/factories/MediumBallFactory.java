package com.dt181g.project.models.ball.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.ball.MediumBallModel;
import com.dt181g.project.support.Appconfig;

/**
 * The {@code MediumBallFactory} class is a concrete implementation of the {@link BallFactory}
 * interface, representing a factory that creates medium difficulty balls for the game.
 * It defines the specific logic for creating medium balls, which include their characteristics
 * such as color.
 * @author Daniel Jönsson
 * @version 1.0
 * @see BallFactory
 */
public final class MediumBallFactory implements BallFactory {

    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs a {@code MediumBallFactory} with a reference to the game
     * model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public MediumBallFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates and returns a new medium difficulty ball with the specified
     * color.
     * @return A new instance of {@link MediumBallModel} representing a medium
     * difficulty ball.
     */
    @Override
    public MediumBallModel createBall() {
        return new MediumBallModel(this.model, Appconfig.MEDIUM_BALL_COLOR);
    }
}
