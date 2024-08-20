package com.dt181g.project.models.paddle;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;

/**
 * The {@code EasyPaddleModel} class represents a specific type of paddle
 * used in the game. It defines the properties and behaviors of an easy
 * difficulty paddle.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class EasyPaddleModel extends PaddleModel{

    /**
     * Constructs an {@code EasyPaddleModel} with a reference to the game
     * model, a specified paddle color, and sets the dimensions and speed for
     * an easy difficulty paddle.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param paddleColor The color of the paddle.
     */
    public EasyPaddleModel(final BreakoutModel model, final Color paddleColor){
        super(model, paddleColor);
        this.dim = Appconfig.EASY_PADDLE_SIZE;
        this.paddleSpeed = Appconfig.EASY_PADDLE_SPEED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEffect() {
        long elapsedtime =
                (System.currentTimeMillis() - this.activateTime)/1000;
        if (elapsedtime > 7){
            setDim(Appconfig.EASY_PADDLE_SIZE);
            effectActive = false;
        }
    }
}
