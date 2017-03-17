package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Deque;
import java.util.Map;

import network.iut.org.flappydragon.interfaces.Drawable;

/**
 * Created by Android on 13/03/2017.
 */

public abstract class AbstractEntity implements Drawable {

    private float x, y;
    private float relativeSpeed;
    private float radius;

    private int age;

    private double updateTimer;

    protected Bitmap displayedFrame;
    protected int currentFrame;

    private Deque<PointF> trajectory;


    public AbstractEntity(Context context) {
        FrameHolder.getInstance().addEntry(getFrames(context));
        this.currentFrame = 0;
        this.displayedFrame = FrameHolder.getInstance().getFrame(getClass().getName(), 0);
        this.radius = Math.min(displayedFrame.getWidth() / 2, displayedFrame.getHeight() / 2);
        this.relativeSpeed = 1;
    }

    public void setTrajectory(Deque<PointF> trajectory) {
        this.trajectory = trajectory;
        if (!trajectory.isEmpty()) {
            PointF origin = trajectory.pop();
            this.x = origin.x;
            this.y = origin.y;
        }
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

    public float getCenterX() {
        return x - displayedFrame.getWidth() / 2;
    }

    public float getCenterY() {
        return y - displayedFrame.getHeight() / 2;
    }

    public void setRelativeSpeed(float s) {
        this.relativeSpeed = s;
    }

    public float getRelativeSpeed() {
        return relativeSpeed;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public int getAge() {
        return age;
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

    public Shot shoot(Context context, float targetX, float targetY) {
        return new Shot(context, x, y, targetX, targetY);
    }

    @Override
    public void update() {
        age++;
        if (updateTimer >= 1) {
            int nbFrames = FrameHolder.getInstance().getNbFrames(getClass().getName());
            this.displayedFrame = FrameHolder.getInstance().getFrame(getClass().getName(), currentFrame);
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
            canvas.drawBitmap(displayedFrame, getCenterX(), getCenterY(), null);
        }
    }

    public boolean collideWith(AbstractEntity other) {
        float dist = (getX() - other.getX()) * (getX() - other.getX());
        dist += (getY() - other.getY()) * (getY() - other.getY());
        dist = (float) Math.sqrt(dist);
        return dist < Math.max(radius, other.getRadius());
    }

    protected abstract Map.Entry<String, Bitmap[]> getFrames(Context context);

}
