package com.example.nguyennghia.circleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TwoCircleImageActivity extends AppCompatActivity {
    CircleImage circleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_circle_image);

//        Bitmap bm = BitmapFactory.decodeResource(getResources(), +R.drawable.ava1);
//        circleImage = (CircleImage) findViewById(R.id.ci_two_image);
//        circleImage.setBitmapUrl("url", "1");
//        circleImage.drawBitmapAt(bm, 0, true);
    }
}
