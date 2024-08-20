package com.dt181g.project.concurrentprocessing;

import com.dt181g.project.models.powerup.BasePowerup;
import com.dt181g.project.models.powerup.objpool.PowerupPool;

import java.util.concurrent.Callable;

/**
 * The {@code Consumer} class represents a callable task that consumes
 * powerups from the {@link PowerupPool}. It implements the {@link Callable}
 * interface to allow it to return consumed powerups to be used in-game.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class Consumer implements Callable<BasePowerup> {
    /**
     * Pool of powerups to consume from.
     */
    private final PowerupPool powerupPool;

    /**
     * Constructs a {@code Consumer} with a reference to the powerup pool
     * from which powerups will be consumed.
     * @param powerupPool The {@link PowerupPool} from which power-ups will
     *                    be consumed.
     */
    public Consumer(final PowerupPool powerupPool){
        this.powerupPool = powerupPool;
    }

    /**
     * Computes and returns a consumed powerup from the powerup pool.
     * @return The consumed {@link BasePowerup} instance, or null if the
     * pool is empty.
     */
    @Override
    public BasePowerup call() {
        return this.powerupPool.consumePowerup();
    }
}
