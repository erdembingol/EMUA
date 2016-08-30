package com.evrekaguys.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.adaptor.ProductListAdapter;
import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.ImageItem;
import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.utils.MenuUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements Serializable{
	private GridView gridView;
	private ProductListAdapter gridAdapter;
	private ArrayList<Product> productListForSwipe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);

		getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        String categoryName = getIntent().getStringExtra("categoryName").split("/-/")[0].toUpperCase();
		setTitle(categoryName);

		Category selectedCategory = (Category) getIntent().getSerializableExtra("selectedCategory");
		final ArrayList<Product> productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");

		gridAdapter = new ProductListAdapter(this, R.layout.grid_item_in_product_list, getData(productList,  selectedCategory));
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ImageItem item = (ImageItem) parent.getItemAtPosition(position);
				Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
				intent.putExtra("productList",productListForSwipe);
				intent.putExtra("title", item.getTitle());
				intent.putExtra("selected",position);
				startActivityForResult(intent, 0);
			}
		});
	}


	private ArrayList<ImageItem> getData(List<Product> productList, Category selectedCategory) {
		List<Product> productListByCategory = new ArrayList<Product>();
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).getCategoryID().equals(selectedCategory.getCategoryID()))
				productListByCategory.add(productList.get(i));
		}

		productListForSwipe = new ArrayList<Product>();
		productListForSwipe = (ArrayList<Product>) productListByCategory;

		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
		for(int j=0;j<productListByCategory.size();j++){
			ImageItem imageItem = new ImageItem();
			imageItem.setTitle(productListByCategory.get(j).getProductName());
			imageItem.setImage((MenuUtils.loadImageSpecificLocation(productListByCategory.get(j).getProductImageUrl())));
			imageItems.add(imageItem);
		}

		return imageItems;
	}
}