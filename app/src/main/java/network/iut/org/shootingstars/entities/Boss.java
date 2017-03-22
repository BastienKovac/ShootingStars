package network.iut.org.shootingstars.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.AbstractMap;
import java.util.Map;

import network.iut.org.shootingstars.R;
import network.iut.org.shootingstars.game.model.Patterns;
import network.iut.org.shootingstars.util.GraphicsUtil;

/**
 * Created by Android on 21/03/2017.
 */

public class Boss extends AbstractEntity {


    public Boss(Context context) {
        super(context);
        setRelativeSpeed(0.15f);
        setTrajectory(Patterns.getInstance().getBossPattern());
    }

    @Override
    protected Map.Entry<String, Bitmap[]> getFrames(Context context) {
        Bitmap[] frames = new Bitmap[4];
        frames[0] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame1);
        frames[1] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame2);
        frames[2] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame3);
        frames[3] = GraphicsUtil.getBitmap(context, R.drawable.boss_frame4);
        return new AbstractMap.SimpleEntry<>(getClass().getName(), frames);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        canvas.drawRect(getX(), getY(), displayedFrame.getWidth() * 0.75f, displayedFrame.getHeight() * 0.7f, p);
        super.draw(canvas);
    }
}
