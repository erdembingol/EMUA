package com.evrekaguys.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.activity.base.BaseActivity;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Colour;
import com.evrekaguys.utils.MenuUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StartActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        ImageView img = (ImageView) findViewById(R.id.start);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                goTabletMenu(v);
                return true;
            }
        });

    }

    public void goTabletMenu(View v) {

        if (isLicenced()) {
            Intent intent = new Intent(StartActivity.this, CategoryListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        } else {
            Intent intent = new Intent(StartActivity.this, LicenceScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        }

    }

    private boolean isLicenced() {

        SharedPreferences settings = getSharedPreferences("LICENCE", 0);
        boolean licenced = settings.getBoolean("licenced", false);

        return licenced;

    }
}
