package com.dt181g.project.models.paddle.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.paddle.MediumPaddleModel;
import com.dt181g.project.models.paddle.PaddleModel;
import com.dt181g.project.support.Appconfig;


/**
 * The {@code MediumPaddleFactory} class is responsible for creating
 * instances of the {@link MediumPaddleModel} class, representing an medium
 * difficulty paddle for the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class MediumPaddleFactory implements PaddleFactory {
    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs an {@code MediumPaddleFactory} with a reference to the game
     * model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public MediumPaddleFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates a new instance of a medium paddle.
     * @return A newly created {@link MediumPaddleModel} object.
     */
    @Override
    public PaddleModel createPaddle() {
        return new MediumPaddleModel(model, Appconfig.PADDLE_COLOR);
    }
}
