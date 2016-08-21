package com.evrekaguys.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;
import com.evrekaguys.utils.MenuUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Serializable{

    ListView list;
    List<Map<String,Object>> itemname = new ArrayList<Map<String,Object>>();
    List<String> itemName = new ArrayList<String>();

    List<Bitmap> imgid = new ArrayList<Bitmap>();
    private  MenuServices menuServices = new MenuServicesImpl();
    private MenuThread menuThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SharedPreferences settings = getSharedPreferences("SQL", 0);
        boolean firstTime = settings.getBoolean("firstTime", true);
        List<Category> categories = null;
        try {
            if (MenuUtils.InternetKontrol((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
              //menuThread = new MenuThread(getApplicationContext());
                //menuThread.start();
                  AsyncTask<Context, String, List<?>> downloadCategoryListToLocalDB = new Services("downloadCategoryListToLocalDB").execute(getApplicationContext());
                  //downloadCategoryListToLocalDB.cancel(true);
               // new Services("downloadProductListToLocalDB").execute(getApplicationContext());
            }

            if (firstTime) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Category> categoryList = null;
        List<Product> productList = null;
        try {
            categoryList = menuServices.getCategoryListFromDB(getApplicationContext());
            productList = menuServices.getProductListFromDB(getApplicationContext());
        } catch (Exception e) {
        }

        for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                Map<String,Object> categoryMap = new HashMap<String, Object>();
                categoryMap.put("categoryName", category.getCategoryName());
            itemName.add(category.getCategoryName());
            int productNumber =0;
            for(int j=0;j<productList.size();j++){
                if(category.getCategoryID().equals(productList.get(j).getCategoryID())){
                    productNumber = productNumber + 1;
                }
            }
            categoryMap.put("productNumber",productNumber);
            itemName.set(i, itemName.get(i).concat("/-/"+productNumber));
                itemname.add(categoryMap);
                imgid.add(MenuUtils.loadImageSpecificLocation(categoryList.get(i).getCategoryImageUrl()));
            }



        CustomListAdapter adapter = new CustomListAdapter(this, itemName, imgid);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);

        final ArrayList<Product> finalProductList = (ArrayList<Product>) productList;
        final List<Category> finalCategoryList = categoryList;
        list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                /*String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                */
                    String categoryName = parent.getItemAtPosition(position).toString();
                    Category selectedCategory = finalCategoryList.get(position);
                    Intent newActivity = new Intent(MainActivity.this, GaleriOlusturmaActivity.class);
                    newActivity.putExtra("categoryName", categoryName);
                    newActivity.putExtra("productList",finalProductList);
                    newActivity.putExtra("selectedCategory",selectedCategory);
                    //newActivity.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) finalProductList);

                    startActivityForResult(newActivity, 0);
                }
            });
        }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
