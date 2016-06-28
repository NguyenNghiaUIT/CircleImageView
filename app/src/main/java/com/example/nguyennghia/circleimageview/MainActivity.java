package com.example.nguyennghia.circleimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        if(v.getId() == R.id.btn_single_test)
            startActivity(new Intent(this, SingleCircleImageTestActivity.class));
        else if(v.getId() == R.id.btn_listview_test)
            startActivity(new Intent(this, ListViewCircleImageTestActivity.class));
    }

}
