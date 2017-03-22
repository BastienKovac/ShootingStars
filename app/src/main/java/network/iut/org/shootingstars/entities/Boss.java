package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.shootingstars.game.model.Patterns;

/**
 * Created by Android on 21/03/2017.
 */

public class Boss extends AbstractEntity {

    private static final float ANGLE_INTERVAL = (float) Math.PI / 24;
    private static final float FACTOR_CANNON_X = (float) (1.7 / 29.0);
    private static final float FACTOR_CANNON_Y = (float) (3.0 / 15.0);

    private float r;
    private float currentAngleClockwiseOne, currentAngleCounterClockwiseOne;
    private float currentAngleClockwiseTwo, currentAngleCounterClockwiseTwo;


    public Boss(Context context) {
        super(context);
        setRelativeSpeed(0.15f);
        setTrajectory(Patterns.getInstance().getBossPattern());
        this.r = context.getResources().getDisplayMetrics().heightPixels;
        this.currentAngleClockwiseOne = 0;
        this.currentAngleCounterClockwiseOne = (float) Math.PI;
        this.currentAngleClockwiseTwo = (float) Math.PI;
        this.currentAngleCounterClockwiseTwo = 0;
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

}
