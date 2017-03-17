package network.iut.org.flappydragon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

public class GraphicsUtil {

    private static final int DEFAULT_DENSITY = 1024;

    public static Bitmap getBitmap(Context context, int bitmapId) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmapOptions.inScaled = true;
        bitmapOptions.inDensity = DEFAULT_DENSITY;
        bitmapOptions.inTargetDensity = (int)(getScaleFactor(context) * DEFAULT_DENSITY);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), bitmapId, bitmapOptions);
        b.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        return b;
    }

    public static Bitmap getBitmap(Context context, int w, int h, int bitmapId) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), bitmapId, bitmapOptions);
        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, w, h);
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmapOptions.inDither = true;
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), bitmapId, bitmapOptions);
        return b;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        float height = options.outHeight;
        float width = options.outWidth;
        double inSampleSize = 1D;

        if (height > reqHeight || width > reqWidth)
        {
            int halfHeight = (int)(height / 2);
            int halfWidth = (int)(width / 2);

            // Calculate a inSampleSize that is a power of 2 - the decoder will use a value that is a power of two anyway.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return (int)inSampleSize;
    }

    public static float getScaleFactor(Context context){
        return context.getResources().getDisplayMetrics().heightPixels / 1066f;
    }

}
