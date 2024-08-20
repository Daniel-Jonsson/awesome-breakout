package com.dt181g.project.models.ball;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;
import java.awt.Point;

/**
 * The {@code MediumBallModel} class is a concrete subclass of
 * {@link BallModel} representing the behavior and properties of a Medium
 * difficulty ball in the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class MediumBallModel extends BallModel{

    /**
     * Constructs an {@code MediumBallModel} with a reference to the game
     * model, a specified ball color, and specific characteristics for a medium
     * difficulty ball.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param ballColor The color of the ball.
     */
    public MediumBallModel(final BreakoutModel model, final Color ballColor) {
        super(model, ballColor, Appconfig.MEDIUM_POWERUP_CHANCE);
        this.pos = Appconfig.BALL_INIT_POS;
        this.dim = Appconfig.MEDIUM_BALL_SIZE;
        this.color = ballColor;
        this.speed = Appconfig.MEDIUM_BALL_SPEED;
        this.velocity = new Point(speed, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEffect() {
        long elapsedtime =
                (System.currentTimeMillis() - this.activateTime)/1000;
        if (elapsedtime > Appconfig.EFFECT_TIME_SECONDS){
            setDim(Appconfig.MEDIUM_BALL_SIZE);
            effectActive = false;
        }
    }
}
