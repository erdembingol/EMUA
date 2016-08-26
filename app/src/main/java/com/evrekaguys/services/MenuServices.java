package com.evrekaguys.services;

import android.content.Context;

import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.Company;
import com.evrekaguys.myapplication.model.Product;

import java.util.List;

/**
 * Created by HP A4 on 18.6.2016.
 */
public interface MenuServices {
    public List<Category> getCategoryList(Context c);
    public List<Product> getProductList(Context c);
    public List<Category> getCategoryListFromDB(Context c);
    public List<Product> getProductListFromDB(Context c);
    public Company getCompany(Context c);
    public Company getCompanyFromDB(Context c);
    public boolean checkLicenceCode(Context c, String licenceCode);
}
