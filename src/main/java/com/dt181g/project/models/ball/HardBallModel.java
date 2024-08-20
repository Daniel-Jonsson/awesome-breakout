package com.dt181g.project.models.ball;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;
import java.awt.Point;

/**
 * The {@code HardBallModel} class is a concrete subclass of
 * {@link BallModel} representing the behavior and properties of a Hard
 * difficulty ball in the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class HardBallModel extends BallModel{

    /**
     * Constructs an {@code HardBallModel} with a reference to the game model, a
     * specified ball color, and specific characteristics for a hard difficulty
     * ball.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param ballColor The color of the ball.
     */
    public HardBallModel(final BreakoutModel model, final Color ballColor) {
        super(model, ballColor, Appconfig.HARD_POWERUP_CHANCE);
        this.pos = Appconfig.BALL_INIT_POS;
        this.dim = Appconfig.HARD_BALL_SIZE;
        this.color = ballColor;
        this.speed = Appconfig.HARD_BALL_SPEED;
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
            setDim(Appconfig.HARD_BALL_SIZE);
            effectActive = false;
        }
    }
}
