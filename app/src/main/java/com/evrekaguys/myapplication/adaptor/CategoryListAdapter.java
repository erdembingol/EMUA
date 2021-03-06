package com.evrekaguys.myapplication.adaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.model.ImageItem;

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
	
	public View getView(int position, View view, ViewGroup parent) {

		View gridView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.single_item_in_category_list, parent, false);
		} else {
			gridView = (View) view;
		}

		/* set category name */
		String[] categoryInfo = itemname.get(position).split("/-/");
		TextView txtTitle = (TextView) gridView.findViewById(R.id.category_name_label);
		txtTitle.setText(categoryInfo[0]);

		/* set category image */
		ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
		imageView.setImageBitmap(imgid.get(position));

		return gridView;

	}

}
