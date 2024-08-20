package com.dt181g.project.models.ball;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.bricks.Brick;
import com.dt181g.project.models.bricks.BrickField;
import com.dt181g.project.models.powerup.BasePowerup;
import com.dt181g.project.concurrentprocessing.Consumer;
import com.dt181g.project.concurrentprocessing.ThreadPoolManager;
import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.sound.Sound;
import com.dt181g.project.support.sound.SoundHandler;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The {@code BallModel} class is an abstract base class for representing
 * balls in the game. It defines common properties and behaviors of different
 * types of balls used in the game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public abstract class BallModel {
    /**
     * Instance field variables.
     */
    private final BreakoutModel model;
    protected Point pos;
    protected Dimension dim;
    protected Point velocity;
    protected int speed;
    private final int powerupChance;
    protected long activateTime;
    protected boolean effectActive = false;
    private boolean isStuck = true;
    protected Color color;
    private final ThreadPoolManager threadPoolManager;
    private final BrickField brickField;

    /**
     * Constructs a {@code BallModel} with a reference to the game model, a
     * specified ball color and a powerup chance.
     * @param model        The {@link BreakoutModel} representing the game model.
     * @param ballColor    The color of the ball.
     * @param powerupChance The chance of spawning powerup.
     */
    public BallModel(final BreakoutModel model, final Color ballColor,
                     final int powerupChance) {
        this.model = model;
        this.color = ballColor;
        this.threadPoolManager = model.getThreadPoolManager();
        this.brickField = model.getBrickField();
        this.powerupChance = powerupChance;
    }

    /**
     * Gets the current position of the ball.
     * @return The current position of the ball.
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Sets the position of the ball.
     * @param pos The new position of the ball.
     */
    public void setPos(final Point pos) {
        this.pos = pos;
    }

    /**
     * Sets the dimensions of the ball.
     *
     * @param dim The new dimension of the ball.
     */
    public void setDim(final Dimension dim) {
        this.dim = dim;
    }

    /**
     * Retrieves the bounds of the ball.
     * @return The bounding rectangle of the ball.
     */
    public Rectangle getBounds() {
        return new Rectangle(pos, dim);
    }

    /**
     * Gets the color of the ball.
     * @return The color of the ball.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Moves the ball, updating its position based on its velocity and
     * checking for collisions.
     */
    public void move() {
        if (isStuck) {
            stuck();
        } else {
            pos.x += velocity.x;
            pos.y += velocity.y;
            checkBrickCollision();
            checkPaddleCollision();
            checkLeftCollision();
            checkRightCollision();
            checkTopCollision();
            checkBottomCollision();
            if (effectActive) {
                updateEffect();
            }
        }
    }

    /**
     * Retrieves the dimensions of the ball.
     * @return The dimensions of the ball.
     */
    public Dimension getDim() {
        return dim;
    }

    /**
     * Puts the ball where the paddle is located.
     */
    private void stuck() {
        int ballX = model.getPaddle().getPos().x + (model.getPaddle().getDim().width / 2) - (getDim().width / 2);
        int ballY = model.getPaddle().getPos().y - getDim().height;
        setPos(new Point(ballX, ballY));
    }

    /**
     * Sets the ball as "stuck" to the paddle.
     * @param stuck Indicates whether the ball is stuck to the paddle.
     */
    public void setStuck(final boolean stuck) {
        this.isStuck = stuck;
    }

    /**
     * Handles collisions with the left wall.
     */
    private void checkLeftCollision() {
        if (pos.x < 0) {
            velocity.x = speed;
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.WALL_HIT, false));
        }
    }

    /**
     * Handles "collisions" with the bottom of the game frame.
     */
    private void checkBottomCollision() {
        if (pos.y > (model.getHeight() + dim.height)) {
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.GAME_OVER, false));
        }
    }

    /**
     * Handles collisions with the right wall.
     */
    private void checkRightCollision() {
        if (pos.x > (model.getWidth() - dim.width)) {
            velocity.x = -speed;
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.WALL_HIT, false));
        }
    }

    /**
     * Handles collisions with the top of the game frame.
     */
    private void checkTopCollision() {
        if (pos.y < 0) {
            velocity.y = speed;
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.WALL_HIT, false));
        }
    }

    /**
     * Handles collisions between the ball and the paddle.
     */
    private void checkPaddleCollision() {
        if (model.getPaddle().getBounds().intersects(this.getBounds())) {
            if (velocity.x == 0) {
                randomizeXVelocity();
            }
            velocity.y *= -1;
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.PADDLE_HIT, false));
        }
    }

    /**
     * Handles collisions with brick and updates the ball's behavior/adds
     * score to player
     */
    private void checkBrickCollision() {
        int brickIdx = brickField.intersects(this);
        if (brickIdx == -1) {
            return;
        }
        Brick brick = brickField.getBrick(brickIdx);
        if (brick == null) {
            return;
        }
        velocity.y *= -1;
        shouldDropPowerup(brick);
        brickField.remove(brickIdx);
        model.addScore(50);
        model.notifyObserver();
    }

    /**
     * Checks if a power-up should drop where the brick is
     * @param brick The brick where the power-up should spawn
     */
    private void shouldDropPowerup(final Brick brick) {
        boolean spawnPowerup = new Random().nextInt(100) <= powerupChance;
        if (spawnPowerup) {
            spawnPowerup(brick);
            threadPoolManager.executeSound(() -> SoundHandler.playSound(Sound.POWERUP, false));
        } else {
            model.getThreadPoolManager().executeSound(() -> SoundHandler.playSound(Sound.BLOCK_HIT, false));
        }
    }

    /**
     * Consumes a powerup from the pool and spawns it where the brick is
     * located.
     * @param brick The brick which got hit by the ball
     */
    protected void spawnPowerup(final Brick brick) {
        Point powerupPos =
                new Point(brick.getX() + (brick.getWidth()/2) - (Appconfig.POWERUP_SIZE.width / 2), brick.getY());
        Future<BasePowerup> powerupFuture =
                threadPoolManager.execute(new Consumer(model.getPowerupPool()));
        try {
            BasePowerup powerup = powerupFuture.get();
            powerup.setColor(brick.getColor());
            powerup.setPos(powerupPos);
            model.addPowerup(powerup);
        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the powerup hits the paddle the ball should activate its effect
     * and changes dimension.
     * @return The new Dimension that the ball should have
     */
    private Dimension powerUpDim() {
        return new Dimension(getDim().width * 2, getDim().height * 2);
    }

    /**
     * Activates a powerup if there is currently no effect active.
     */
    public void activatePowerup() {
        if (!effectActive) {
            this.activateTime = System.currentTimeMillis();
            setDim(powerUpDim());
            effectActive = true;
        }
    }

    /**
     * Abstract method for updating Effect, since the size of balls differ
     * for concrete balls this method needs to be implemented separately by
     * concrete balls.
     */
    public abstract void updateEffect();

    /**
     * Starts moving the ball when the user presses the spacebar. Sets the
     * x-velocity to 0 and y-velocity to *= -1 to move the ball up in a
     * straight line.
     */
    public void startBallMovement() {
        velocity.x = 0;
        isStuck = false;
        velocity.y *= -1;
    }

    /**
     * Randomizes the x-velocity to either positive or negative value so
     * the ball goes either left or right.
     */
    private void randomizeXVelocity() {
        boolean isLeft = new Random().nextBoolean();
        velocity.x = isLeft ? -speed : speed;
    }
}
