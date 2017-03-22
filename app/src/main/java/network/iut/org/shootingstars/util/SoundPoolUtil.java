package network.iut.org.shootingstars.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import network.iut.org.shootingstars.R;

/**
 * Created by Android on 16/03/2017.
 */

public class SoundPoolUtil {

    private static SoundPoolUtil instance;
    private SoundPool pool;

    private int idExplosion;
    private int idLaser;
    private int idStartBtn;
    private int idBackBtn;
    private int idR2D2;
    private int idWilhelm;
    private int idBossLaugn;

    private MediaPlayer player;
    private boolean running;


    private SoundPoolUtil(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            pool = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(10).build();
        }
        else {
            pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        idExplosion = pool.load(context, R.raw.explosion, 1);
        idLaser = pool.load(context, R.raw.laser_shot, 1);
        idStartBtn = pool.load(context, R.raw.startbtn, 1);
        idBackBtn = pool.load(context, R.raw.backbtn, 1);
        idR2D2 = pool.load(context, R.raw.r2d2, 1);
        idWilhelm = pool.load(context, R.raw.criwilhelm, 1);
        idBossLaugn = pool.load(context, R.raw.bosslaugh, 1);
        initBackgroundMusic(context);
    }

    private void initBackgroundMusic(final Context context) {
        player = MediaPlayer.create(context, R.raw.shooting_stars);
        player.setLooping(true); // Set looping
        changeMusicVolume(PreferencesUtil.getMusicVolume());
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SoundPoolUtil(context);
        }
    }

    public static SoundPoolUtil getInstance() {
        return instance;
    }

    public void changeMusicVolume(int newRatio) {
        float volume = 0.5f * (newRatio / 5f);
        player.setVolume(volume, volume);
    }

    public void playExplosion() {
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idExplosion, volume, volume, 1, 0, 1);
    }

    public void playLaser() {
        float volume = 0.1f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idLaser, volume, volume, 1, 0, 1);
    }

    public void playStartBtn(){
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idStartBtn, volume, volume, 1, 0, 1);
    }

    public void playBackBtn(){
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idBackBtn, volume, volume, 1, 0, 1);
    }
    public void playR2D2(){
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idR2D2, volume, volume, 1, 0, 1);
    }
    public void playWilhelm(){
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idWilhelm, volume, volume, 1, 0, 1);
    }
    public void playidBossLaugn(){
        float volume = 0.5f * (PreferencesUtil.getSFXVolume() / 5f);
        pool.play(idBossLaugn, volume, volume, 1, 0, 1);
    }

    public void startBackgroundMusic() {
        if (!running && PreferencesUtil.isMusicEnabled()) {
            player.start();
            running = true;
        }
    }

    public void stopBackgroundMusic() {
        if (running) {
            player.stop();
            running = false;
        }
    }

}
