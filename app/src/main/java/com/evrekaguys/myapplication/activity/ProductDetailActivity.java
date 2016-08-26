package com.evrekaguys.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.adaptor.ProductDetailAdapter;
import com.evrekaguys.myapplication.model.Product;

import java.util.ArrayList;

public class ProductDetailActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        int selectedItem = getIntent().getIntExtra("selected",0);
        ArrayList<Product> productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ProductDetailAdapter adapter = new ProductDetailAdapter(this, productList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selectedItem);
    }
}
