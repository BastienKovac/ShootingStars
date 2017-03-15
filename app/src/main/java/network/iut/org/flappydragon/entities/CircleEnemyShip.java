package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 15/03/2017.
 */

public class CircleEnemyShip extends AbstractEntity {

    public CircleEnemyShip(Context context, GameView view) {
        super(context, view);
        setRelativeSpeed(0.10);
    }

    @Override
    protected Bitmap[] getFrames(Context context) {
        Bitmap[] frames = new Bitmap[6];
        for (int i = 0 ; i < frames.length ; i++ ) {
            String res = "circleenemy1_ship_frame" + (i + 1);
            int id = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
            frames[i] = GraphicsUtil.getScaledBitmapAlpha8(context, id);
        }
        return frames;
    }

    @Override
    public boolean collideWith(float x, float y) {
        return false;
    }

}
