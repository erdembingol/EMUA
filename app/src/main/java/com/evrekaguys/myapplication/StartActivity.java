package com.evrekaguys.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by HP A4 on 29.5.2016.
 */
public class StartActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        ImageView img = (ImageView) findViewById(R.id.start);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent newActivity = new Intent(StartActivity.this, MainActivity.class);
                newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newActivity);
                return true;
            }
        });
    }
}
