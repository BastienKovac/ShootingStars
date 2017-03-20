package network.iut.org.flappydragon.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Android on 20/03/2017.
 */

public class PreferencesUtil {

    private static final String PREF_FILE_NAME = "shootingStarsPref";
    private static final String MUSIC_ENABLED = "musicEnabled";
    private static final String MUSIC_VOLUME = "musicVolume";
    private static final String SFX_VOLUME = "sfxVolume";

    private static SharedPreferences settings;


    public static void loadPreferences(Context context) {
        settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
    }

    public static boolean isMusicEnabled() {
        return settings.getBoolean(MUSIC_ENABLED, true);
    }

    public static int getMusicVolume() {
        return settings.getInt(MUSIC_VOLUME, 5);
    }

    public static int getSFXVolume() {
        return settings.getInt(SFX_VOLUME, 5);
    }

    public static void setMusicEnabled(boolean enabled) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(MUSIC_ENABLED, enabled);
        editor.commit();
    }

    public static void setMusicVolume(int volume) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MUSIC_VOLUME, volume);
        editor.commit();
    }

    public static void setSfxVolume(int volume) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SFX_VOLUME, volume);
        editor.commit();
    }

}
