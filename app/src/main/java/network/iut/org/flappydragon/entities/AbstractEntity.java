package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.interfaces.Collidable;
import network.iut.org.flappydragon.interfaces.Drawable;

/**
 * Created by Android on 13/03/2017.
 */

public abstract class AbstractEntity implements Collidable, Drawable {

    private boolean scaleUp;
    private GameView view;

    private float x, y;
    private double relativeSpeed;

    protected Bitmap displayedFrame;
    protected Bitmap[] frames;
    protected int currentFrame;


    public AbstractEntity(Context context, GameView view, boolean scaleUp) {
        this.view = view;
        this.scaleUp = scaleUp;
        this.frames = getFrames(context);
        this.currentFrame = 0;
        this.displayedFrame = frames[0];
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setRelativeSpeed(double s) {
        this.relativeSpeed = s;
    }

    public double getRelativeSpeed() {
        return relativeSpeed;
    }

    @Override
    public void update() {
        int nbFrames = frames.length;
        this.currentFrame = (this.currentFrame + 1) % nbFrames;
        this.displayedFrame = frames[currentFrame];
    }

    @Override
    public void draw(Canvas canvas) {
        if (scaleUp) {
            canvas.drawBitmap(displayedFrame, x, y, null);
        } else {
            canvas.drawBitmap(displayedFrame, new Rect(0, 0, displayedFrame.getWidth(), displayedFrame.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        }
    }

    protected abstract Bitmap[] getFrames(Context context);

}
