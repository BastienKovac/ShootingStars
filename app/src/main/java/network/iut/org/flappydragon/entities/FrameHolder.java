package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 16/03/2017.
 */

public class FrameHolder {

    private static FrameHolder instance;

    private Map<String, Bitmap[]> frames;
    private Bitmap[] framesExplosion;

    private Bitmap playerShot;


    private FrameHolder() {
        this.frames = new HashMap<>();
    }

    public static FrameHolder getInstance() {
        if (instance == null) {
            instance = new FrameHolder();
        }
        return instance;
    }

    public void initPlayerShot(Context context) {
        this.playerShot = GraphicsUtil.getBitmap(context, R.drawable.shot_frame1);
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
