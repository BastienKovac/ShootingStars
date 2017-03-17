package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 13/03/2017.
 */

public class PlayerShip extends AbstractEntity {

    public PlayerShip(Context context) {
        super(context);
        setRelativeSpeed(0.14f);
    }

    @Override
    public void moveTo(float targetX, float targetY) {
        float diffX = (targetX - displayedFrame.getWidth() / 2) - getX();
        float diffY = (targetY - displayedFrame.getHeight() * 0.75f) - getY();

        setX(getX() + diffX * getRelativeSpeed());
        setY(getY() + diffY * getRelativeSpeed());
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        Bitmap[] frames = new Bitmap[4];
        frames[0] = GraphicsUtil.getBitmap(context, R.drawable.player_ship_frame1);
        frames[1] = GraphicsUtil.getBitmap(context, R.drawable.player_ship_frame2);
        frames[2] = GraphicsUtil.getBitmap(context, R.drawable.player_ship_frame3);
        frames[3] = GraphicsUtil.getBitmap(context, R.drawable.player_ship_frame4);
        return new AbstractMap.SimpleEntry<>(getClass().getName(), frames);
    }

}
