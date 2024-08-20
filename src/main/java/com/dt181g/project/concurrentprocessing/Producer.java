package com.dt181g.project.concurrentprocessing;

import com.dt181g.project.models.powerup.objpool.PowerupPool;

/**
 * The {@code Producer} class represents a runnable task that produces random
 * powerups and adds them to a {@link PowerupPool} if the object pool size
 * dwindles.It implements the {@link Runnable} interface to execute the task.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class Producer implements Runnable{

    /**
     * Pool of powerups to produce to.
     */
    private final PowerupPool powerupPool;

    /**
     * Constructs a {@code Producer} with a reference to the powerup pool to
     * which produced powerups will be added.
     *
     * @param powerupPool The {@link PowerupPool} to which produced powerups
     *                    will be added.
     */
    public Producer(final PowerupPool powerupPool){
        this.powerupPool = powerupPool;
    }
    /**
     * Produces a random powerup and add it to the pool
     */
    @Override
    public void run() {
        this.powerupPool.produceRandomPowerup();
    }
}
