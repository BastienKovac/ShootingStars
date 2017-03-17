package network.iut.org.flappydragon.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import network.iut.org.flappydragon.interfaces.Drawable;
import network.iut.org.flappydragon.util.SoundPoolUtil;

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
            Bitmap toDraw = frames[currentIndex];
            canvas.drawBitmap(toDraw, x - (toDraw.getWidth() / 2), y - (toDraw.getHeight() / 2), null);
            currentIndex++;
        }
    }
}
