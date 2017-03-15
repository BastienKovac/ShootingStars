package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import network.iut.org.flappydragon.game.view.GameView;
import network.iut.org.flappydragon.util.GraphicsUtil;

public class Background {
    private GameView view;
    private Bitmap background1;

    public Background(Context context, GameView view) {
        background1 = GraphicsUtil.getScaledBitmapAlpha8(context, R.drawable.layer_0);
        this.view = view;
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawBitmap(background1, new Rect(0, 0, background1.getWidth(), background1.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        }
    }

    public int getWidth() {
        return background1.getWidth();
    }

    public int getHeight() {
        return background1.getHeight();
    }

}
