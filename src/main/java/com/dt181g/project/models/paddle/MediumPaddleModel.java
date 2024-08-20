package com.dt181g.project.models.paddle;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;

/**
 * The {@code MediumPaddleModel} class represents a specific type of paddle
 * used in the game. It defines the properties and behaviors of a medium
 * difficulty paddle.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class MediumPaddleModel extends PaddleModel{

    /**
     * Constructs an {@code MediumPaddleModel} with a reference to the game
     * model, a specified paddle color, and sets the dimensions and speed for
     * a medium difficulty paddle.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param paddleColor The color of the paddle.
     */
    public MediumPaddleModel(final BreakoutModel model, final Color paddleColor){
        super(model, paddleColor);
        this.dim = Appconfig.MEDIUM_PADDLE_SIZE;
        this.paddleSpeed = Appconfig.MEDIUM_PADDLE_SPEED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEffect() {
        long elapsedtime =
                (System.currentTimeMillis() - this.activateTime)/1000;
        if (elapsedtime > 7){
            setDim(Appconfig.MEDIUM_PADDLE_SIZE);
            effectActive = false;
        }
    }
}
