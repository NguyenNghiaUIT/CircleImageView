package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nguyennghia on 29/06/2016.
 */
public class CustomView extends View {
    private static final String TAG = "CustomView";
    private TextPaint mPaint;
    private String mText = "paint's style, used for controlling how primitives' geometries are in You can split the text into two parts, main and remainder.";
    private Drawable mDrawable;
    private CircleColorDrawable circleColorDrawable;
    private Rect mCircleBound = new Rect(0, 0, 500, 500);
    private CircleBitmapDrawable circleBitmapDrawable;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

       mPaint = new TextPaint();
        mPaint.setTextSize(context.getResources().getDimension(R.dimen.font_demo));
        mPaint.setColor(Color.BLACK);
        mPaint.setTypeface(Typeface.create(mPaint.getTypeface(), Typeface.BOLD_ITALIC));
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);

//        mDrawable = getResources().getDrawable(R.drawable.ava1);
//
//        circleColorDrawable = new CircleColorDrawable(context.getResources().getColor(R.color.default_color));
//        circleBitmapDrawable = new CircleBitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.ava1));
//        Log.d(TAG, "onDraw: PaddingLeft " + getPaddingLeft());
//        circleColorDrawable.setAlpha(20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*
        Log.d(TAG, "onDraw: ");
        canvas.translate(200, 200);
        mDrawable.setAlpha(10);
        Log.d(TAG, "onDraw: ");
        mDrawable.setBounds(0, 0, 200, 300);
        mDrawable.draw(canvas);*/

        // canvas.drawRoundRect(new RectF(0,0,300,300), 150, 150, mPaint);
        CharSequence str = TextUtils.ellipsize(mText, mPaint, getWidth() - 100, TextUtils.TruncateAt.END);
        canvas.drawText(str, 0, str.length(), 100, 100, mPaint);
//        canvas.translate(300, 200);
//
//        circleColorDrawable.setBounds(mCircleBound);
//        circleColorDrawable.draw(canvas);
//
//        circleBitmapDrawable.setBounds(mCircleBound);
//        circleBitmapDrawable.draw(canvas);
    }

}
