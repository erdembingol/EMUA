package com.evrekaguys.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.activity.base.BaseActivity;
import com.evrekaguys.myapplication.adaptor.CategoryListAdapter;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.Company;
import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;
import com.evrekaguys.utils.MenuUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends BaseActivity implements Serializable{

    GridView categoriesGridView;
    List<String> itemName = new ArrayList<String>();
    List<Bitmap> imgId = new ArrayList<Bitmap>();
    ProgressDialog dialog;
    private MenuServices menuServices = new MenuServicesImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle(R.string.app_name);

        try {
//            if (MenuUtils.checkInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
//                Services services = new Services();
//                services.execute(getApplicationContext());
//            }else{
                showCategoryList();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CategoryListActivity.this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

    }

    @Override
    protected void onPause() {

        super.onPause();

        if ((dialog != null) && dialog.isShowing())
            dialog.dismiss();
        dialog = null;

    }

    public void showCategoryList() {

        List<Category> categoryList = null;
        List<Product> productList = null;
        try {
            categoryList = menuServices.getCategoryListFromDB(getApplicationContext());
            productList = menuServices.getProductListFromDB(getApplicationContext());
        } catch (Exception e) {}

        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            itemName.add(category.getCategoryName());

            int productNumber =0;
            for(int j=0;j<productList.size();j++){
                if(category.getCategoryID().equals(productList.get(j).getCategoryID())){
                    productNumber = productNumber + 1;
                }
            }
            itemName.set(i, itemName.get(i).concat("/-/"+productNumber));
            imgId.add(MenuUtils.loadImageSpecificLocation(categoryList.get(i).getCategoryImageUrl()));
        }
        CategoryListAdapter adapter = new CategoryListAdapter(CategoryListActivity.this, itemName, imgId);
        categoriesGridView = (GridView) findViewById(R.id.gridView);
        categoriesGridView.setAdapter(adapter);

        final ArrayList<Product> finalProductList = (ArrayList<Product>) productList;
        final List<Category> finalCategoryList = categoryList;

        categoriesGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String categoryName = parent.getItemAtPosition(position).toString();
                Category selectedCategory = finalCategoryList.get(position);
                Intent newActivity = new Intent(CategoryListActivity.this, ProductListActivity.class);
                newActivity.putExtra("categoryName", categoryName);
                newActivity.putExtra("productList",finalProductList);
                newActivity.putExtra("selectedCategory",selectedCategory);
                startActivityForResult(newActivity, 0);
            }
        });

    }

    public class Services extends AsyncTask<Context, String, List<?>> {

        private MenuServices menuServices = new MenuServicesImpl();

        private boolean downloadCategoryListToLocalDB(Context c) {

            DBHelper dbHelper = new DBHelper(c);
            dbHelper.deleteCategories();

            List<Category> categoryList = menuServices.getCategoryList(c);
            for (int i=0;i<categoryList.size();i++){
                Category category = categoryList.get(i);
                dbHelper.insertCategory(category);
            }

            return true;

        }

        private boolean downloadProductListToLocalDB(Context c) {

            DBHelper dbHelper = new DBHelper(c);
            dbHelper.deleteProducts();

            List<Product> productList = menuServices.getProductList(c);
            for (int i=0;i<productList.size();i++){
                Product product = productList.get(i);
                dbHelper.insertProduct(product);
            }

            return true;

        }

        private boolean downloadCompanyToLocalDB(Context c){

            DBHelper dbHelper = new DBHelper(c);
            dbHelper.deleteCompany();

            Company company = menuServices.getCompany(c);
            dbHelper.insertCompany(company);

            return true;

        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(CategoryListActivity.this);
            dialog.setMessage("Güncel ürün bilgileri indiriliyor. Lütfen bekleyiniz...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(true);
            dialog.show();

        }

        @Override
        protected List<?> doInBackground(Context... params) {

            if(!isCancelled()) {
                downloadCategoryListToLocalDB(params[0]);
                downloadProductListToLocalDB(params[0]);
                downloadCompanyToLocalDB(params[0]);
            }

            return null;

        }

        @Override
        protected void onPostExecute(List<?> objects) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }

            showCategoryList();

        }

    }
}
