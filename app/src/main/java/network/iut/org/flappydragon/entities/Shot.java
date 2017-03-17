package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by Android on 16/03/2017.
 */

public class Shot extends AbstractEntity {

    private float targetX, targetY;
    private int boundWidth, boundHeight;


    public Shot(Context context, float originX, float originY, float targetX, float targetY) {
        super(context);
        setRelativeSpeed(25f);
        setX(originX);
        setY(originY);
        this.targetX = targetX;
        this.targetY = targetY;
        this.boundHeight = context.getResources().getDisplayMetrics().heightPixels;
        this.boundWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void moveShoot() {
        setY(getY() - getRelativeSpeed());
    }

    public boolean isOutside() {
        return getX() < 0 || getX() > boundWidth || getY() < 0 || getY() > boundHeight;
    }

    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[] {FrameHolder.getInstance().getPlayerShot()});
    }

}
