package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by Android on 16/03/2017.
 */

public class Shot extends AbstractEntity {

    private int boundWidth, boundHeight;
    private float angle;


    public Shot(Context context, float originX, float originY, float targetX, float targetY) {
        super(context);
        setRelativeSpeed(25f);
        setX(originX);
        setY(originY);
        this.boundHeight = context.getResources().getDisplayMetrics().heightPixels;
        this.boundWidth = context.getResources().getDisplayMetrics().widthPixels;
        if (targetX == originX) {
            this.angle = - (float) Math.PI / 2;
        } else {
            this.angle = (float) (Math.atan((targetY - originY) / (targetX - originX)));
        }
    }

    public void moveShoot() {
        setY(getY() + ((float) Math.sin(angle) * getRelativeSpeed()));
        setX(getX() + ((float) Math.cos(angle) * getRelativeSpeed()));
    }

    public boolean isOutside() {
        return getX() < 0 || getX() > boundWidth || getY() < 0 || getY() > boundHeight;
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[] {FrameHolder.getInstance().getPlayerShot()});
    }

}
