package com.example.nguyennghia.circleimageview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by nguyennghia on 04/07/2016.
 */
public class CircleColorDrawable extends Drawable{
    private static final String TAG = "CircleColorDrawable";
    private Paint mPaint;
    private int mColor;
    private float mWidth;
    private float mHeight;

    public CircleColorDrawable(@ColorInt int color){
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }
    @Override
    public void draw(Canvas canvas) {
        mWidth = getBounds().width();
        mHeight = getBounds().height();
        float radius = mWidth / 2.0f;
        canvas.drawCircle(radius, radius, radius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
