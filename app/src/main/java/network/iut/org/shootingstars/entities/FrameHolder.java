package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.game.view.background.Background;
import network.iut.org.shootingstars.util.GraphicsUtil;

/**
 * Created by Android on 16/03/2017.
 */

public class FrameHolder {

    private static FrameHolder instance;

    private Map<String, Bitmap[]> frames;
    private List<Background> backgrounds;
    private Bitmap[] framesExplosion;

    private Bitmap playerShot;

    private boolean backgroundInitialized;


    private FrameHolder() {
        this.frames = new HashMap<>();
        this.backgrounds = new ArrayList<>();
    }

    public static FrameHolder getInstance() {
        if (instance == null) {
            instance = new FrameHolder();
        }
        return instance;
    }

    public void initBackgrounds(Context context, int width, int height) {
        if (!backgroundInitialized) {
            backgrounds.add(new Background(context, width, height, "layer_0", 100));
            backgrounds.add(new Background(context, width, height, "layer_1", 125));
            backgrounds.add(new Background(context, width, height, "layer_2", 300));
            backgroundInitialized = true;
        }
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public void initPlayerShot(Context context) {
        this.playerShot = GraphicsUtil.getBitmap(context, R.drawable.shot_frame1);
    }

    public void initBossFrames(Context context) {
        Bitmap[] frames = new Bitmap[4];
        frames[0] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame1);
        frames[1] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame2);
        frames[2] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame3);
        frames[3] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame4);
        this.frames.put(Boss.class.getName(), frames);
    }

    public void initExplosionsFrame(Context context) {
        this.framesExplosion = new Bitmap[9];
        Bitmap frame1 = GraphicsUtil.getBitmap(context, R.drawable.explo_frame1);
        Bitmap frame2 = GraphicsUtil.getBitmap(context, R.drawable.explo_frame2);
        Bitmap frame3 = GraphicsUtil.getBitmap(context, R.drawable.explo_frame3);
        for (int i = 0 ; i < framesExplosion.length ; i++) {
            if (i < 3) {
                framesExplosion[i] = frame1;
            } else if (i < 6) {
                framesExplosion[i] = frame2;
            } else {
                framesExplosion[i] = frame3;
            }
        }
    }

    public void addEntry(Map.Entry<String, Bitmap[]> entry) {
        if (frames.containsKey(entry.getKey())) {
            return;
        }
        this.frames.put(entry.getKey(), entry.getValue());
    }

    public Bitmap getFrame(String className, int index) {
        return this.frames.get(className)[index];
    }

    public Bitmap getPlayerShot() {
        return playerShot;
    }

    public int getNbFrames(String className) {
        return this.frames.get(className).length;
    }

    public Bitmap[] getFramesExplosion() {
        return framesExplosion;
    }
}
