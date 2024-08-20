package com.dt181g.project.models.bricks;

import com.dt181g.project.models.ball.BallModel;
import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.Util;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The {@code BrickField} class represents a collection of {@link Brick} arranged
 * in a field. It provides methods for initializing, rendering, and interacting
 * with the bricks.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class BrickField {

    /**
     * Instance field variables
     */
    private int amountBricks;
    private final Brick[] brickArray;
    private final Point startPos;
    private final Dimension dim;
    private final float lum;
    private final float sat;

    /**
     * Constructs a {@code BrickField} with default properties, such as the number
     * of bricks, starting position, dimensions, luminance, and saturation.
     */
    public BrickField(){
        this.amountBricks = Appconfig.BLOCK_AMOUNT;
        this.startPos = Appconfig.BLOCK_POS_START;
        this.dim = Appconfig.BLOCK_SIZE;
        this.lum = Appconfig.COLOR_LUMINANCE;
        this.sat = Appconfig.COLOR_SATURATION;
        this.brickArray = initBricks();
    }

    /**
     * Initializes an array of bricks with specified properties and returns it.
     * @return An array of bricks with properties like position, dimension,
     * and color.
     */
    private Brick[] initBricks(){
        Brick[] arr = new Brick[amountBricks];
        int initX = startPos.x;
        for (int i = 0; i < arr.length; i++){
            Point pos = new Point(startPos);
            Color color = Util.INSTANCE.generateRandomColor(lum, sat);
            arr[i] = new Brick(pos, dim, color);
            startPos.x += dim.width + Appconfig.BLOCK_POS_SPACING.x;
            if (startPos.x + dim.width >= Appconfig.FRAME_SIZE.width){
                startPos.y += Appconfig.BLOCK_POS_SPACING.y;
                startPos.x = initX;
            }
        }
        return arr;
    }

    /**
     * Gets a list of renderable bricks
     * @return A list of non-null bricks that are currently part of the brick field.
     */
    public List<Rectangle> getRenderableBricks(){
        return Arrays.stream(brickArray)
                .filter(Brick::isAlive)
                .map(Brick::getBrickRectangle)
                .collect(Collectors.toList());
    }

    public List<Color> getRenderableBricksColor() {
        return Arrays.stream(brickArray)
                .filter(Brick::isAlive)
                .map(Brick::getColor)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the brick at the specified index from the brick field.
     * @param index The index of the brick to retrieve.
     * @return The brick at the given index if it exists and is not null, or
     * null if not found.
     */
    public Brick getBrick(final int index){
        return IntStream.range(0, brickArray.length)
                .filter(i -> i == index && brickArray[i].isAlive())
                .mapToObj(i -> brickArray[i])
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes the brick at the specified index from the brick field.
     * @param index The index of the brick to remove.
     */
    public void remove(final int index){
        IntStream.range(0, brickArray.length)
                .filter(i -> i == index)
                .findFirst()
                .ifPresent(i ->{
                    brickArray[i].destroy();
                    amountBricks--;
                });
    }

    /**
     * Gets the total number of bricks in the brick field.
     * @return The total number of bricks in the brick field.
     */
    public int getTotalAmountBricks(){
        return amountBricks;
    }

    /**
     * Checks for intersections between a ball and the bricks in the
     * field.
     * @param ballModel The ball model to check for intersections with bricks.
     * @return The index of the brick that intersects with the ball or -1 if
     * no intersection is found.
     */
    public int intersects(final BallModel ballModel){
        return IntStream.range(0, brickArray.length)
                .filter(i -> brickArray[i].isAlive() && brickArray[i].intersects(ballModel))
                .findFirst()
                .orElse(-1);
    }
}
