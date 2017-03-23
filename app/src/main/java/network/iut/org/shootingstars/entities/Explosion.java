package network.iut.org.shootingstars.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.Random;

import network.iut.org.shootingstars.interfaces.Drawable;

/**
 * Created by Android on 16/03/2017.
 */

public class Explosion implements Drawable {

    private float x, y;
    private int currentIndex;
    private Bitmap[] frames;


    public Explosion(AbstractEntity source) {
        this.x = source.getX();
        this.y = source.getY();
        this.frames = FrameHolder.getInstance().getFramesExplosion();
        this.currentIndex = 0;
    }

    public Explosion(PointF source) {
        this.x = source.x;
        this.y = source.y;
        this.frames = FrameHolder.getInstance().getFramesExplosion();
        Random rnd = new Random();
        this.currentIndex = rnd.nextInt((0 + 60) + 1) - 60;
    }

    public boolean isStarted() {
        return currentIndex == 0;
    }

    public boolean isDone() {
        return currentIndex == this.frames.length;
    }

    @Override
    public void update() {
        // Nothing
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isDone()) {
            if (currentIndex >= 0) {
                Bitmap toDraw = frames[currentIndex];
                canvas.drawBitmap(toDraw, x - (toDraw.getWidth() / 2), y - (toDraw.getHeight() / 2), null);
            }
            currentIndex++;
        }
    }
}
