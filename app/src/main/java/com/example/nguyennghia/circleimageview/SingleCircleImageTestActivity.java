package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SingleCircleImageTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SingleCircle";
    private Bitmap bm1;
    private Bitmap bm2;
    private Bitmap bm3;
    private Bitmap bm4;

    private ChatView ci1;
    private ChatView ci2;
    private ChatView ci3;
    private ChatView ci4;
    private ChatView ci5;

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_circle_image_test);


        ci1 = (ChatView) findViewById(R.id.ci_1);
        ci2 = (ChatView) findViewById(R.id.ci_2);
        ci3 = (ChatView) findViewById(R.id.ci_3);
        ci4 = (ChatView) findViewById(R.id.ci_4);
        ci5 = (ChatView) findViewById(R.id.ci_5);


        cb1 = (CheckBox) findViewById(R.id.cb_1);
        cb2 = (CheckBox) findViewById(R.id.cb_2);
        cb3 = (CheckBox) findViewById(R.id.cb_3);
        cb4 = (CheckBox) findViewById(R.id.cb_4);
        cb5 = (CheckBox) findViewById(R.id.cb_5);

        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.ava);
        bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ava1);
        bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.ava2);
        bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.ava1);


        ci1.setBitmapUrls("url0");
        ci2.setBitmapUrls("url0", "url1");
        ci3.setBitmapUrls("url0", "url1", "url2");
        ci4.setBitmapUrls("url0", "url1", "url2", "url3");
        ci5.setBitmapUrls("url0", "url1", "url2", "url3", "url4", "url5", "url6");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_1:
                ci1.reset();
                if (cb1.isChecked())
                    ci1.drawBitmapAt(bm1, 0, true);
                else
                   // ci1.drawBitmapAt(bm1, 0, false);
                 ci1.setDrawableDefault(new CircleColorDrawable(getResources().getColor(R.color.default_color)));

//                ci1.drawUnRead("N");
                break;
            case R.id.btn_2:
                if (cb2.isChecked()) {
                    ci2.drawBitmapAt(bm1, 0, true);
                    ci2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci2.drawBitmapAt(bm2, 1, true);
                        }
                    }, 1000);
                } else {
                    ci2.drawBitmapAt(bm1, 0, false);
                    ci2.drawBitmapAt(bm2, 1, false);
                }
                ci2.drawUnRead("1");
                break;
            case R.id.btn_3:
                if (cb3.isChecked()) {
                    ci3.drawBitmapAt(bm1, 0, true);
                    ci3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci3.drawBitmapAt(bm2, 1, true);
                        }
                    }, 1000);

                    ci3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci3.drawBitmapAt(bm3, 2, true);
                        }
                    }, 2000);
                } else {
                    ci3.drawBitmapAt(bm1, 0, false);
                    ci3.drawBitmapAt(bm2, 1, false);
                    ci3.drawBitmapAt(bm3, 2, false);

                }
                ci3.drawUnRead("2");
                break;
            case R.id.btn_4:
                if (cb4.isChecked()) {
                    ci4.drawBitmapAt(bm1, 0, true);
                    ci4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci4.drawBitmapAt(bm2, 1, true);
                        }
                    }, 1000);

                    ci4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci4.drawBitmapAt(bm3, 2, true);
                        }
                    }, 2000);

                    ci4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci4.drawBitmapAt(bm4, 3, true);
                        }
                    }, 3000);


                } else {
                    ci4.drawBitmapAt(bm1, 0, false);
                    ci4.drawBitmapAt(bm2, 1, false);
                    ci4.drawBitmapAt(bm3, 2, false);
                    ci4.drawBitmapAt(bm4, 3, false);
                }
                ci4.drawUnRead("1");
                break;
            case R.id.btn_5:
                if (cb5.isChecked()) {
                    ci5.drawBitmapAt(bm1, 0, true);
                    ci5.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci5.drawBitmapAt(bm2, 1, true);
                        }
                    }, 1000);

                    ci5.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ci5.drawBitmapAt(bm3, 2, true);
                        }
                    }, 2000);
                } else {
                    ci5.drawBitmapAt(bm1, 0, false);
                    ci5.drawBitmapAt(bm2, 1, false);
                    ci5.drawBitmapAt(bm3, 2, false);
                }
                ci5.drawUnRead("5+");
                break;
            default:
                break;
        }
    }
}
