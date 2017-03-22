package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.shootingstars.util.GraphicsUtil;

/**
 * Created by Android on 15/03/2017.
 */

public class CircleEnemyShip extends AbstractEntity {

    public CircleEnemyShip(Context context) {
        super(context);
        setRelativeSpeed(0.1f);
        setRadius(getRadius() / 1.5f);
    }

    @Override
    public Shot shoot(Context context, float targetX, float targetY) {
        return new EnemyShot(context, getX(), getY(), targetX, targetY);
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        Bitmap[] frames = new Bitmap[6];
        for (int i = 0 ; i < frames.length ; i++ ) {
            String res = "circleenemy1_ship_frame" + (i + 1);
            int id = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
            frames[i] = GraphicsUtil.getBitmap(context, id);
        }
        return new AbstractMap.SimpleEntry<>(getClass().getName(), frames);
    }

}
