package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;

import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 15/03/2017.
 */

public class CircleEnemyShip extends AbstractEntity {

    public CircleEnemyShip(Context context) {
        super(context);
        setRelativeSpeed(0.1f);
    }

    @Override
    protected Bitmap[] getFrames(Context context) {
        Bitmap[] frames = new Bitmap[6];
        for (int i = 0 ; i < frames.length ; i++ ) {
            String res = "circleenemy1_ship_frame" + (i + 1);
            int id = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
            frames[i] = GraphicsUtil.getBitmap(context, id);
        }
        return frames;
    }

}
