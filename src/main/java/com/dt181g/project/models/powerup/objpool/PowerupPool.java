package com.dt181g.project.models.powerup.objpool;

import com.dt181g.project.models.BreakoutModel;
import com.dt181g.project.models.powerup.BallPowerup;
import com.dt181g.project.models.powerup.BasePowerup;
import com.dt181g.project.models.powerup.PaddlePowerup;
import com.dt181g.project.models.powerup.factories.PowerupFactory;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code PowerupPool} class represents a object pool of powerups in the
 * game. It manages the creation, consumption, and returning of powerups,
 * allowing the game to re-use specific powerup objects which in turn saves
 * memory consumption.
 * @author Daniel Jönsson
 * @version 1.0
 */
public final class PowerupPool {

    /**
     * Instance field variables
     */
    private final BreakoutModel model;
    private final LinkedBlockingQueue<BasePowerup> powerupPool;
    private final Random random;

    /**
     * Constructs a {@code PowerupPool} with a reference to the game model.
     * @param model The {@link BreakoutModel} representing the game model.
     */
    public PowerupPool(final BreakoutModel model){
        this.model = model;
        this.powerupPool = new LinkedBlockingQueue<>();
        this.random = new Random();
    }

    /**
     * Consumes and removes a powerup from the pool.
     *
     * @return The consumed {@link BasePowerup} instance, or null if the
     * pool is empty.
     */
    public synchronized BasePowerup consumePowerup(){
        return powerupPool.poll();
    }

    /**
     * Produces a ball powerup and adds it to the pool. It is critical that
     * the method works in synchronization in order to avoid race-condition.
     */
    public void produceBallPowerup(){
        synchronized (powerupPool){
            BallPowerup newBallPowerup = new PowerupFactory(model).addBallPowerup();
            powerupPool.add(newBallPowerup);
            powerupPool.notify(); // Notify waiting thread
        }
    }

    /**
     * Produces a paddle powerup and adds it to the pool. It is critical that
     * the method works in synchronization in order to avoid race-condition.
     */
    public void producePaddlePowerup(){
        synchronized (powerupPool){
            PaddlePowerup newPaddlePowerup = new PowerupFactory(model).addPaddlePowerup();
            powerupPool.add(newPaddlePowerup);
            powerupPool.notify();
        }
    }

    /**
     * Produces a random powerup (ball or paddle) and adds it to the pool.
     */
    public void produceRandomPowerup(){
        switch (random.nextInt(2)) {
            case 0 -> produceBallPowerup();
            case 1 -> producePaddlePowerup();
        }
    }

    /**
     * Returns a powerup to the pool.
     * @param powerup The {@link BasePowerup} to return to the pool.
     */
    public void returnPowerup(final BasePowerup powerup){
        synchronized (powerupPool){
            this.powerupPool.add(powerup);
        }
    }

    /**
     * Retrieves the pool of powerups.
     * @return The {@link LinkedBlockingQueue} containing powerup objects.
     */
    public LinkedBlockingQueue<BasePowerup> getPowerupPool() {
        return powerupPool;
    }
}
