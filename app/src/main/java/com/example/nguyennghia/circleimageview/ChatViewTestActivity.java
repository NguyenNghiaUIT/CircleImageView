package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ChatViewTestActivity extends AppCompatActivity {

    private ChatView ciChatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view_test);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ava3);
        ciChatView = (ChatView) findViewById(R.id.ci_chat_view);
        ciChatView.setBitmapUrls("url", "url", "url", "url", "url");
        ciChatView.drawBitmapAt(bm, 0, true);
        ciChatView.drawBitmapAt(bm, 1, false);

        ciChatView.setTitleStyle(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        ciChatView.setContentStyle(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        ciChatView.drawBitmapAt(bm, 2, true);
        ciChatView.drawUnRead("5");

        ciChatView.setStatus("1 day");
        ciChatView.setTitle("Nguyễn Nghĩa, Hoàng Ánh, Hoàng Xoan");
        ciChatView.setContent("Update frmTraCuuTiecCuoi + SQL_QuanLyTiecCuoi_NEW.sql");
    }
}
