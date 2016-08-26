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
	//private final List<Map<String,Object>> itemname;
	private final List<String> itemname;
	private final List<Bitmap> imgid;
	
	public CustomListAdapter(Activity context, List<String> itemname /*List<Map<String,Object>> itemname*/, List<Bitmap> imgid) {
		//super(context, R.layout.mylist, itemname);
		// TODO Auto-generated constructor stub
		super(context, R.layout.mylist,itemname);
		this.context=context;
		this.itemname=itemname;
		this.imgid=imgid;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.mylist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
		String [] categoryInfo = itemname.get(position).split("/-/");
		txtTitle.setText(categoryInfo[0]);//get("categoryName").toString());
		imageView.setImageBitmap(imgid.get(position));
		extratxt.setText(txtTitle.getText() + " kategorisi altında "+ categoryInfo[1]+ " adet ürün vardır.");
		return rowView;
		
	}
}
