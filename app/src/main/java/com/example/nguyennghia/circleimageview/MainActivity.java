package com.example.nguyennghia.circleimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_single_test)
            startActivity(new Intent(this, SingleCircleImageTestActivity.class));
        else if (v.getId() == R.id.btn_listview_test)
            startActivity(new Intent(this, ListViewCircleImageTestActivity.class));
        else if (v.getId() == R.id.btn_chat_view)
            startActivity(new Intent(this, ChatViewTestActivity.class));
        else if(v.getId() == R.id.btn_ui_test)
            startActivity(new Intent(this, TestActivity.class));
    }
}
