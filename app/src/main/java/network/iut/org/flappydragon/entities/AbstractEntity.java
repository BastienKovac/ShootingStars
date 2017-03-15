package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Arrays;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.interfaces.Collidable;
import network.iut.org.flappydragon.interfaces.Drawable;

/**
 * Created by Android on 13/03/2017.
 */

public abstract class AbstractEntity implements Drawable {

    private float x, y;
    private double relativeSpeed;

    private double updateTimer;

    private Collidable hitbox;

    protected Bitmap displayedFrame;
    protected Bitmap[] frames;
    protected int currentFrame;

    public AbstractEntity(Context context) {
        this.frames = getFrames(context);
        this.currentFrame = 0;
        this.displayedFrame = frames[0];
        this.relativeSpeed = 1;
    }

    public void setHitbox(Collidable hitbox) {
        this.hitbox = hitbox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRelativeSpeed(double s) {
        this.relativeSpeed = s;
    }

    public double getRelativeSpeed() {
        return relativeSpeed;
    }

    public void moveTo(float targetX, float targetY) {
        float diffX = (targetX - displayedFrame.getWidth() / 2) - this.x;
        float diffY = (targetY - displayedFrame.getHeight() * 1.5f) - this.y;

        this.x += diffX * relativeSpeed;
        this.y += diffY * relativeSpeed;
    }

    @Override
    public void update() {
        if (updateTimer >= 1) {
            int nbFrames = frames.length;
            this.displayedFrame = frames[currentFrame];
            this.currentFrame = (this.currentFrame + 1) % nbFrames;
            updateTimer = 0;
        } else {
            updateTimer += relativeSpeed;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
           update();
           canvas.drawBitmap(displayedFrame, x, y, null);
        }
    }

    protected abstract Bitmap[] getFrames(Context context);

}
