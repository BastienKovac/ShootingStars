package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.interfaces.Collidable;
import network.iut.org.flappydragon.interfaces.Drawable;

/**
 * Created by Android on 13/03/2017.
 */

public abstract class AbstractEntity implements Drawable {

    private float x, y;
    private float relativeSpeed;

    private double updateTimer;

    protected Bitmap displayedFrame;
    protected Bitmap[] frames;
    protected int currentFrame;

    private Deque<PointF> trajectory;


    public AbstractEntity(Context context) {
        this.frames = getFrames(context);
        this.currentFrame = 0;
        this.displayedFrame = frames[0];
        this.relativeSpeed = 1;
    }

    public void setTrajectory(Deque<PointF> trajectory) {
        this.trajectory = trajectory;
        PointF origin = trajectory.pop();
        this.x = origin.x;
        this.y = origin.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCenterX() {
        return x - displayedFrame.getWidth() / 2;
    }

    public float getCenterY() {
        return y - displayedFrame.getHeight() / 4;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRelativeSpeed(float s) {
        this.relativeSpeed = s;
    }

    public float getRelativeSpeed() {
        return relativeSpeed;
    }

    public void moveTo(float targetX, float targetY) {
        float diffX = targetX - this.x;
        float diffY = targetY - this.y;

        this.x += diffX * relativeSpeed;
        this.y += diffY * relativeSpeed;
    }

    public boolean followTrajectory() {
        if (trajectory != null && !trajectory.isEmpty()) {
            PointF p = trajectory.pop();
            moveTo(p.x, p.y);
            return true;
        }
        return false;
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
           canvas.drawBitmap(displayedFrame, x - displayedFrame.getWidth() / 2, y - displayedFrame.getHeight() / 2, null);
        }
    }

    protected abstract Bitmap[] getFrames(Context context);

}
