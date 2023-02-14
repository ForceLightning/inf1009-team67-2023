package com.inf1009.team67.game.Main;

public class Settings {
    private static float masterVolume = 1f;
    private static float musicVolume = 1f;
    private static float effectsVolume = 1f;

    public static float getMasterVolume() {
        return masterVolume;
    }

    public static void setMasterVolume(float masterVolume) {
        Settings.masterVolume = masterVolume;
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(float musicVolume) {
        Settings.musicVolume = musicVolume;
    }

    public static float getEffectsVolume() {
        return effectsVolume;
    }

    public static void setEffectsVolume(float effectsVolume) {
        Settings.effectsVolume = effectsVolume;
    }
}
