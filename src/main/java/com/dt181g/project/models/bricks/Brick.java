package com.dt181g.project.models.bricks;

import com.dt181g.project.models.ball.BallModel;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * The {@code Brick} class represents a brick in the game. It defines
 * properties and behaviors of individual bricks.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class Brick {

    /**
     * Instance field variables
     */
    private final Point pos;
    private final Dimension dim;
    private Color color;
    private boolean isAlive = true;

    /**
     * Constructs a brick with the specified position, dimensions, and color.
     * @param pos   The position of the brick.
     * @param dim   The dimensions of the brick.
     * @param color The color of the brick.
     */
    public Brick(final Point pos, final Dimension dim, final Color color){
        this.pos = pos;
        this.dim = dim;
        this.color = color;
    }

    /**
     * Retrieves the height of the brick.
     * @return The height of the brick.
     */
    public int getHeight() {
        return dim.height;
    }

    /**
     * Retrieves the width of the brick.
     * @return The width of the brick.
     */
    public int getWidth() {
        return dim.width;
    }

    /**
     * Retrieves the X-coordinate of the brick's position.
     * @return The X-coordinate of the brick.
     */
    public int getX() {
        return pos.x;
    }

    /**
     * Retrieves the Y-coordinate of the brick's position.
     * @return The Y-coordinate of the brick.
     */
    public int getY() {
        return pos.y;
    }

    /**
     * Retrieves the color of the brick.
     * @return The color of the brick.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the brick to the specified color.
     * @param color The new color of the brick.
     */
    public void setColor(final Color color) {
        this.color = color;
    }

    /**
     * Retrieves the bounds of the brick, representing its position and
     * dimensions.
     * @return The bounding rectangle of the brick.
     */
    public Rectangle getBrickRectangle(){
        return new Rectangle(pos, dim);
    }

    public boolean intersects(BallModel ball) {
        return getBrickRectangle().intersects(ball.getBounds());
    }

    public void destroy() {
        isAlive = false;
    }
    public boolean isAlive() {
        return isAlive;
    }
}
