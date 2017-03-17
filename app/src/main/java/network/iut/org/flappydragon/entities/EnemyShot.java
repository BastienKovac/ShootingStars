package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 17/03/2017.
 */

public class EnemyShot extends Shot {


    public EnemyShot(Context context, float originX, float originY, float targetX, float targetY) {
        super(context, originX, originY, targetX, targetY);
        setRelativeSpeed(12.5f);
    }

    @Override
    public void moveShoot() {
        setY(getY() + getRelativeSpeed());
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        return new AbstractMap.SimpleEntry<>(getClass().getName(), new Bitmap[] {GraphicsUtil.getBitmap(context, R.drawable.shot_frame2)});
    }
}
