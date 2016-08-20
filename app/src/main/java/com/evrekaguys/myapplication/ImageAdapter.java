package com.evrekaguys.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;
import com.evrekaguys.utils.MenuUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP A4 on 29.5.2016.
 */
public class ImageAdapter extends PagerAdapter {

    private final Activity context;
    private final ArrayList<Product> productList;
    private List<Bitmap> imgid = new ArrayList<Bitmap>();
    private MenuServices menuServices = new MenuServicesImpl();
    private int[] GalImages = new int[] {
            R.drawable.menu,
            R.drawable.ornek2,
            R.drawable.ornek3,
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3
    };
    ImageAdapter(Activity context, ArrayList<Product> productList){
        this.context=context;
        this.productList = productList;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       // ImageView imageView = new ImageView(context);
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.swipe_detail_format, null,true);
        Company company = menuServices.getCompanyFromDB(context);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.urunResim);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        final Product product = productList.get(position);
        imageView.setImageBitmap(MenuUtils.loadImageSpecificLocation(product.getProductImageUrl()));
       // imageView.setImageResource(GalImages[position]);

        final TextView urunAdi = (TextView) rowView.findViewById(R.id.urunAdi);
        urunAdi.setText(product.getProductName());

        if(!product.getProductDetail().equals(null)){
            final TextView urunDetay = (TextView) rowView.findViewById(R.id.urunDetay);
            if(product.getProductDetail().length() < urunDetay.getTextSize()) {
                urunDetay.setText(product.getProductDetail());
            }else {
                urunDetay.setText(product.getProductDetail().substring(0, Math.round(urunDetay.getTextSize())-5)+">>>>>");
                urunDetay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // custom dialog
                        final Dialog dialog = new Dialog(context, R.style.ThemeDialogCustom);
                        dialog.setContentView(R.layout.custom);
                        dialog.setTitle("Açıklama");

                        // set the custom dialog components - text, image and butto
                        TextView text = (TextView) dialog.findViewById(R.id.fullDetail);
                        text.setText(product.getProductDetail());
                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                });
            }

        }else{
            LinearLayout ll = (LinearLayout) rowView.findViewById(R.id.urunDetayLayout);
            ll.setVisibility(View.GONE);
        }
        TextView urunFiyat = (TextView) rowView.findViewById(R.id.urunFiyat);
        urunFiyat.setText("FİYAT : " + product.getProductPrice().toString() + " TL");

        TextView urunServisiSuresi = (TextView) rowView.findViewById(R.id.urunServisSuresi);
        urunServisiSuresi.setText("SERVİS SÜRESİ : " + product.getServiceTime().toString() + " DK");

        ImageView icon = (ImageView) rowView.findViewById(R.id.sirketIcon);
        icon.setImageBitmap(MenuUtils.loadImageSpecificLocation(company.getCompanyLogoUrl()));
        ((ViewPager) container).addView(rowView, 0);
        return rowView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}
