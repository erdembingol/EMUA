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

public class CustomListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final List<String> itemname;
	private final List<Bitmap> imgid;
	
	public CustomListAdapter(Activity context, List<String> itemname, List<Bitmap> imgid) {
		super(context, R.layout.product_list_product_format,itemname);

		this.context = context;
		this.itemname = itemname;
		this.imgid = imgid;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.product_list_product_format, null,true);

		String [] categoryInfo = itemname.get(position).split("/-/");
		TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		txtTitle.setText(categoryInfo[0]);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		imageView.setImageBitmap(imgid.get(position));

		TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
		extratxt.setText(txtTitle.getText() + " kategorisi altında "+ categoryInfo[1]+ " adet ürün vardır.");

		return rowView;
	}
}
