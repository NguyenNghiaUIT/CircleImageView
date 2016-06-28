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
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by NguyenNghia on 5/27/2016.
 */
public class CircleImage extends View {
    private static final String TAG = CircleImage.class.getSimpleName();
    private static final int ALPHA_DEFAULT = 255;
    private float mHeight;
    private float mWidth;

    private int mSize;
    private Paint[] mPaints;
    private Paint[] mPaintDefaults;

    private float mWidthImage;
    private float mHeightImage;
    private Paint mPaintText;

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
        mPaintDefaults = new Paint[4];
        for (int i = 0; i < 4; i++) {
            mPaintDefaults[i] = new Paint();
            mPaintDefaults[i].setColor(Color.parseColor("#95a5a6"));
            mPaintDefaults[i].setAntiAlias(true);
        }
    }

    public void reset() {
        currentAlpha0 = currentAlpha1 = currentAlpha2 = currentAlpha3 = 0;
        mIsDrawBitmap0 = mIsDrawBitmap1 = mIsDrawBitmap2 = mIsDrawBitmap3 = false;
        mIsAnimation0 = mIsAnimation1 = mIsAnimation2 = mIsAnimation3 = false;
        currentDefaultAlphal0 = currentDefaultAlphal1 = currentDefaultAlphal2 = currentDefaultAlphal3 = ALPHA_DEFAULT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) mWidth, (int) mHeight);
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

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(50);
        mPaintText.setColor(Color.BLACK);
        mText = String.valueOf(mSize);
    }

    public int getImageType() {
        return mImageType.ordinal();
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

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(50);
        mPaintText.setColor(Color.BLACK);
        mText = String.valueOf(mSize - 3);
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
//        Log.i(TAG, "onDraw");
        if (mSize == 1) {
            float radius = mWidthImage / 2.0f;
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
        } else if (mSize == 2) {
            float radius = mWidthImage / 2.0f;
            float tranX = mWidth - mWidthImage;
            float tranY = mHeight - mHeightImage;
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

        } else if (mSize == 3) {
            float radius = mWidthImage / 2.0f;
            float tranX = (mWidth / 2) - radius;
            float tranY = (mHeight - mHeightDraw) / 2.0f;

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

        } else if (mSize == 4) {
            float radius = mWidthImage / 2.0f;
            float tranX;
            float tranY;
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
        } else {
            float radius = mWidthImage / 2.0f;
            float tranX;
            float tranY;
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
            float widthTextMeasure = mPaintText.measureText(mText, 0, mText.length());
            if (mRectBoundText == null)
                mRectBoundText = new Rect();
            mPaintText.getTextBounds(mText, 0, mText.length(), mRectBoundText);
            canvas.translate(tranX, 0);
            canvas.drawCircle(radius, radius, radius, mPaintDefaults[3]); //right bottom
            canvas.drawText(mText, radius - (widthTextMeasure / 2), radius + mRectBoundText.height() / 2, mPaintText);
        }
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


