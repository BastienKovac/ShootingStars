package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.game.model.Patterns;
import network.iut.org.shootingstars.util.GraphicsUtil;

/**
 * Created by Android on 21/03/2017.
 */

public class Boss extends AbstractEntity {

    private static final float ANGLE_INTERVAL = (float) Math.PI / 24;
    private static final float FACTOR_CANNON_X = (float) (1.7 / 29.0);
    private static final float FACTOR_CANNON_Y = (float) (3.0 / 15.0);

    private float r;
    private float currentAngleClockwise, currentAngleCounterClockwise;


    public Boss(Context context) {
        super(context);
        setRelativeSpeed(0.15f);
        setTrajectory(Patterns.getInstance().getBossPattern());
        this.r = context.getResources().getDisplayMetrics().heightPixels;
        this.currentAngleClockwise = 0;
        this.currentAngleCounterClockwise = (float) Math.PI;
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[0]);
    }

    public Shot shootClockwise(Context context) {
        float angle = (float) (currentAngleClockwise % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleClockwise += ANGLE_INTERVAL;
        return new EnemyShot(context, getX(), getY(), x, y);
    }

    public Shot shootCounterClockwise(Context context) {
        float angle = (float) (currentAngleCounterClockwise % (2 * Math.PI));
        float x = (float) (getX() + r * Math.cos(angle));
        float y = (float) (getY() + r * Math.sin(angle));
        currentAngleCounterClockwise -= ANGLE_INTERVAL;
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
