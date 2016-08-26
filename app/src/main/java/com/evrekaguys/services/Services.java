package com.evrekaguys.services;

import android.content.Context;
import android.os.AsyncTask;

import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.Company;

import java.util.List;

public class Services extends AsyncTask<Context, String, List<?>> {

    private String requestType = "";
    private MenuServices menuServices = new MenuServicesImpl();

    public Services(String requestType) {
        this.requestType = requestType;
    }

    private void downloadCategoryListToLocalDB(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteCategories();

        List<Category> categoryList = menuServices.getCategoryList(c);
        for (int i=0;i<categoryList.size();i++){
            Category category = categoryList.get(i);
            dbHelper.insertCategory(category);
        }
    }

    private void downloadProductListToLocalDB(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteProducts();

        List<Product> productList = menuServices.getProductList(c);
        for (int i=0;i<productList.size();i++){
            Product product = productList.get(i);
            dbHelper.insertProduct(product);
        }
    }

    private void getCompany(Context c){
        DBHelper dbHelper = new DBHelper(c);
        dbHelper.deleteCompany();

        Company company = menuServices.getCompany(c);
        dbHelper.insertCompany(company);
    }

    private List<Category> getCategoryList(Context c){
        DBHelper dbHelper = new DBHelper(c);
        List<Category> categoryList = dbHelper.getAllCategories();

        return categoryList;
    }

    private List<Product> getProductList(Context c){
        DBHelper dbHelper = new DBHelper(c);
        List<Product> productList = dbHelper.getAllProducts();

        return  productList;
    }

    @Override
    protected List<?> doInBackground(Context... params){
        // if("downloadCategoryListToLocalDB".equals(requestType)){
        if(!isCancelled()) {
            downloadCategoryListToLocalDB(params[0]);
            //}else if("downloadProductListToLocalDB".equals(requestType)){
            downloadProductListToLocalDB(params[0]);

            getCompany(params[0]);
        }

        return null;

        //}else if("getCategoryList".equals(requestType)){
         //   return getCategoryList(params[0]);
        //}else if("getProductList".equals(requestType)){
          //  return  getProductList(params[0]);
        //}else
        //       return null;
    }
}
