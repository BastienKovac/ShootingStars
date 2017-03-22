package network.iut.org.shootingstars.game.view.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import network.iut.org.shootingstars.util.GraphicsUtil;

public class Background {

    private Bitmap bg, bgReversed;

    private int width;
    private int height;
    private boolean reversedFirst;
    private float speed;

    private int yClip;

    public Background(Context context, int w, int h, String name, float s) {
        int resID = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        reversedFirst = false;
        yClip = 0;
        speed = s;
        bg = GraphicsUtil.getBitmap(context, w, h, resID);

        float ratio = bg.getHeight() / bg.getWidth();
        int newH = (int) (w * ratio);

        bg = Bitmap.createScaledBitmap(bg, w, newH, false);

        width = bg.getWidth();
        height = bg.getHeight();

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bgReversed = Bitmap.createBitmap(bg, 0, 0, width, height, matrix, true);
    }

    public void update(long fps){
        yClip += speed/fps;
        if (yClip >= height) {
            yClip = 0;
            reversedFirst = !reversedFirst;
        } else if (yClip <= 0) {
            yClip = height;
            reversedFirst = !reversedFirst;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getyClip() {
        return yClip;
    }

    public boolean isReversedFirst() {
        return reversedFirst;
    }

    public Bitmap getBg() {
        return bg;
    }

    public Bitmap getBgReversed() {
        return bgReversed;
    }
}
