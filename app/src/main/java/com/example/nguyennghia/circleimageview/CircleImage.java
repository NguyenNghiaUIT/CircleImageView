package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by NguyenNghia on 5/27/2016.
 */
public class CircleImage extends View {
    private static final String TAG = CircleImage.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final int ALPHA_DEFAULT = 255;
    private float mHeight;
    private float mWidth;

    private Paint mPaintTest;

    private int mSize;
    private Paint[] mPaints; // using for draw bitmap
    private Paint[] mPaintDefaults; //using for defaul color when bitmap not fill

    private TextPaint mTitlePaint;
    private TextPaint mContentPaint;
    private TextPaint mStatusPaint;
    private String mStatusText; /*"4 minutes";*/
    private String mTitleText; /*= "Help people interested in this repository understand your project by adding a README.";*/
    private String mContentText; /* = "Pixels - corresponds to actual pixels on the screen.";*/

    private float mTittleSize;
    private float mContentSize;
    private float mStatusSize;

    private Paint mUnReadPaint; //using for draw circle Unread Count Notificition
    private Paint mUnReadPaintText; //using for draw text in circle Unread Count Notification
    private String mUnReadText;
    private float mPaddingUnread;
    private float mTextSizeUnread;
    private Rect mUnReadTextBound;

    private float mWidthImage;
    private float mHeightImage;
    private Paint mPaintText;

    private float mPaddingLeftRight;
    private float mPaddingTopBottom;
    private float mTitleMarginTop;

    private Paint mPaintDivider;


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
    private boolean mIsDrawUnRead;

    private ImageType mImageType;


    public enum ImageType {
        TYPE_1, //represent for 1 bitmap in view
        TYPE_2,  //represent for 2 bitmap in view
        TYPE_3, //represent for 3 bitmap in view
        TYPE_4,  //represent for 4 bitmap in view
        TYPE_5   //represent for 3 bitmap and text in view
    }

    public CircleImage(Context context) {
        this(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResource = getResources();
        mTextSizeUnread = mResource.getDimension(R.dimen.text_unread_font_size);
        mPaddingLeftRight = mResource.getDimension(R.dimen.padding_left_right_chat_view);
        mPaddingTopBottom = mResource.getDimension(R.dimen.padding_top_bottom_chat_view);
        mPaddingUnread = mResource.getDimension(R.dimen.padding_unread_size);
        mTitleMarginTop = mResource.getDimension(R.dimen.title_margin_top);

        //get font size from dimen
        mTittleSize = mResource.getDimension(R.dimen.title_font_size);
        mContentSize = mResource.getDimension(R.dimen.content_font_size);
        mStatusSize = mResource.getDimension(R.dimen.status_font_size);

        mPaintDefaults = new Paint[4];
        for (int i = 0; i < 4; i++) {
            mPaintDefaults[i] = new Paint();
            mPaintDefaults[i].setColor(Color.parseColor("#95a5a6"));
            mPaintDefaults[i].setAntiAlias(true);
        }

        mUnReadPaint = new Paint();
        mUnReadPaint.setColor(Color.RED);
        mUnReadPaint.setAntiAlias(true);

        mUnReadPaintText = new Paint();
        mUnReadPaintText.setTextSize(mTextSizeUnread);
        mUnReadPaintText.setColor(Color.WHITE);
        mUnReadPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mUnReadPaintText.setAntiAlias(true);

        mTitlePaint = new TextPaint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTextSize(mTittleSize);
        mTitlePaint.setColor(Color.parseColor("#34495e"));

        mContentPaint = new TextPaint();
        mContentPaint.setAntiAlias(true);
        mContentPaint.setTextSize(mContentSize);
        mContentPaint.setColor(Color.parseColor("#95a5a6"));

        mStatusPaint = new TextPaint();
        mStatusPaint.setAntiAlias(true);
        mStatusPaint.setTextSize(mStatusSize);
        mStatusPaint.setColor(Color.parseColor("#34495e"));

        mPaintDivider = new Paint();
        mPaintDivider.setStrokeWidth(1);
        mPaintDivider.setColor(Color.parseColor("#7f8c8d"));
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
        mIsDrawUnRead = false;
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
        mTitlePaint.setTypeface(tf);
        invalidate();
    }

    public void setContentStyle(Typeface tf) {
        mContentPaint.setTypeface(tf);
        invalidate();
    }

    public void setStatusStyle(Typeface tf) {
        mStatusPaint.setTypeface(tf);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mWidth == 0.0f || mHeight == 0.0f)
            mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (mHeight + mPaddingTopBottom * 2));
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
        mPaints = new Paint[mSize];
        for (int i = 0; i < mSize; i++) {
            mPaints[i] = new Paint();
            mPaints[i].setAntiAlias(true);
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

        if (mSize > 4) {
            mPaintText = new Paint();
            mPaintText.setAntiAlias(true);
            mPaintText.setTextSize(50);
            mPaintText.setColor(Color.parseColor("#ecf0f1"));
            mText = String.valueOf(mSize);
        }
        invalidate();
    }

    public int getImageType() {
        return mImageType.ordinal();
    }

    //Method only using for draw one bitmap and text
    public void setBitmapUrl(String url, String text) {
        mSize = 1;
        mPaints = new Paint[1];
        mPaints[0] = new Paint();
        mPaints[0].setAntiAlias(true);
        mPaints[0].setAlpha(0);

        mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_2);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(50);
        mPaintText.setColor(Color.parseColor("#ecf0f1"));
        mText = text;
    }

    public void setBitmapUrls(String... urls) {
        mSize = urls.length;
        mPaints = new Paint[mSize];
        for (int i = 0; i < mSize; i++) {
            mPaints[i] = new Paint();
            mPaints[i].setAntiAlias(true);
            mPaints[i].setAlpha(0);
        }

        mHeight = mWidth = mResource.getDimension(R.dimen.height_width_circle_image_view);
        if (mSize == 1) {
            mWidthImage = mHeightImage = mWidth;
        } else if (mSize == 2) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_2);
        } else if (mSize == 3) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
            mHeightDraw = mResource.getDimension(R.dimen.height_case_3);
        } else if (mSize == 4) {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
        } else {
            mWidthImage = mHeightImage = mResource.getDimension(R.dimen.height_width_circle_image_view_case_3);
        }

        if (mSize > 4) {
            mPaintText = new Paint();
            mPaintText.setAntiAlias(true);
            mPaintText.setTextSize(50);
            mPaintText.setColor(Color.parseColor("#ecf0f1"));
            mText = String.valueOf(mSize);
        }
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

        // TODO: 04/07/2016 Draw CircleImageBox
        drawCircleImageBox(canvas);

        float tranX = mWidth + mPaddingLeftRight * 2;
        canvas.translate(tranX, 0);
        float availableWidth = getWidth() - tranX - mPaddingLeftRight;

        
        // TODO: 04/07/2016  Draw Divider ChatView
        canvas.drawLine(0, getHeight(), getWidth() - tranX, getHeight(), mPaintDivider);

        // TODO: 29/06/2016 Draw Status
        if (mStatusText != null) {
            float measureStatusWidth = mStatusPaint.measureText(mStatusText);
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            mStatusPaint.getTextBounds(mStatusText, 0, mStatusText.length(), mRectBoundText);
            canvas.drawText(mStatusText, availableWidth - measureStatusWidth, mTitleMarginTop + mPaddingTopBottom + mRectBoundText.height(), mStatusPaint);
            availableWidth -= measureStatusWidth;
        }

        CharSequence str;
        // TODO: 29/06/2016 Draw Title
        if (mTitleText != null) {
            str = TextUtils.ellipsize(mTitleText, mTitlePaint, availableWidth, TextUtils.TruncateAt.END);
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            mTitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mRectBoundText);
            canvas.drawText(str, 0, str.length(), 0, mTitleMarginTop + mPaddingTopBottom + mRectBoundText.height(), mTitlePaint);

        }

        // TODO: 29/06/2016 Draw Content
        if (mContentText != null) {
            str = TextUtils.ellipsize(mContentText, mContentPaint, availableWidth, TextUtils.TruncateAt.END);
            canvas.drawText(str, 0, str.length(), 0, getHeight() - (mPaddingTopBottom + mTitleMarginTop), mContentPaint);
        }


        Log.d(TAG, "onDraw: Total Time: " + (System.nanoTime() - startTime));
    }


    private void drawCircleImageBox(Canvas canvas) {
        canvas.translate(mPaddingLeftRight, mPaddingTopBottom);
        float radius = 0.0f;
        float tranX = 0.0f;
        float tranY = 0.0f;

        if (mSize == 1) {
            if (mText == null) {
                radius = mWidthImage / 2.0f;
                if (!mIsDrawBitmap0) {
                    mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]);
                } else {
                    if (mIsAnimation0) {
                        processAnimationBitmap0(canvas, radius, 0, 0);
                    } else {
                        mPaints[0].setAlpha(ALPHA_DEFAULT);
                        canvas.drawCircle(radius, radius, radius, mPaints[0]);
                    }
                }
            } else {
                Log.i(TAG, "onDraw: " + "Case 2");
                radius = mWidthImage / 2.0f;
                tranX = mWidth - mWidthImage;
                tranY = mHeight - mHeightImage;
                if (!mIsDrawBitmap0) {
                    canvas.translate(tranX, 0);
                    mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                    canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top right
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
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[3]); //right bottom
                canvas.drawText(mText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mPaintText);
            }
        } else if (mSize == 2) {
            radius = mWidthImage / 2.0f;
            tranX = mWidth - mWidthImage;
            tranY = mHeight - mHeightImage;
            if (!mIsDrawBitmap0) {
                canvas.translate(tranX, 0);
                mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top right
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
                mPaintDefaults[1].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //bottom left
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
                mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top
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
                mPaintDefaults[1].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //left
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
                mPaintDefaults[2].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[2]); //right
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
                mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top left
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
                mPaintDefaults[1].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //top right
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
                mPaintDefaults[2].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[2]); //left bottom
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
                mPaintDefaults[3].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[3]); //right bottom
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
                mPaintDefaults[0].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top left
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
                mPaintDefaults[1].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //top right
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
                mPaintDefaults[2].setAlpha(ALPHA_DEFAULT);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[2]); //left bottom
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
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[3]); //right bottom
                canvas.drawText(mText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mPaintText);
            }
            canvas.translate(-tranX, -tranY);
        }

        // TODO: 29/06/2016 Draw unread count message
        if (mIsDrawUnRead) {
            if (mUnReadTextBound == null)
                mUnReadTextBound = new Rect();
            float widthMeasureText = mUnReadPaintText.measureText(mUnReadText, 0, mUnReadText.length());
            mUnReadPaintText.getTextBounds(mUnReadText, 0, mUnReadText.length(), mUnReadTextBound);
            Log.e(TAG, "onDraw: " + "widthMeasureText: " + widthMeasureText);
            Log.e(TAG, "onDraw: " + "WidthBound" + mUnReadTextBound.width());
            Log.e(TAG, "onDraw: " + "HeightBound" + mUnReadTextBound.height());
            float height = mTextSizeUnread + (mPaddingUnread * 2.0f);
            Log.i(TAG, "onDraw: " + height);
            radius = height / 2.0f;
            canvas.translate(mWidth - height, 0);
            canvas.drawCircle(radius, radius, radius, mUnReadPaint);

            float x = (height - widthMeasureText) / 2.0f;
            float y = radius + mTextSizeUnread / 2.0f;
            canvas.drawText(mUnReadText, x, y, mUnReadPaintText);
            canvas.translate(-(mWidth - height), 0);
        }


        canvas.translate(-mPaddingLeftRight, -mPaddingTopBottom);
    }

    private void processAnimationBitmap3(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 4) {
            if (currentDefaultAlphal3 < 0)
                currentDefaultAlphal3 = 0;
            mPaintDefaults[3].setAlpha(currentDefaultAlphal3);
            canvas.translate(tranX, 0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[3]); //right bottom

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

    private void processAnimationBitmap2(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 3) {
            if (currentDefaultAlphal2 < 0)
                currentDefaultAlphal2 = 0;
            mPaintDefaults[2].setAlpha(currentDefaultAlphal2);
            canvas.translate(mWidth - mWidthImage, 0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[2]); //right

            if (currentAlpha2 > ALPHA_DEFAULT)
                currentAlpha2 = ALPHA_DEFAULT;
            mPaints[2].setAlpha(currentAlpha2);
            canvas.drawCircle(radius, radius, radius, mPaints[2]); //right
            if (currentAlpha2 < 255) {
                currentAlpha2 += ALPHA_STEP;
                currentDefaultAlphal2 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        } else if (mSize == 4 || mSize > 4) {
            if (currentDefaultAlphal2 < 0)
                currentDefaultAlphal2 = 0;
            mPaintDefaults[2].setAlpha(currentDefaultAlphal2);
            canvas.translate(-tranX, tranY);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[2]); //left bottom

            if (currentAlpha2 > ALPHA_DEFAULT)
                currentAlpha2 = ALPHA_DEFAULT;
            mPaints[2].setAlpha(currentAlpha2);
            canvas.drawCircle(radius, radius, radius, mPaints[2]); //left bottom
            if (currentAlpha2 < 255) {
                currentAlpha2 += ALPHA_STEP;
                currentDefaultAlphal2 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        }
    }

    private void processAnimationBitmap1(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 2) {
            if (currentDefaultAlphal1 < 0)
                currentDefaultAlphal1 = 0;

            mPaintDefaults[1].setAlpha(currentDefaultAlphal1);
            canvas.translate(-tranX, tranY);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]);

            if (currentAlpha1 > ALPHA_DEFAULT)
                currentAlpha1 = ALPHA_DEFAULT;

            mPaints[1].setAlpha(currentAlpha1);
            canvas.drawCircle(radius, radius, radius, mPaints[1]); //bottom left
            if (currentAlpha1 < 255) {
                currentAlpha1 += ALPHA_STEP;
                currentDefaultAlphal1 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        } else if (mSize == 3) {
            if (currentDefaultAlphal1 < 0)
                currentDefaultAlphal1 = 0;
            mPaintDefaults[1].setAlpha(currentDefaultAlphal1);
            canvas.translate(-tranX, mHeightImage - tranY);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //left
            if (currentAlpha1 > ALPHA_DEFAULT)
                currentAlpha1 = ALPHA_DEFAULT;
            mPaints[1].setAlpha(currentAlpha1);
            canvas.drawCircle(radius, radius, radius, mPaints[1]); //left
            if (currentAlpha1 < 255) {
                currentAlpha1 += ALPHA_STEP;
                currentDefaultAlphal1 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        } else if (mSize == 4 || mSize > 4) {
            if (currentDefaultAlphal1 < 0)
                currentDefaultAlphal1 = 0;
            mPaintDefaults[1].setAlpha(currentDefaultAlphal1);
            canvas.translate(tranX, 0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[1]); //top right
            if (currentAlpha1 > ALPHA_DEFAULT)
                currentAlpha1 = ALPHA_DEFAULT;
            mPaints[1].setAlpha(currentAlpha1);
            canvas.drawCircle(radius, radius, radius, mPaints[1]); //top right
            if (currentAlpha1 < 255) {
                currentAlpha1 += ALPHA_STEP;
                currentDefaultAlphal1 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        }

    }

    private void processAnimationBitmap0(Canvas canvas, float radius, float tranX, float tranY) {
        if (mSize == 1) {
            if (mText == null) {
                if (currentDefaultAlphal0 < 0)
                    currentDefaultAlphal0 = 0;
                mPaintDefaults[0].setAlpha(currentDefaultAlphal0);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]);
                if (currentAlpha0 >= ALPHA_DEFAULT) {
                    currentAlpha0 = ALPHA_DEFAULT;
                }
                mPaints[0].setAlpha(currentAlpha0);
                canvas.drawCircle(radius, radius, radius, mPaints[0]);
                if (currentAlpha0 < 255) {
                    currentAlpha0 += ALPHA_STEP;
                    currentDefaultAlphal0 -= ALPHA_STEP;
                    postInvalidateDelayed(TIME_REFESH);
                }
            } else {
                if (currentDefaultAlphal0 < 0)
                    currentDefaultAlphal0 = 0;
                mPaintDefaults[0].setAlpha(currentDefaultAlphal0);
                canvas.translate(tranX, 0);
                canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]);

                if (currentAlpha0 > ALPHA_DEFAULT)
                    currentAlpha0 = ALPHA_DEFAULT;
                mPaints[0].setAlpha(currentAlpha0);
                canvas.drawCircle(radius, radius, radius, mPaints[0]);

                if (currentAlpha0 < 255) {
                    currentAlpha0 += ALPHA_STEP;
                    currentDefaultAlphal0 -= ALPHA_STEP;
                    postInvalidateDelayed(TIME_REFESH);
                }
            }
        } else if (mSize == 2) {
            if (currentDefaultAlphal0 < 0)
                currentDefaultAlphal0 = 0;
            mPaintDefaults[0].setAlpha(currentDefaultAlphal0);
            canvas.translate(tranX, 0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]);

            if (currentAlpha0 > ALPHA_DEFAULT)
                currentAlpha0 = ALPHA_DEFAULT;
            mPaints[0].setAlpha(currentAlpha0);
            canvas.drawCircle(radius, radius, radius, mPaints[0]);

            if (currentAlpha0 < 255) {
                currentAlpha0 += ALPHA_STEP;
                currentDefaultAlphal0 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        } else if (mSize == 3) {
            if (currentDefaultAlphal0 < 0)
                currentDefaultAlphal0 = 0;
            mPaintDefaults[0].setAlpha(currentDefaultAlphal0);
            canvas.translate(tranX, tranY);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top
            if (currentAlpha0 > ALPHA_DEFAULT)
                currentAlpha0 = ALPHA_DEFAULT;
            mPaints[0].setAlpha(currentAlpha0);
            canvas.drawCircle(radius, radius, radius, mPaints[0]); //top right
            if (currentAlpha0 < 255) {
                currentAlpha0 += ALPHA_STEP;
                currentDefaultAlphal0 -= ALPHA_STEP;
                postInvalidateDelayed(TIME_REFESH);
            }
        } else if (mSize == 4 || mSize > 4) {
            if (currentDefaultAlphal0 < 0)
                currentDefaultAlphal0 = 0;
            mPaintDefaults[0].setAlpha(currentDefaultAlphal0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[0]); //top
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
    }
}

