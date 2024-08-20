package com.dt181g.project.models.ball.factories;

import com.dt181g.project.models.ball.BallModel;

/**
 * The {@code BallFactory} interface represents the Factory Method Pattern,
 * providing a method to create instances of {@link BallModel} objects.
 * Concrete classes implementing this interface will define the specific
 * logic for creating different types of balls.
 * @author Daniel Jönsson
 * @version 1.0
 */
public interface BallFactory {

    /**
     * Creates a new BallModel, is implemented by concrete factories.
     * @return A new BallModel
     */
    BallModel createBall();
}
