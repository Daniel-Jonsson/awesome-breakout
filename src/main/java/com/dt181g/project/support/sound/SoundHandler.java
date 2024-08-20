package com.dt181g.project.support.sound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code SoundHandler} class manages the loading and playing of sound
 * effects in the game.
 * @author Daniel Jönsson
 */
public class SoundHandler {
    /**
     * A map between Sound enum constant and corresponding Clip to that constant
     */
    private static final ConcurrentHashMap<Sound, Clip> SOUND_CLIPS =
            new ConcurrentHashMap<>();


    static {
        for (Sound sound : Sound.values()) {
            loadSoundClip(sound);
        }
    }

    /**
     * Maps each enum constant to corresponding Clip
     * @param sound The enum constant
     */
    private static void loadSoundClip(Sound sound) {
        try {
            Clip clip = AudioSystem.getClip();
            URL soundURL = SoundHandler.class.getResource(sound.getSoundPath());
            if (soundURL != null){
                clip.open(AudioSystem.getAudioInputStream(soundURL));
                SOUND_CLIPS.put(sound, clip);
            } else{
                System.out.printf("Sound file not found %s", sound.getSoundPath());
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the specified sound effect with optional looping and volume control.
     * @param sound The sound effect to play.
     * @param loop true to loop the sound continuously, false
     *                        to play it once.
     * @param volume The desired volume of the sound (0.0f to 2.0f).
     */
    public static void playSound(Sound sound, boolean loop, float volume) {
        if (SOUND_CLIPS.containsKey(sound)) {
            Clip clip = SOUND_CLIPS.get(sound);
            if(volume < 0.0f){
                volume = 0.0f;
            } else if (volume > 2.0f) {
                volume = 2.0f;
            }

            FloatControl control =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0)* 20.0);
            control.setValue(dB);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }

    /**
     * Plays the specified sound effect with optional looping.
     *
     * @param sound The sound effect to play.
     * @param loop {@code true} to loop the sound continuously, {@code
     * false} to play it once.
     */
    public static void playSound(Sound sound, boolean loop) {
        if (SOUND_CLIPS.containsKey(sound)) {
            Clip clip = SOUND_CLIPS.get(sound);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }

    /**
     * Stops the specified sound effect and resets its playback position.
     * @param sound The sound effect to stop.
     */
    public static void stopSound(Sound sound) {
        if (SOUND_CLIPS.containsKey(sound)) {
            Clip clip = SOUND_CLIPS.get(sound);
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /**
     * Stops playback of all loaded sound effects and resets their playback
     * positions.
     */
    public static void stopAllSounds(){
        for (Sound sound : SOUND_CLIPS.keySet()){
            stopSound(sound);
        }
    }

    /**
     * Change volume for the sounds, it is mainly used within
     * @param sound the sound to change volume for
     * @param volume the volume to change to.
     */
    public static void changeVolume(Sound sound, float volume){
        if (SOUND_CLIPS.containsKey(sound)){
            Clip clip = SOUND_CLIPS.get(sound);
            FloatControl control =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0)*20.0);
            control.setValue(dB);
        }
    }

    /**
     * Sets volume for all sounds based on the volumed decided by the user.
     * @param volume The volume to set each sound to.
     */
    public static void setVolumeForSounds(float volume){
        for (Sound sound : SOUND_CLIPS.keySet()) {
            changeVolume(sound, volume);
        }
    }
}
