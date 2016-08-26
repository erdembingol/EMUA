package com.evrekaguys.services;

import android.content.Context;

import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Category;

import java.util.List;

public class Webservices {
    private MenuServices menuServices = new MenuServicesImpl();

    private void downloadCategoryListToLocalDB(Context c){
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteCategories();

        List<Category> categoryList = menuServices.getCategoryList(c);
        for (int i=0;i<categoryList.size();i++){
            Category category = categoryList.get(i);
            dbHelper.insertCategory(category);
        }
    }

    private void downloadProductListToLocalDB(Context c){
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteProducts();

        List<Product> productList = menuServices.getProductList(c);
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
