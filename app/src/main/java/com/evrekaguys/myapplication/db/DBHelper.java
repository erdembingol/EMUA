package com.evrekaguys.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.Company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "emuamenuDB";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_COMPANY = "company";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_table_categories = "CREATE TABLE "+TABLE_CATEGORIES+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, CategoryID INTEGER, Name TEXT, ImageUrl TEXT, CategoryOrder INTEGER, CreateDate LONG"+")";
        String sql_table_products = "CREATE TABLE "+TABLE_PRODUCTS+" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,ProductID INTEGER, Name TEXT, ImageUrl TEXT, ProductOrder INTEGER, Detail TEXT, ServiceTime TEXT, Price DECIMAL, CreateDate LONG,ProductCategoryID INTEGER, FOREIGN KEY(ProductCategoryID) REFERENCES categories(CategoryID)"+")";
        String sql_table_company = "CREATE TABLE "+TABLE_COMPANY+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, CompanyID INTEGER, Name TEXT, LogoUrl TEXT, Adress TEXT, Mail TEXT, Phone TEXT, CreateDate LONG"+")";
        db.execSQL(sql_table_categories);
        db.execSQL(sql_table_products);
        db.execSQL(sql_table_company);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMPANY);
        onCreate(db);
    }

    public void insertCategory(Category category){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CategoryID", category.getCategoryID());
        values.put("Name", category.getCategoryName());
        values.put("ImageUrl", category.getCategoryImageUrl());
        values.put("CategoryOrder", category.getOrder());
        values.put("CreateDate", category.getCreateDate().getTime());

        database.insert(TABLE_CATEGORIES,null,values);
        database.close();
    }

    public void insertCompany(Company company){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CompanyID", company.getCompanyID());
        values.put("Name", company.getCompanyName());
        values.put("LogoUrl", company.getCompanyLogoUrl());
        values.put("Adress", company.getAdress());
        values.put("Mail", company.getMail());
        values.put("Phone", company.getPhone());
        values.put("CreateDate", company.getCreateDate().getTime());

        database.insert(TABLE_COMPANY,null,values);
        database.close();
    }

    public void insertProduct(Product product){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ProductID", product.getProductID());
        values.put("Name", product.getProductName());
        values.put("ImageUrl", product.getProductImageUrl());
        values.put("ProductOrder", product.getOrder());
        values.put("Detail", product.getProductDetail());
        values.put("ServiceTime", product.getServiceTime());
        values.put("Price", product.getProductPrice().doubleValue());
        values.put("CreateDate", product.getCreateDate().getTime());
        values.put("ProductCategoryID",product.getCategoryID());

        database.insert(TABLE_PRODUCTS,null,values);
        database.close();
    }

    public  List<Product> getAllProducts(){
        List<Product> products = new ArrayList<Product>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.query(TABLE_PRODUCTS,new String[]{"ID", "ProductID", "Name", "ImageUrl", "ProductOrder", "Detail", "ServiceTime", "Price", "CreateDate", "ProductCategoryID"},null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Product product = new Product();
            product.setProductID(cursor.getInt(1));
            product.setProductName(cursor.getString(2));
            product.setProductImageUrl(cursor.getString(3));
            product.setOrder(cursor.getInt(4));
            product.setProductDetail(cursor.getString(5));
            product.setServiceTime(cursor.getString(6));
            product.setProductPrice(BigDecimal.valueOf(cursor.getDouble(7)));
            Date createDate = new Date(cursor.getLong(8));
            product.setCreateDate(createDate);
            product.setCategoryID(cursor.getInt(9));

            products.add(product);
        }
        database.close();
        return  products;
    }

    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<Category>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.query(TABLE_CATEGORIES,new String[]{"ID","CategoryID","Name","ImageUrl", "CategoryOrder", "CreateDate"},null,null,null,null,null);
        while(cursor.moveToNext()){
            Category category = new Category();
            category.setCategoryID(cursor.getInt(1));
            category.setCategoryName(cursor.getString(2));
            category.setCategoryImageUrl(cursor.getString(3));
            category.setOrder(cursor.getInt(4));
            Date createDate = new Date(cursor.getLong(5));
            category.setCreateDate(createDate);

            categories.add(category);
        }
        database.close();
        return categories;
    }

    public Company getCompany(){
        Company company = new Company();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.query(TABLE_COMPANY,new String[]{"ID","CompanyID","Name","LogoUrl", "Adress", "Mail", "Phone", "CreateDate"},null,null,null,null,null);
        while(cursor.moveToNext()){
            company.setCompanyID(cursor.getInt(1));
            company.setCompanyName(cursor.getString(2));
            company.setCompanyLogoUrl(cursor.getString(3));
            company.setAdress(cursor.getString(4));
            company.setMail(cursor.getString(5));
            company.setPhone(cursor.getString(6));
            Date createDate = new Date(cursor.getLong(7));
            company.setCreateDate(createDate);

        }
        database.close();
        return company;
    }

    public boolean deleteProducts(){
        SQLiteDatabase database = this.getWritableDatabase();
        int state = database.delete(TABLE_PRODUCTS,null,null);
        database.close();
        return state > 0;
    }

    public boolean deleteCategories(){
        SQLiteDatabase database = this.getWritableDatabase();
        int state = database.delete(TABLE_CATEGORIES,null,null);
        database.close();
        return state > 0;
    }

    public boolean deleteCompany(){
        SQLiteDatabase database = this.getWritableDatabase();
        int state = database.delete(TABLE_COMPANY,null,null);
        database.close();
        return state > 0;
    }

}
