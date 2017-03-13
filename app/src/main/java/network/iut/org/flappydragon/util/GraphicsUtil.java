package network.iut.org.flappydragon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GraphicsUtil {
    private static final int DEFAULT_DENSITY = 1024;

    public static Bitmap getScaledBitmapAlpha8(Context context, int bitmapId) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmapOptions.inScaled = true;
        bitmapOptions.inDensity = DEFAULT_DENSITY;
        bitmapOptions.inTargetDensity = (int)(getScaleFactor(context) * DEFAULT_DENSITY);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), bitmapId, bitmapOptions);
        b.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        return b;
    }

    public static float getScaleFactor(Context context){
        return context.getResources().getDisplayMetrics().heightPixels / 1066f;
    }
}
