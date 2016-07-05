package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by nguyennghia on 04/07/2016.
 */
public class CircleBitmapDrawable extends Drawable {
    private static final String TAG = "CircleBitmapDrawable";
    private float mWidth;
    private float mHeight;
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;

    public CircleBitmapDrawable(Bitmap bitmap) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        float radius = mWidth / 2.0f;
        if (mWidth != getBounds().width() || mHeight != getBounds().height()) {
            mWidth = getBounds().width();
            mHeight = getBounds().height();
            mBitmapShader = new BitmapShader(BitmapUtils.centerCropImage(mBitmap, mWidth, mHeight), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        mPaint.setShader(mBitmapShader);
        canvas.drawRoundRect(new RectF(0, 0, mWidth, mHeight), mWidth / 2, mWidth / 2, mPaint);
        mPaint.setShader(null);

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
