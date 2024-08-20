package com.dt181g.project.models.paddle;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * The {@code PaddleModel} class is an abstract base class for representing
 * paddles in the game. It defines common properties and behaviors of different
 * types of paddles used in the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public abstract class PaddleModel {

    /**
     * Instance field variables
     */
    protected final BreakoutModel model;
    protected final Color paddleColor;
    protected Point pos;
    protected Dimension dim;
    protected int paddleSpeed;
    protected long activateTime;
    protected boolean effectActive = false;
    protected int xVelocity;

    /**
     * Constructs a {@code PaddleModel} with a reference to the game model and a
     * specified paddle color.
     * @param model The {@link BreakoutModel} representing the game model.
     * @param paddleColor The color of the paddle.
     */
    public PaddleModel(final BreakoutModel model, final Color paddleColor){
        this.model = model;
        this.paddleColor = paddleColor;
        int initX = model.getWidth()/2 + Appconfig.EASY_PADDLE_SIZE.width/2;
        this.pos = new Point(initX, Appconfig.EASY_PADDLE_SIZE.height + Appconfig.PADDLE_POS_OFFSET.y);
    }

    /**
     * Sets the dimensions of the paddle.
     * @param dim The dimensions of the paddle.
     */
    public void setDim(Dimension dim) {
        this.dim = dim;
    }

    /**
     * Retrieves the dimensions of the paddle.
     * @return The dimensions of the paddle.
     */
    public Dimension getDim() {
        return dim;
    }

    /**
     * Retrieves the speed of the paddle.
     * @return The speed of the paddle.
     */
    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Retrieves the color of the paddle.
     * @return The color of the paddle.
     */
    public Color getColor() {
        return paddleColor;
    }

    /**
     * Sets the x-velocity of the paddle.
     * @param xVelocity The x-velocity of the paddle.
     */
    public void setXVelocity(final int xVelocity){
        this.xVelocity = xVelocity;
    }

    /**
     * Moves the paddle horizontally, updating its position within the game.
     */
    public void movePaddle() {
        int nextX = this.pos.x + this.xVelocity;
        int leftSide = model.getWidth();
        int rightSide = 0;
        // Makes sure that the x-position is within the game bounds.
        this.pos.x = Math.min(Math.max(nextX, rightSide), leftSide - this.dim.width);
        if (effectActive){
            updateEffect();
        }
    }

    /**
     * Sets the position of the paddle.
     * @param pos The position to set.
     */
    public void setPos(final Point pos) {
        this.pos = pos;
    }

    /**
     * Retrieves the current position of the paddle.
     * @return The position of the paddle.
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Retrieves the dimensions of the paddle with a powerup effect applied.
     * @return The dimensions of the paddle with a powerup effect.
     */
    private Dimension powerUpDim(){

        return new Dimension(getDim().width*2, getDim().height);
    }

    /**
     * Activates a powerup effect for the paddle.
     */
    public void activatePowerup() {
        if (!effectActive){
            this.activateTime = System.currentTimeMillis();
            setDim(powerUpDim());
            effectActive = true;
        }

    }

    /**
     * Updates the power-up effect on the paddle. Subclasses will implement
     * this method to define specific effects.
     */
    public abstract void updateEffect();

    /**
     * Retrieves the bounds of the paddle.
     * @return The bounding rectangle of the paddle.
     */
    public Rectangle getBounds() {
        return new Rectangle(pos, dim);
    }
}
