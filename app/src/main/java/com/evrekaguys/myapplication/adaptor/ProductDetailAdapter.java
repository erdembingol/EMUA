package com.evrekaguys.myapplication.adaptor;

import android.app.Activity;
import android.app.Dialog;
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

public class ProductDetailAdapter extends PagerAdapter {

    private final Activity context;
    private final ArrayList<Product> productList;
    private MenuServices menuServices = new MenuServicesImpl();

    public ProductDetailAdapter(Activity context, ArrayList<Product> productList) {

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

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.product_detail_format, null,true);

        Company company = menuServices.getCompanyFromDB(context);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.urunResim);

        final Product product = productList.get(position);
        imageView.setImageBitmap(MenuUtils.loadImageSpecificLocation(product.getProductImageUrl()));

        /* set the product name */
        final TextView urunAdi = (TextView) rowView.findViewById(R.id.urunAdi);
        urunAdi.setText(product.getProductName());

        /* set the product detail */
        final TextView urunDetay = (TextView) rowView.findViewById(R.id.urunDetay);
        if (product.getProductDetail() == null || product.getProductDetail().length() == 0 || product.getProductDetail().trim().equalsIgnoreCase("Any Type")) {
            urunDetay.setText("");
            LinearLayout ll = (LinearLayout) rowView.findViewById(R.id.urunDetayLayout);
            ll.setVisibility(View.GONE);
        } else {
            urunDetay.setText("Detay >>>");
            urunDetay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context, R.style.ThemeDialogCustom);
                    dialog.setContentView(R.layout.description_in_product_detail);
                    dialog.setTitle("Açıklama");

                    TextView text = (TextView) dialog.findViewById(R.id.fullDetail);
                    text.setText(product.getProductDetail());

                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }

        /* set the product cost */
        TextView urunFiyat = (TextView) rowView.findViewById(R.id.urunFiyat);
        urunFiyat.setText(product.getProductPrice().toString() + " TL");

        /* set the product service time */
        TextView urunServisiSuresi = (TextView) rowView.findViewById(R.id.urunServisSuresi);
        urunServisiSuresi.setText(product.getServiceTime().toString() + " dk");

        /* set the company image */
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
