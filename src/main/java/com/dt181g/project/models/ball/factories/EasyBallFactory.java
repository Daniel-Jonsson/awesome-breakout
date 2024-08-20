package com.dt181g.project.models.ball.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.ball.EasyBallModel;
import com.dt181g.project.support.Appconfig;

/**
 * The {@code EasyBallFactory} class is a concrete implementation of the {@link BallFactory}
 * interface, representing a factory that creates easy difficulty balls for the game.
 * It defines the specific logic for creating easy balls, which include their characteristics
 * such as color.
 * @author Daniel Jönsson
 * @version 1.0
 * @see BallFactory
 */
public final class EasyBallFactory implements BallFactory{

    /**
     * Instance field variable
     */
    private final BreakoutModel model;
    /**
     * Constructs an {@code EasyBallFactory} with a reference to the game model.
     *
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public EasyBallFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates and returns a new easy difficulty ball with the specified color.
     *
     * @return A new instance of {@link EasyBallModel} representing an easy
     * difficulty ball.
     */
    @Override
    public EasyBallModel createBall() {
        return new EasyBallModel(this.model, Appconfig.EASY_BALL_COLOR);
    }
}
