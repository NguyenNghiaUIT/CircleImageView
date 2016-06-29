package com.example.nguyennghia.circleimageview.model;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyennghia on 27/06/2016.
 */
public class Picture {
    private static final String TAG = "Picture";
    private List<String> mUrls;
    private Bitmap[] mBitmaps;
    private boolean[] mLoadeds;
    private boolean[] mAnimations;
    private OnDownloadBitmap mListener;

    public interface OnDownloadBitmap {
        void onSuscess(Bitmap bitmap, int index, int size);
    }

    public Picture() {
        mUrls = new ArrayList<>();
    }


    public void setOnDownloadBitmapListener(OnDownloadBitmap listener) {
        mListener = listener;
    }

    public Bitmap[] getBitmaps() {
        return mBitmaps;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public boolean[] getLoadeds() {
        return mLoadeds;
    }

    public void setBitmap(Bitmap bimap, int index) {
        mLoadeds[index] = true;
        if (mBitmaps[index] != bimap)
            mBitmaps[index] = bimap;
        if (mListener != null)
            mListener.onSuscess(bimap, index, mUrls.size() > 4 ? 4 : mUrls.size() - 1);
    }

    public void setUrl(String... urls) {
        int lenght = urls.length;
        for (String s : urls) {
            mUrls.add(s);
        }

        if (lenght > 4) {
            mBitmaps = new Bitmap[3];
            mLoadeds = new boolean[3];
            mAnimations = new boolean[3];
        } else {
            mBitmaps = new Bitmap[lenght];
            mLoadeds = new boolean[lenght];
            mAnimations = new boolean[lenght];
        }

        for (int i = 0; i < mAnimations.length; i++) {
            mAnimations[i] = true;
        }
    }

    public void setUrl(List<String> urls) {
        int size = urls.size();
        this.mUrls = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mUrls.add(urls.get(i));
        }
        if (size > 4) {
            mBitmaps = new Bitmap[3];
            mLoadeds = new boolean[3];
            mAnimations = new boolean[3];
        } else {
            mBitmaps = new Bitmap[size];
            mLoadeds = new boolean[size];
            mAnimations = new boolean[size];
        }
        for (int i = 0; i < mAnimations.length; i++) {
            mAnimations[i] = true;
        }
    }

    public boolean[] getIsAnimation() {
        return mAnimations;
    }

    @Override
    public String toString() {
        for (Bitmap b : mBitmaps)
            Log.i(TAG, "Bitmap: " + b);

        for (boolean b : mLoadeds)
            Log.i(TAG, "Load :" + b);

        for (boolean a : mAnimations) {
            Log.i(TAG, "Animation: " + a);
        }
        return super.toString();
    }
}
