package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TwoCircleImageActivity extends AppCompatActivity {
    ChatView circleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_circle_image);

        final Bitmap bm = BitmapFactory.decodeResource(getResources(), +R.drawable.ava1);
        circleImage = (ChatView) findViewById(R.id.cv_two_image);
        circleImage.setBitmapUrl("url", "1");
        CircleBitmapDrawable circleBitmapDrawable = new CircleBitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.default_ava));
        CircleColorDrawable circleColorDrawable = new CircleColorDrawable(getResources().getColor(R.color.colorAccent));
        circleImage.setDrawableDefault(circleColorDrawable);
        circleImage.drawUnRead("N");

        circleImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleImage.drawBitmapAt(bm, 0, true);
            }
        }, 2000);

    }
}
