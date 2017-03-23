package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

import network.iut.org.shootingstars.game.model.Patterns;

/**
 * Created by Android on 21/03/2017.
 */

public class Boss extends AbstractEntity {

    private static final float ANGLE_INTERVAL = (float) Math.PI / 24f;
    private static final float FACTOR_CANNON_X = (float) (1.7 / 29.0);
    private static final float FACTOR_CANNON_Y = (float) (3.0 / 15.0);

    private float r;
    private float currentAngleClockwiseOne, currentAngleCounterClockwiseOne;
    private float currentAngleClockwiseTwo, currentAngleCounterClockwiseTwo;

    private int hp;
    private float currentDestroyAngle;


    public Boss(Context context) {
        super(context);
        setRelativeSpeed(0.15f);
        setTrajectory(Patterns.getInstance().getBossPattern());
        this.r = context.getResources().getDisplayMetrics().heightPixels;
        this.currentAngleClockwiseOne = 0;
        this.currentAngleCounterClockwiseOne = (float) Math.PI;
        this.currentAngleClockwiseTwo = (float) Math.PI;
        this.currentAngleCounterClockwiseTwo = 0;
        this.currentDestroyAngle = 0f;
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[0]);
    }

    public Shot[] shootWhips(Context context) {
        Shot[] shots = new Shot[4];
        shots[0] = shootClockwiseOne(context);
        shots[1] = shootCounterClockwiseOne(context);
        shots[2] = shootClockwiseTwo(context);
        shots[3] = shootCounterClockwiseTwo(context);
        return shots;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void decrementHp() {
        this.hp--;
    }

    public PointF getRandomExplosionPoint() {
        Random rnd = new Random();
        return new PointF(displayedFrame.getWidth() * rnd.nextFloat(), displayedFrame.getHeight() * rnd.nextFloat());
    }

    public boolean isDestroyed() {
        return this.hp <= 0;
    }

    private Shot shootClockwiseOne(Context context) {
        float angle = (float) (currentAngleClockwiseOne % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleClockwiseOne += ANGLE_INTERVAL;
        return new EnemyShot(context, getX(), getY(), x, y);
    }

    private Shot shootCounterClockwiseOne(Context context) {
        float angle = (float) (currentAngleCounterClockwiseOne % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleCounterClockwiseOne -= ANGLE_INTERVAL;
        return new EnemyShot(context, getX(), getY(), x, y);
    }

    private Shot shootClockwiseTwo(Context context) {
        float angle = (float) (currentAngleClockwiseTwo % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleClockwiseTwo += ANGLE_INTERVAL;
        return new EnemyShot(context, getX(), getY(), x, y);
    }

    private Shot shootCounterClockwiseTwo(Context context) {
        float angle = (float) (currentAngleCounterClockwiseTwo % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleCounterClockwiseTwo -= ANGLE_INTERVAL;
        return new EnemyShot(context, getX(), getY(), x, y);
    }

    public Shot shootRightCanon(Context context, float targetX, float targetY) {
        float newOriginX = getX() + displayedFrame.getWidth() * FACTOR_CANNON_X;
        float newOriginY = getY() + displayedFrame.getHeight() * FACTOR_CANNON_Y;
        return new EnemyShot(context, newOriginX, newOriginY, targetX, targetY);
    }

    public Shot shootLeftCanon(Context context, float targetX, float targetY) {
        float newOriginX = getX() - displayedFrame.getWidth() * FACTOR_CANNON_X;
        float newOriginY = getY() + displayedFrame.getHeight() * FACTOR_CANNON_Y;
        return new EnemyShot(context, newOriginX, newOriginY, targetX, targetY);
    }

    @Override
    public boolean collideWith(AbstractEntity other) {
        float x = getX() - displayedFrame.getWidth() / 2;
        float y = getY() - displayedFrame.getHeight() / 2;
        // Find the point of the rectangle closest to the circle
        float deltaX = other.getX() - Math.max(x, Math.min(other.getX(), x + displayedFrame.getWidth()));
        float deltaY = other.getY() - Math.max(y, Math.min(other.getY(), y + displayedFrame.getHeight()));
        return (deltaX * deltaX + deltaY * deltaY) < (other.getRadius() * other.getRadius());
    }

    @Override
    public void draw(Canvas canvas) {
        update();
        Bitmap rotatedFrame= displayedFrame;
        if (isDestroyed()) {
            Matrix mat = new Matrix();
            mat.postRotate(currentDestroyAngle);
            currentDestroyAngle += 0.1;
            rotatedFrame = Bitmap.createBitmap(displayedFrame, 0, 0, displayedFrame.getWidth(), displayedFrame.getHeight(), mat, true);
        }
        canvas.drawBitmap(rotatedFrame, getCenterX(), getCenterY(), null);
    }
}
