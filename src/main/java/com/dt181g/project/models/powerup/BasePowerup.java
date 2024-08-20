package com.dt181g.project.models.powerup;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.support.Appconfig;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * The {@code BasePowerup} class is a base class for representing various
 * powerups in the game. It provides common properties and methods for all
 * powerup types.
 * @author Daniel Jönsson
 * @version 1.0
 */
public class BasePowerup implements IPowerup{

    /**
     * Instance field variables
     */
    protected Point pos;
    protected Dimension dim;
    protected Color color;
    protected int velocity = Appconfig.POWERUP_SPEED;
    private boolean shouldRemove = false;
    private boolean shouldActivate = false;
    protected BreakoutModel model;

    /**
     * Constructs an {@code BasePowerup} with a reference to the game model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public BasePowerup(final BreakoutModel model){
        this.model = model;
        this.dim = Appconfig.POWERUP_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        pos.y += velocity;
        collidesWithPaddle();
        checkBottomCollision();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyPowerup() {
        if (this instanceof BallPowerup){
            model.getBall().activatePowerup();
        } else if (this instanceof PaddlePowerup){
            model.getPaddle().activatePowerup();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWithPaddle() {
        if (model.getPaddle().getBounds().intersects(this.getBounds())){
            applyPowerup();
            shouldActivate = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkBottomCollision(){
        if (this.getPos().y > (model.getHeight()) + this.dim.height){
            shouldRemove = true;
        }
    }

    /**
     * Checks if the powerup should be removed from the game.
     *
     * @return true if the power-up should be removed, false otherwise.
     */
    public boolean isShouldRemove() {
        return shouldRemove;
    }

    /**
     * Checks if the powerup should be activated.
     * @return true if the power-up should be activated, false otherwise.
     */
    public boolean isShouldActivate() {
        return shouldActivate;
    }

    /**
     * Gets the color of the powerup.
     * @return The color of the powerup.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the powerup.
     * @param color The color to set for the powerup.
     */
    public void setColor(final Color color){
        this.color = color;
    }

    /**
     * Gets the dimensions of the powerup.
     * @return The dimensions of the powerup.
     */
    public Dimension getDim() {
        return dim;
    }

    /**
     * Gets the position of the powerup.
     * @return The position of the powerup.
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Resets the state of the powerup, clearing activation and removal flags
     * . Is used when a powerup gets returned to the pool
     */
    public void resetState(){
        shouldRemove = false;
        shouldActivate = false;
    }

    /**
     * Sets the dimensions of the powerup.
     * @param dim The dimensions to set for the powerup.
     */
    public void setDim(final Dimension dim) {
        this.dim = dim;
    }

    /**
     * Sets the position of the powerup.
     * @param pos The position to set for the powerup.
     */
    public void setPos(final Point pos) {
        this.pos = pos;
    }

    /**
     * Gets the bounds of the powerup for collision detection.
     * @return The bounding rectangle of the powerup.
     */
    public Rectangle getBounds(){
        return new Rectangle(pos, dim);
    }
}
