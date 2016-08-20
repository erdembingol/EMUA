package com.evrekaguys.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

import com.evrekaguys.utils.MenuUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GaleriOlusturmaActivity extends AppCompatActivity implements Serializable{
	private GridView gridView;
	private GridViewAdapter gridAdapter;
	private ArrayList<Product> productListForSwipe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        String categoryName = getIntent().getStringExtra("categoryName").split("/-/")[0].toUpperCase();
		Category selectedCategory = (Category) getIntent().getSerializableExtra("selectedCategory");
		final ArrayList<Product> productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
		setTitle(categoryName);
		gridView = (GridView) findViewById(R.id.gridView);
		gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData(productList,  selectedCategory));
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ImageItem item = (ImageItem) parent.getItemAtPosition(position);
				//Create intent
				Intent intent = new Intent(GaleriOlusturmaActivity.this, SwipeActivity.class);
				intent.putExtra("productList",productListForSwipe);
				intent.putExtra("title", item.getTitle());
				//intent.putExtra("image", item.getImage());
				intent.putExtra("selected",position);

				//Start details activity
				startActivityForResult(intent, 0);

			}
		});
	}

	// Prepare some dummy data for gridview
	private ArrayList<ImageItem> getData(List<Product> productList, Category selectedCategory) {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
		List<Product> productListByCategory = new ArrayList<Product>();
		for(int i=0;i<productList.size();i++){
			if(productList.get(i).getCategoryID().equals(selectedCategory.getCategoryID()))
				productListByCategory.add(productList.get(i));
		}
		productListForSwipe = new ArrayList<Product>();
		productListForSwipe = (ArrayList<Product>) productListByCategory;
		for(int j=0;j<productListByCategory.size();j++){
			ImageItem imageItem = new ImageItem();
			imageItem.setTitle(productListByCategory.get(j).getProductName());
			imageItem.setImage((MenuUtils.loadImageSpecificLocation(productListByCategory.get(j).getProductImageUrl())));
			imageItems.add(imageItem);
		}
		/*
		TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
		for (int i = 0; i < imgs.length(); i++) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
			imageItems.add(new ImageItem(bitmap, "Image#" + i));
		}*/
		return imageItems;
	}
}