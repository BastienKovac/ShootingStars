package network.iut.org.flappydragon.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.util.GraphicsUtil;

/**
 * Created by Android on 13/03/2017.
 */

public class PlayerShip extends AbstractEntity {

    public PlayerShip(Context context, GameView view) {
        super(context, view, false);
    }

    @Override
    protected Bitmap[] getFrames(Context context) {
        Bitmap[] frames = new Bitmap[4];
        frames[0] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame1);
        frames[1] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame2);
        frames[2] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame3);
        frames[2] = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.player_ship_frame4);
        return frames;
    }

    @Override
    public boolean collideWith(double x, double y) {
        return false;
    }

}
