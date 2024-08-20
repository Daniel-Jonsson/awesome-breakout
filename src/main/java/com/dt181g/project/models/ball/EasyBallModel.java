package com.dt181g.project.models.ball;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;
import java.awt.Point;

/**
 * The {@code EasyBallModel} class is a concrete subclass of
 * {@link BallModel} representing the behavior and properties of an easy
 * difficulty ball in the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class EasyBallModel extends BallModel{

    /**
     * Constructs a {@code EasyBallModel} with a reference to the game model
     * and specific characteristics for an easy difficulty ball.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param ballColor The color of the ball.
     */
    public EasyBallModel(final BreakoutModel model, final Color ballColor){
        super(model, ballColor, Appconfig.EASY_POWERUP_CHANCE);
        this.pos = Appconfig.BALL_INIT_POS;
        this.dim = Appconfig.EASY_BALL_SIZE;
        this.color = ballColor;
        this.speed = Appconfig.EASY_BALL_SPEED;
        this.velocity = new Point(speed,
                speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEffect() {
        long elapsedtime =
                (System.currentTimeMillis() - this.activateTime)/1000;
        if (elapsedtime > Appconfig.EFFECT_TIME_SECONDS){
            setDim(Appconfig.EASY_BALL_SIZE);
            effectActive = false;
        }
    }
}
