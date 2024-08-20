package com.dt181g.project.handlers;

import com.dt181g.project.support.Appconfig;
import com.dt181g.project.support.sound.Sound;
import com.dt181g.project.support.sound.SoundHandler;

/**
 * The {@code GameWonHandler} extend the {@link GameOutcomeHandler} to
 * implement concrete logic for game won outcome.
 * @author Daniel Jönsson
 * @see GameOutcomeHandler
 */
public final class GameWonHandler extends GameOutcomeHandler{

    /**
     * {@inheritDoc}
     * @return The String to display
     */
    @Override
    protected String displayMessage() {
        return Appconfig.WON_BANNER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void playSound() {
        SoundHandler.playSound(Sound.GAME_WON, false);
    }
}
