package com.dt181g.project.models.paddle.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.paddle.EasyPaddleModel;
import com.dt181g.project.models.paddle.PaddleModel;
import com.dt181g.project.support.Appconfig;

/**
 * The {@code EasyPaddleFactory} class is responsible for creating instances of
 * the {@link EasyPaddleModel} class, representing an easy difficulty paddle
 * for the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class EasyPaddleFactory implements PaddleFactory {

    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs an {@code EasyPaddleFactory} with a reference to the game
     * model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public EasyPaddleFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates a new instance of an easy paddle.
     * @return A newly created {@link EasyPaddleModel} object.
     */
    @Override
    public PaddleModel createPaddle() {
        return new EasyPaddleModel(model, Appconfig.PADDLE_COLOR);
    }
}
