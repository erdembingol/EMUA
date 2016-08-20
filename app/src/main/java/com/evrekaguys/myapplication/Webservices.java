package com.evrekaguys.myapplication;

import android.content.Context;

import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;

import java.util.List;

/**
 * Created by HP A4 on 30.7.2016.
 */
public class Webservices {
    private MenuServices menuServices = new MenuServicesImpl();

    private void downloadCategoryListToLocalDB(Context c){
        List<Category> categoryList = menuServices.getCategoryList(c);
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteCategories();
        for (int i=0;i<categoryList.size();i++){
            Category category = categoryList.get(i);
            dbHelper.insertCategory(category);
        }
    }

    private void downloadProductListToLocalDB(Context c){
        List<Product> productList = menuServices.getProductList(c);

        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteProducts();
        for (int i=0;i<productList.size();i++){
            Product product = productList.get(i);
            dbHelper.insertProduct(product);
        }
    }

    public void doInBackgroundForGetMenu(Context c){
            downloadCategoryListToLocalDB(c);
            downloadProductListToLocalDB(c);
    }
}
