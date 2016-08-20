package com.evrekaguys.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by HP A4 on 29.5.2016.
 */
public class SwipeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_layout);
        int selectedItem = getIntent().getIntExtra("selected",0);
        ArrayList<Product> productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, productList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selectedItem);
    }
}
