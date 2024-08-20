package com.dt181g.project.models.paddle.factories;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.paddle.HardPaddleModel;
import com.dt181g.project.models.paddle.PaddleModel;
import com.dt181g.project.support.Appconfig;

/**
 * The {@code HardPaddleFactory} class is responsible for creating
 * instances of the {@link HardPaddleModel} class, representing a hard
 * difficulty paddle for the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class HardPaddleFactory implements PaddleFactory {

    /**
     * Instance field variable
     */
    private final BreakoutModel model;

    /**
     * Constructs an {@code HardPaddleFactory} with a reference to the game
     * model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public HardPaddleFactory(final BreakoutModel model){
        this.model = model;
    }

    /**
     * Creates a new instance of a hard paddle.
     * @return A newly created {@link HardPaddleModel} object.
     */
    @Override
    public PaddleModel createPaddle() {
        return new HardPaddleModel(model, Appconfig.PADDLE_COLOR);
    }
}
