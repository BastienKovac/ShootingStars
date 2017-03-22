package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by Android on 16/03/2017.
 */

public class Shot extends AbstractEntity {

    private int boundWidth, boundHeight;
    private float dist;
    private int frame;

    private float originX, originY, targetX, targetY;


    public Shot(Context context, float originX, float originY, float targetX, float targetY) {
        super(context);
        setRelativeSpeed(25f);
        setX(originX);
        setY(originY);
        this.boundHeight = context.getResources().getDisplayMetrics().heightPixels;
        this.boundWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.originX = originX;
        this.originY = originY;
        this.targetX = targetX;
        this.targetY = targetY;
        dist = (targetX - originX) * (targetX - originX);
        dist += (targetY - originY) * (targetY - originY);
        dist = (float) Math.sqrt(dist);
    }

    public void moveShoot() {
        float d = (frame + 1) * getRelativeSpeed();
        float ratio = d / dist;
        setX((1 - ratio) * originX + ratio * targetX);
        setY((1 - ratio) * originY + ratio * targetY);
        frame++;
    }

    public boolean isOutside() {
        return getX() < 0 || getX() > boundWidth || getY() < 0 || getY() > boundHeight;
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[] {FrameHolder.getInstance().getPlayerShot()});
    }

}
