package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by nguyennghia on 29/06/2016.
 */
public class ChatView extends View {
    private static final String TAG = "ChatView";
    private static final boolean DEBUG = false;
    private static final int ALPHA_DEFAULT = 255;
    private float mHeight;
    private float mWidth;

    private int mSize;
    private Paint[] mPaints; // using for draw bitmap

    private Paint mUnReadPaint; //using for draw circle Unread Count Notificition
    private Paint mUnReadPaintText; //using for draw text in circle Unread Count Notification
    private String mUnReadText;
    private float mPaddingUnread;
    private float mUnreadTextSize;
    private int mUnreadMinHeight;
    private int mUnreadMinWidth;
    private int mUnreadColor;
    private int mUnreadTextColor;


    private TextPaint mTitlePaint;
    private String mTitleText;
    private int mTitleMarginTop;
    private int mTitleMarginBottom;
    private int mTitleMarginLeft;
    private int mTitleMarginRight;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private TextPaint mContentPaint;
    private String mContentText;
    private int mContentMarginTop;
    private int mContentMarginBottom;
    private int mContentMarginLeft;
    private int mContentMarginRight;
    private int mContentTextColor;
    private int mContentTextSize;


    private TextPaint mStatusPaint;
    private String mStatusText;
    private int mStatusMarginTop;
    private int mStatusMarginBottom;
    private int mStatusMarginLeft;
    private int mStatusMarginRight;
    private int mStatusTextColor;
    private int mStatusTextSize;


    private float mWidthImage;
    private float mHeightImage;
    private Paint mPaintText;

    private Paint mPaintDivider;
    private int mDividerHeight;
    private int mDividerColor;
    private boolean mIsDrawDivider;

    private float mHeightDraw;
    private Resources mResource;
    private String mText;
    private Rect mRectBoundText;

    private boolean mIsAnimation0;
    private boolean mIsAnimation1;
    private boolean mIsAnimation2;
    private boolean mIsAnimation3;

    private static final int ANIMATION_DURATION = 300;
    private static final int TIME_REFESH = 10;
    private static final int ALPHA_STEP = ALPHA_DEFAULT / (ANIMATION_DURATION / TIME_REFESH);

    private int currentAlpha0;
    private int currentAlpha1;
    private int currentAlpha2;
    private int currentAlpha3;

    private int currentDefaultAlphal0 = ALPHA_DEFAULT;
    private int currentDefaultAlphal1 = ALPHA_DEFAULT;
    private int currentDefaultAlphal2 = ALPHA_DEFAULT;
    private int currentDefaultAlphal3 = ALPHA_DEFAULT;

    private boolean mIsDrawBitmap0;
    private boolean mIsDrawBitmap1;
    private boolean mIsDrawBitmap2;
    private boolean mIsDrawBitmap3;

    private Drawable mDrawableDefault0;
    private Drawable mDrawableDefault1;
    private Drawable mDrawableDefault2;
    private Drawable mDrawableDefault3;
    private Rect mDrawableBound = new Rect();

    private Paint mPaintDefault;

    private boolean mIsDrawUnRead;
    private ImageType mImageType;

    private Paint mPaintMark;


    public enum ImageType {
        TYPE_1, //represent for 1 bitmap in view
        TYPE_2,  //represent for 2 bitmap in view
        TYPE_3, //represent for 3 bitmap in view
        TYPE_4,  //represent for 4 bitmap in view
        TYPE_5   //represent for 3 bitmap and text in view
    }

    public void setDrawableDefault(Drawable drawableDefault) {
        mDrawableDefault0 = mDrawableDefault1 = mDrawableDefault2 = mDrawableDefault3 = drawableDefault;
        invalidate();
    }

    public ChatView(Context context) {
        this(context, null);
    }

    public ChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResource = getResources();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChatView, 0, 0);

        try {
            mTitleMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_left, 0);
            mTitleMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_top, 0);
            mTitleMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_right, 0);
            mTitleMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_title_margin_bottom, 0);
            mTitleTextColor = a.getColor(R.styleable.ChatView_title_color, Color.BLACK);
            mTitleTextSize = a.getDimensionPixelSize(R.styleable.ChatView_title_text_size, 30);

            mContentMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_left, 0);
            mContentMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_top, 0);
            mContentMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_right, 0);
            mContentMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_content_margin_bottom, 0);
            mContentTextColor = a.getColor(R.styleable.ChatView_content_color, Color.BLACK);
            mContentTextSize = a.getDimensionPixelSize(R.styleable.ChatView_content_text_size, 30);

            mStatusMarginLeft = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_left, 0);
            mStatusMarginTop = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_top, 0);
            mStatusMarginRight = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_right, 0);
            mStatusMarginBottom = a.getDimensionPixelSize(R.styleable.ChatView_status_margin_bottom, 0);
            mStatusTextColor = a.getColor(R.styleable.ChatView_status_color, Color.BLACK);
            mStatusTextSize = a.getDimensionPixelSize(R.styleable.ChatView_status_text_size, 30);

            mDividerColor = a.getColor(R.styleable.ChatView_divider_color, Color.GRAY);
            mDividerHeight = a.getDimensionPixelSize(R.styleable.ChatView_divider_height, 1);

            mUnreadColor = a.getColor(R.styleable.ChatView_unread_color, Color.RED);
            mPaddingUnread = a.getDimensionPixelSize(R.styleable.ChatView_unread_padding, 0);
            mUnreadTextColor = a.getColor(R.styleable.ChatView_unread_text_color, Color.WHITE);
            mUnreadTextSize = a.getDimensionPixelSize(R.styleable.ChatView_unread_text_size, 10);
            mUnreadMinWidth = a.getDimensionPixelSize(R.styleable.ChatView_unread_min_width, 0);
            mUnreadMinHeight = a.getDimensionPixelSize(R.styleable.ChatView_unread_min_height, 0);


        } finally {
            a.recycle();
        }


        mPaintMark = new Paint();
        mPaintMark.setColor(Color.RED);
        mPaintMark.setStrokeWidth(1);

      /*  mTitleTextSize = (int) mResource.getDimension(R.dimen.title_font_size);
        mContentSize = mResource.getDimension(R.dimen.content_font_size);
        mStatusSize = mResource.getDimension(R.dimen.status_font_size);*/

        mPaintDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDefault.setColor(Color.parseColor("#95a5a6"));

        mUnReadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnReadPaint.setColor(Color.RED);

        mUnReadPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnReadPaintText.setTextSize(mUnreadTextSize);
        mUnReadPaintText.setColor(Color.WHITE);
        mUnReadPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setTextSize(mTitleTextSize);
        mTitlePaint.setColor(mTitleTextColor);

        mContentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mContentPaint.setTextSize(mContentTextSize);
        mContentPaint.setColor(mContentTextColor);

        mStatusPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mStatusPaint.setTextSize(mStatusTextSize);
        mStatusPaint.setColor(mStatusTextColor);

        mPaintDivider = new Paint();
        mPaintDivider.setStrokeWidth(mDividerHeight);
        mPaintDivider.setColor(mDividerColor);
    }

    public void drawUnRead(String text) {
        mUnReadText = text;
        mIsDrawUnRead = true;
        invalidate();
    }

    public void reset() {
        currentAlpha0 = currentAlpha1 = currentAlpha2 = currentAlpha3 = 0;
        mIsDrawBitmap0 = mIsDrawBitmap1 = mIsDrawBitmap2 = mIsDrawBitmap3 = false;
        mIsAnimation0 = mIsAnimation1 = mIsAnimation2 = mIsAnimation3 = false;
        currentDefaultAlphal0 = currentDefaultAlphal1 = currentDefaultAlphal2 = currentDefaultAlphal3 = ALPHA_DEFAULT;
        mIsDrawUnRead = mIsDrawDivider = false;
        mUnReadText = null;
        mTitleText = mContentText = mStatusText = null;
        mText = null;
    }

    public void setTitle(String text) {
        mTitleText = text;
        invalidate();
    }

    public void setContent(String text) {
        mContentText = text;
    }

    public void setStatus(String text) {
        mStatusText = text;
    }

    public void setTitleStyle(Typeface tf) {
        if (mTitlePaint != null) {
            mTitlePaint.setTypeface(tf);
            invalidate();
        }
    }

    public void setContentStyle(Typeface tf) {
        if (mContentPaint != null) {
            mContentPaint.setTypeface(tf);
            invalidate();
        }
    }

    public void setStatusStyle(Typeface tf) {
        if (mStatusPaint != null) {
            mStatusPaint.setTypeface(tf);
            invalidate();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mWidth == 0.0f || mHeight == 0.0f)
            mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        setMeasuredDimension(Math.max((int) mWidth, MeasureSpec.getSize(widthMeasureSpec)), (int) (mHeight + getPaddingTop() + getPaddingBottom()));
    }

    private Bitmap centerCropImage(Bitmap src, float widthView, float heightView) {
        Bitmap des = null;
        if (src != null) {
            Matrix matrix = new Matrix();
            int bitmapWidth = src.getWidth();
            int bitmapHeight = src.getHeight();
            float scaleX = widthView / (bitmapWidth * 1.0f);
            float scaleY = heightView / (bitmapHeight * 1.0f);
            float scale = Math.max(scaleX, scaleY);
            float width = bitmapWidth * scale;
            float height = bitmapHeight * scale;
            matrix.postScale(scale, scale);
            float startX = Math.abs(widthView - width) * 0.5f;
            float startY = Math.abs(heightView - height) * 0.5f;
            des = Bitmap.createBitmap(src, (int) startX, (int) startY, (int) (bitmapWidth - startX), (int) (bitmapHeight - startY), matrix, true);
        }
        return des;
    }

    public void setBitmapUrl(List<String> urls) {
        mSize = urls.size();
        configWidthAndHeightCircleImage();
    }

    public void setBitmapUrls(String... urls) {
        mSize = urls.length;
        configWidthAndHeightCircleImage();
    }

    private void configWidthAndHeightCircleImage() {
        mPaints = new Paint[mSize];
        for (int i = 0; i < mSize; i++) {
            mPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaints[i].setAlpha(0);
        }
        mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        if (mSize == 1) {
            mWidthImage = mHeightImage = mWidth;
            mImageType = ImageType.TYPE_1;
        } else if (mSize == 2) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_2);
            mImageType = ImageType.TYPE_2;
        } else if (mSize == 3) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
            mImageType = ImageType.TYPE_3;
            mHeightDraw = mResource.getDimension(R.dimen.height_case_3);
        } else if (mSize == 4) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
            mImageType = ImageType.TYPE_4;
        } else {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
            mImageType = ImageType.TYPE_5;
        }

        mDrawableBound.set(0, 0, (int) mWidthImage, (int) mHeightImage);

        if (mSize > 4) {
            mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintText.setTextSize(50);
            mPaintText.setColor(Color.parseColor("#ecf0f1"));
            mText = String.valueOf(mSize);
        }
        invalidate();
    }

    //Method only using for draw one bitmap and text
    public void setBitmapUrl(String url, String text) {
        mSize = 1;
        mPaints = new Paint[1];
        mPaints[0] = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaints[0].setAlpha(0);

        mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_2);

        mDrawableBound.set(0, 0, (int) mWidthImage, (int) mHeightImage);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextSize(50);
        mPaintText.setColor(Color.parseColor("#ecf0f1"));
        mText = text;
    }

    public int getImageType() {
        return mImageType.ordinal();
    }

    public void drawBitmapAt(Bitmap bitmap, int index, boolean animation) {
        if (bitmap == null)
            throw new IllegalArgumentException("Bitmap not null");
        if (index > mSize - 1)
            return;

        mPaints[index].setShader(new BitmapShader(centerCropImage(bitmap, mWidthImage, mHeightImage), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        switch (index) {
            case 0:
                mIsDrawBitmap0 = true;
                mIsAnimation0 = animation;
                break;
            case 1:
                mIsDrawBitmap1 = true;
                mIsAnimation1 = animation;
                break;
            case 2:
                mIsDrawBitmap2 = true;
                mIsAnimation2 = animation;
                break;
            case 3:
                mIsDrawBitmap3 = true;
                mIsAnimation3 = animation;
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG)
            Log.d(TAG, "onDraw");
        long startTime = System.nanoTime();
        if (mRectBoundText == null)
            mRectBoundText = new Rect();

        // TODO: 04/07/2016 Draw CircleImageBox
        drawCircleImageBox(canvas);

        float tranX = mWidth + getPaddingLeft();
        canvas.translate(tranX, 0);
        float availableWidth = getWidth() - tranX - getPaddingRight();


//        canvas.drawLine(0, getPaddingTop(), getWidth(), getPaddingTop(), mPaintMark);


        // TODO: 29/06/2016 Draw Status
        if (mStatusText != null) {

            float measureStatusWidth = mStatusPaint.measureText(mStatusText);
            mStatusPaint.getTextBounds(mStatusText, 0, mStatusText.length(), mRectBoundText);
            canvas.drawText(mStatusText, availableWidth - measureStatusWidth, getPaddingTop() + mRectBoundText.height() + mStatusMarginTop, mStatusPaint);
            availableWidth -= measureStatusWidth;
        }

        CharSequence str;
        // TODO: 29/06/2016 Draw Title
        if (mTitleText != null) {
            mIsDrawDivider = true;
            str = TextUtils.ellipsize(mTitleText, mTitlePaint, availableWidth - mTitleMarginLeft, TextUtils.TruncateAt.END);
            mTitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRectBoundText);
            canvas.drawText(str, 0, str.length(), mTitleMarginLeft, getPaddingTop() + mRectBoundText.height() + mTitleMarginTop, mTitlePaint);
        }

        // TODO: 29/06/2016 Draw Content
        if (mContentText != null) {
            str = TextUtils.ellipsize(mContentText, mContentPaint, availableWidth - mContentMarginLeft, TextUtils.TruncateAt.END);
            canvas.drawText(str, 0, str.length(), mContentMarginLeft, getHeight() - mContentPaint.descent() - getPaddingBottom() - mContentMarginBottom, mContentPaint);
        }


        // TODO: 04/07/2016  Draw Divider ChatView
        if (mIsDrawDivider)
            canvas.drawLine(0, getHeight(), getWidth() - tranX, getHeight(), mPaintDivider);
        Log.d(TAG, "onDraw: Total Time: " + (System.nanoTime() - startTime));
    }

    private void drawCircleImageBox(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float radius = 0.0f;
        float tranX = 0.0f;
        float tranY = 0.0f;

        if (mSize == 1) {
            if (mText == null) {
                radius = mWidthImage / 2.0f;
                if (!mIsDrawBitmap0) {
                    Log.i(TAG, "drawCircleImageBox: ");
                    if (mDrawableDefault0 != null) {
                        mDrawableDefault0.setBounds(mDrawableBound);
                        mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                        mDrawableDefault0.draw(canvas);
                    }
                } else {
                    if (mIsAnimation0) {
                        processAnimationBitmap0(canvas, radius, 0, 0);
                    } else {
                        mPaints[0].setAlpha(ALPHA_DEFAULT);
                        canvas.drawCircle(radius, radius, radius, mPaints[0]);
                    }
                }
            } else {
                radius = mWidthImage / 2.0f;
                tranX = mWidth - mWidthImage;
                tranY = mHeight - mHeightImage;
                if (!mIsDrawBitmap0) {
                    canvas.translate(tranX, 0);
                    if (mDrawableDefault0 != null) {
                        mDrawableDefault0.setBounds(mDrawableBound);
                        mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                        mDrawableDefault0.draw(canvas);
                    }
                } else {
                    if (mIsAnimation0) {
                        Log.i(TAG, "onDraw: " + "Animation");
                        processAnimationBitmap0(canvas, radius, tranX, tranY);
                    } else {
                        mPaints[0].setAlpha(ALPHA_DEFAULT);
                        canvas.translate(tranX, 0);
                        canvas.drawCircle(radius, radius, radius, mPaints[0]); //top right
                    }
                }

                float widthTextMeasure = mPaintText.measureText(mText, 0, mText.length());
                if (mRectBoundText == null)
                    mRectBoundText = new Rect();
                mPaintText.getTextBounds(mText, 0, mText.length(), mRectBoundText);
                canvas.translate(-tranX, tranY);
                canvas.drawCircle(radius, radius, radius, mPaintDefault); //right bottom
                canvas.drawText(mText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mPaintText);
            }
        } else if (mSize == 2) {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;
            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {

                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawCircle(radius, radius, radius, mPaints[0]); //top right
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawCircle(radius, radius, radius, mPaints[1]); //bottom left
                }
            }
            canvas.translate(0, -tranY);
        } else if (mSize == 3) {
            radius = mWidthImage / 2.0f;
            tranX = (mWidth / 2) - radius;
            tranY = (mHeight - mHeightDraw) / 2.0f;

            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, tranY);
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, tranY);
                    canvas.drawCircle(radius, radius, radius, mPaints[0]); //top
                }
            }

            if (!mIsDrawBitmap1) {
                canvas.translate(-tranX, mHeightImage - tranY);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, mHeightImage - tranY);
                    canvas.drawCircle(radius, radius, radius, mPaints[1]); //left
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(mWidth - mWidthImage, 0);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(mWidth - mWidthImage, 0);
                    canvas.drawCircle(radius, radius, radius, mPaints[2]); //right
                }
            }
            canvas.translate(-(mWidth - mWidthImage), -(mHeightImage - tranY) - ((mHeight - mHeightDraw) / 2.0f));

        } else if (mSize == 4) {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawCircle(radius, radius, radius, mPaints[0]); //top left
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawCircle(radius, radius, radius, mPaints[1]); //top right
                }
            }

            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawCircle(radius, radius, radius, mPaints[2]); //left bottom
                }
            }

            if (!mIsDrawBitmap3) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault3 != null) {
                    mDrawableDefault3.setBounds(mDrawableBound);
                    mDrawableDefault3.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault3.draw(canvas);
                }
            } else {
                if (mIsAnimation3) {
                    processAnimationBitmap3(canvas, radius, tranX, tranY);
                } else {
                    mPaints[3].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawCircle(radius, radius, radius, mPaints[3]); //right bottom
                }
            }
            canvas.translate(-tranX, -tranY);
        } else {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;

            if (!mIsDrawBitmap0) {
                if (mDrawableDefault0 != null) {
                    mDrawableDefault0.setBounds(mDrawableBound);
                    mDrawableDefault0.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault0.draw(canvas);
                }
            } else {
                if (mIsAnimation0) {
                    processAnimationBitmap0(canvas, radius, tranX, tranY);
                } else {
                    mPaints[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawCircle(radius, radius, radius, mPaints[0]); //top left
                }
            }
            if (!mIsDrawBitmap1) {
                canvas.translate(tranX, 0);
                if (mDrawableDefault1 != null) {
                    mDrawableDefault1.setBounds(mDrawableBound);
                    mDrawableDefault1.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault1.draw(canvas);
                }
            } else {
                if (mIsAnimation1) {
                    processAnimationBitmap1(canvas, radius, tranX, tranY);
                } else {
                    mPaints[1].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(tranX, 0);
                    canvas.drawCircle(radius, radius, radius, mPaints[1]); //top right
                }
            }


            if (!mIsDrawBitmap2) {
                canvas.translate(-tranX, tranY);
                if (mDrawableDefault2 != null) {
                    mDrawableDefault2.setBounds(mDrawableBound);
                    mDrawableDefault2.setAlpha(ALPHA_DEFAULT);
                    mDrawableDefault2.draw(canvas);
                }
            } else {
                if (mIsAnimation2) {
                    processAnimationBitmap2(canvas, radius, tranX, tranY);
                } else {
                    mPaints[2].setAlpha(ALPHA_DEFAULT);
                    canvas.translate(-tranX, tranY);
                    canvas.drawCircle(radius, radius, radius, mPaints[2]); //left bottom
                }
            }

            if (mText != null) {
                float widthTextMeasure = mPaintText.measureText(mText, 0, mText.length());
                if (mRectBoundText == null)
                    mRectBoundText = new Rect();
                mPaintText.getTextBounds(mText, 0, mText.length(), mRectBoundText);
                canvas.translate(tranX, 0);
                canvas.drawCircle(radius, radius, radius, mPaintDefault); //right bottom
                canvas.drawText(mText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mPaintText);
            }
            canvas.translate(-tranX, -tranY);
        }

        // TODO: 29/06/2016 Draw unread count message
        if (mIsDrawUnRead) {
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            if (mUnReadText != null) {
                float widthMeasureText = mUnReadPaintText.measureText(mUnReadText, 0, mUnReadText.length());
                float width = Math.max(widthMeasureText, mUnreadMinWidth);
                width += (mPaddingUnread * 2);

                mUnReadPaintText.getTextBounds(mUnReadText, 0, mUnReadText.length(), mRectBoundText);

                radius = width / 2.0f;
                canvas.translate(mWidth - width, 0);
                canvas.drawCircle(radius, radius, radius, mUnReadPaint);

                float x = (width - widthMeasureText) / 2.0f;
                float y =   radius + (mRectBoundText.height() /2.0f);
                canvas.drawText(mUnReadText, x, y, mUnReadPaintText);
                canvas.translate(-(mWidth - width), 0);

            }
        }
        canvas.translate(-getPaddingLeft(), -getPaddingTop());
    }

    private void processAnimationBitmap0(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 1) {
            if (mText != null) {
                canvas.translate(tranX, 0);
            }
        } else if (mSize == 2) {
            canvas.translate(tranX, 0);
        } else if (mSize == 3) {
            canvas.translate(tranX, tranY);
        }
        if (currentDefaultAlphal0 < 0)
            currentDefaultAlphal0 = 0;

        if (mDrawableDefault0 != null) {
            mDrawableDefault0.setBounds(mDrawableBound);
            mDrawableDefault0.setAlpha(currentDefaultAlphal0);
            mDrawableDefault0.draw(canvas);
        }
        if (currentAlpha0 > ALPHA_DEFAULT)
            currentAlpha0 = ALPHA_DEFAULT;
        mPaints[0].setAlpha(currentAlpha0);
        canvas.drawCircle(radius, radius, radius, mPaints[0]); //top right
        if (currentAlpha0 < 255) {
            currentAlpha0 += ALPHA_STEP;
            currentDefaultAlphal0 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }
    }

    private void processAnimationBitmap1(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 2) {
            canvas.translate(-tranX, tranY);
        } else if (mSize == 3) {
            canvas.translate(-tranX, mHeightImage - tranY);
        } else if (mSize == 4 || mSize > 4) {
            canvas.translate(tranX, 0);
        }

        if (currentDefaultAlphal1 < 0)
            currentDefaultAlphal1 = 0;

        if (mDrawableDefault1 != null) {
            mDrawableDefault1.setBounds(mDrawableBound);
            mDrawableDefault1.setAlpha(currentDefaultAlphal1);
            mDrawableDefault1.draw(canvas);
        }
        if (currentAlpha1 > ALPHA_DEFAULT)
            currentAlpha1 = ALPHA_DEFAULT;

        mPaints[1].setAlpha(currentAlpha1);
        canvas.drawCircle(radius, radius, radius, mPaints[1]); //bottom left
        if (currentAlpha1 < 255) {
            currentAlpha1 += ALPHA_STEP;
            currentDefaultAlphal1 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }

    }

    private void processAnimationBitmap2(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 3) {
            canvas.translate(mWidth - mWidthImage, 0);
        } else if (mSize == 4 || mSize > 4) {
            canvas.translate(-tranX, tranY);
        }

        if (currentDefaultAlphal2 < 0)
            currentDefaultAlphal2 = 0;

        if (mDrawableDefault2 != null) {
            mDrawableDefault2.setBounds(mDrawableBound);
            mDrawableDefault2.setAlpha(currentDefaultAlphal2);
            mDrawableDefault2.draw(canvas);
        }

        if (currentAlpha2 > ALPHA_DEFAULT)
            currentAlpha2 = ALPHA_DEFAULT;
        mPaints[2].setAlpha(currentAlpha2);
        canvas.drawCircle(radius, radius, radius, mPaints[2]); //right
        if (currentAlpha2 < 255) {
            currentAlpha2 += ALPHA_STEP;
            currentDefaultAlphal2 -= ALPHA_STEP;
            postInvalidateDelayed(TIME_REFESH);
        }
    }

    private void processAnimationBitmap3(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 4) {
            if (currentDefaultAlphal3 < 0)
                currentDefaultAlphal3 = 0;
            canvas.translate(tranX, 0);
            if (mDrawableDefault3 != null) {
                mDrawableDefault3.setBounds(mDrawableBound);
                mDrawableDefault3.setAlpha(currentDefaultAlphal3);
                mDrawableDefault3.draw(canvas);
            }

            if (currentAlpha3 > ALPHA_DEFAULT)
                currentAlpha3 = ALPHA_DEFAULT;
            mPaints[3].setAlpha(currentAlpha3);
            canvas.drawCircle(radius, radius, radius, mPaints[3]); //left bottom
            if (currentAlpha3 < 255) {
                currentAlpha3 += ALPHA_STEP;
                currentDefaultAlphal3 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        }
    }
}
