package network.iut.org.flappydragon.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import network.iut.org.flappydragon.R;

/**
 * Created by Android on 16/03/2017.
 */

public class SoundPoolUtil {

    private static SoundPoolUtil instance;
    private SoundPool pool;

    private int idExplosion;
    private int idLaser;

    private Thread bgMusic;
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
        initBackgroundMusic(context);
    }

    private void initBackgroundMusic(final Context context) {
        bgMusic = new Thread() {
            @Override
            public void run() {
                player = MediaPlayer.create(context, R.raw.shooting_stars);
                player.setLooping(true); // Set looping
                player.setVolume(0.2f, 0.2f);
                player.start();
            }
        };
    }

    public static void init(Context context) {
        instance = new SoundPoolUtil(context);
    }

    public static SoundPoolUtil getInstance() {
        return instance;
    }

    public void playExplosion() {
        pool.play(idExplosion, 1.5f, 1.5f, 1, 0, 1);
    }

    public void playLaser() {
        pool.play(idLaser, 0.10f, 0.10f, 1, 0, 1);
    }

    public void startBackgroundMusic() {
        if (!running) {
            bgMusic.start();
            running = true;
        }
    }

    public void stopBackgroundMusic() {
        if (running) {
            bgMusic.interrupt();
            running = false;
        }
    }

}
