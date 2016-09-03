package com.evrekaguys.myapplication.adaptor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evrekaguys.myapplication.R;

import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final List<String> itemname;
	private final List<Bitmap> imgid;
	
	public CategoryListAdapter(Activity context, List<String> itemname, List<Bitmap> imgid) {
		super(context, R.layout.single_item_in_category_list,itemname);

		this.context = context;
		this.itemname = itemname;
		this.imgid = imgid;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View gridView = inflater.inflate(R.layout.single_item_in_category_list, null,true);

		if (view == null) {
			String[] categoryInfo = itemname.get(position).split("/-/");
			TextView txtTitle = (TextView) gridView.findViewById(R.id.category_name_label);
			txtTitle.setText(categoryInfo[0]);

			ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
			imageView.setImageBitmap(imgid.get(position));

			TextView extratxt = (TextView) gridView.findViewById(R.id.category_description_label);
			extratxt.setText("Bu kategori altında " + categoryInfo[1] + " adet ürün vardır.");
		} else {
			gridView = (View) view;
		}

		return gridView;
	}
}
