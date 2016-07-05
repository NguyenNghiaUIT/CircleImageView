package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by nguyennghia on 04/07/2016.
 */
public class BitmapUtils {
    public static Bitmap centerCropImage(Bitmap src, float widthView, float heightView) {
        Bitmap des = null;
        Bitmap source = Bitmap.createBitmap(src);
        if (source != null) {
            Matrix matrix = new Matrix();
            int bitmapWidth = source.getWidth();
            int bitmapHeight = source.getHeight();
            float scaleX = widthView / (bitmapWidth * 1.0f);
            float scaleY = heightView / (bitmapHeight * 1.0f);
            float scale = Math.max(scaleX, scaleY);
            float width = bitmapWidth * scale;
            float height = bitmapHeight * scale;
            matrix.postScale(scale, scale);
            float startX = Math.abs(widthView - width) * 0.5f;
            float startY = Math.abs(heightView - height) * 0.5f;
            des = Bitmap.createBitmap(source, (int) startX, (int) startY, (int) (bitmapWidth - startX), (int) (bitmapHeight - startY), matrix, true);
        }
        return des;
    }
}
