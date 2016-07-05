package com.example.nguyennghia.circleimageview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.nguyennghia.circleimageview.ChatView;
import com.example.nguyennghia.circleimageview.CircleBitmapDrawable;
import com.example.nguyennghia.circleimageview.CircleColorDrawable;
import com.example.nguyennghia.circleimageview.R;
import com.example.nguyennghia.circleimageview.model.AvatarBox;
import com.example.nguyennghia.circleimageview.model.Picture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyennghia on 27/06/2016.
 */


public class AvatarBoxAdapter extends ArrayAdapter<AvatarBox> {
    private static final String TAG = AvatarBoxAdapter.class.getSimpleName();
    private Context mContext;
    private List<AvatarBox> mAvatarBox;
    private CircleColorDrawable mCircleColorDrawable;
    private CircleBitmapDrawable mCircleBitmapDrawable;

    public AvatarBoxAdapter(Context context, List<AvatarBox> objects) {
        super(context, 0, objects);
        mContext = context;
        mAvatarBox = objects;
        mCircleColorDrawable = new CircleColorDrawable(context.getResources().getColor(R.color.colorAccent));
        mCircleBitmapDrawable = new CircleBitmapDrawable(BitmapFactory.decodeResource(context.getResources() ,R.drawable.default_ava));

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Log.i(TAG, "getView: " + position);
        final ViewHolder viewHolder;
        final AvatarBox author = mAvatarBox.get(position);
        final Picture picture = author.getPictures();

        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.author_row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ciAvaAuthor = (ChatView) convertView.findViewById(R.id.ci_ava_author);
            convertView.setTag(viewHolder);
        } else {
            Log.i(TAG, "getView: " + "Tai su dung");
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.ciAvaAuthor.reset();
        }

        viewHolder.ciAvaAuthor.setBitmapUrl(picture.getUrls());
        viewHolder.ciAvaAuthor.drawUnRead("N");
        viewHolder.ciAvaAuthor.setStatus("10 minutes");
        viewHolder.ciAvaAuthor.setDrawableDefault(mCircleColorDrawable);
//        viewHolder.ciAvaAuthor.setDrawableDefault(mCircleBitmapDrawable);
        viewHolder.ciAvaAuthor.setTitle("Nguyễn Nghĩa, Hoàng Ánh, Hoàng Xoan, Nguyễn Sơn");
        viewHolder.ciAvaAuthor.setContent("Thanks for your answer, that helps a bit, but I want to have two separate windows");

        int size = picture.getUrls().size() > 4 ? 3 : picture.getUrls().size();
        for (int i = 0; i < size; i++) {
            if (picture.getLoadeds()[i]) {
                viewHolder.ciAvaAuthor.drawBitmapAt(picture.getBitmaps()[i], i, false);
                picture.getIsAnimation()[i] = false;
            } else {
                new DownloadAvatarBoxTask().execute(picture);
            }
        }

        picture.setOnDownloadBitmapListener(new Picture.OnDownloadBitmap() {
            @Override
            public void onSuscess(Bitmap bitmap, int index, int type) {
                if (viewHolder.ciAvaAuthor.getImageType() == type && picture.getLoadeds()[index]) {
                    viewHolder.ciAvaAuthor.drawBitmapAt(bitmap, index, true);
                    picture.getIsAnimation()[index] = false;
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tvAuthor;
        private ChatView ciAvaAuthor;
    }

    class DownloadAvatarBoxTask extends AsyncTask<Picture, Void, Void> {
        private Picture picture;
        private List<Bitmap> bitmaps = new ArrayList<>();
        private int size;

        @Override
        protected Void doInBackground(Picture... params) {
            picture = params[0];
            List<String> url = picture.getUrls();
            size = url.size() > 4 ? 3 : url.size();
            for (int i = 0; i < size; i++) {
                Bitmap bm = null;
                try {
                    if (picture.getLoadeds()[i]) {
                        bm = picture.getBitmaps()[i];
                    } else {
                        URL aURL = new URL(url.get(i));
                        URLConnection conn = aURL.openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bm = BitmapFactory.decodeStream(bis);
                        bis.close();
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmaps.add(bm);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            for (int i = 0; i < size; i++) {
                Bitmap bm = bitmaps.get(i);
                if (!picture.getLoadeds()[i] && bm != null) {
                    picture.setBitmap(bm, i);
                }
            }
        }
    }

}
