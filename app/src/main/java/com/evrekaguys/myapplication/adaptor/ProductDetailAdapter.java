package com.evrekaguys.myapplication.adaptor;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.model.Company;
import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;
import com.evrekaguys.utils.MenuUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends PagerAdapter {

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
    public ProductDetailAdapter(Activity context, ArrayList<Product> productList){
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
        View rowView=inflater.inflate(R.layout.product_detail_format, null,true);
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
                        // description_in_product_detail dialog
                        final Dialog dialog = new Dialog(context, R.style.ThemeDialogCustom);
                        dialog.setContentView(R.layout.description_in_product_detail);
                        dialog.setTitle("Açıklama");

                        // set the description_in_product_detail dialog components - text, image and butto
                        TextView text = (TextView) dialog.findViewById(R.id.fullDetail);
                        text.setText(product.getProductDetail());
                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the description_in_product_detail dialog
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
