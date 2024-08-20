package com.dt181g.project.support.sound;

/**
 * The {@code Sound} enum represents different sound effects used in the game
 * . Each enum constant corresponds to a specific sound effect file path.
 * @author Daniel Jönsson
 */
public enum Sound {

    /**
     * The soundtrack music.
     */
    SOUNDTRACK("/sounds/soundtrack.wav"),

    /**
     * Sound effect for main-menu(setup screen).
     */
    SETUPSOUND("/sounds/setupSound.wav"),

    /**
     * Sound effect for when a ball hits a brick.
     */
    BLOCK_HIT("/sounds/block_hit.wav"),

    /**
     * Sound effect for when a ball hits a wall.
     */
    WALL_HIT("/sounds/ball_hit_wall.wav"),

    /**
     * Sound effect for when a ball hits the paddle.
     */
    PADDLE_HIT("/sounds/paddle_hit.wav"),

    /**
     * Sound effect for game over.
     */
    GAME_OVER("/sounds/game_over.wav"),

    /**
     * Sound effect for winning the game.
     */
    GAME_WON("/sounds/game_won.wav"),

    /**
     * Sound effect for collecting a power-up.
     */
    POWERUP("/sounds/powerup.wav");

    private final String soundPath;

    /**
     * Constructs a {@code Sound} enum with the specified sound file path.
     * @param soundPath The file path of the sound effect.
     */
    Sound(String soundPath){
        this.soundPath = soundPath;
    }

    /**
     * Gets the file path of the sound effect.
     * @return The file path of the sound effect.
     */
    public String getSoundPath(){
        return soundPath;
    }
}
