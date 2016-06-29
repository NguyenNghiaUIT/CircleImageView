package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nguyennghia on 29/06/2016.
 */
public class CustomView extends View {

    private TextPaint mPaint;
    private String mText = "paint's style, used for controlling how primitives' geometries are in You can split the text into two parts, main and remainder.";

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
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 500);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CharSequence str = TextUtils.ellipsize(mText, mPaint, getWidth() - 100, TextUtils.TruncateAt.END);
        canvas.drawText(str, 0, str.length(), 100, 100, mPaint);
    }

}
