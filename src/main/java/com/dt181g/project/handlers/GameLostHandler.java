package com.dt181g.project.handlers;

import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.sound.Sound;
import com.dt181g.project.support.sound.SoundHandler;

/**
 * The {@code GameLostHandler} extend the {@link GameOutcomeHandler} to
 * implement concrete logic for game lost outcome.
 * @author Daniel Jönsson
 * @see GameOutcomeHandler
 */
public final class GameLostHandler extends GameOutcomeHandler{

    /**
     * {@inheritDoc}
     * @return The String to display
     */
    @Override
    protected String displayMessage() {
        return Appconfig.LOST_BANNER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void playSound() {
        SoundHandler.playSound(Sound.GAME_OVER, false);
    }
}
