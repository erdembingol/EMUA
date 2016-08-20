package com.evrekaguys.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.evrekaguys.myapplication.Category;
import com.evrekaguys.myapplication.Company;
import com.evrekaguys.myapplication.DBHelper;
import com.evrekaguys.myapplication.Product;
import com.evrekaguys.utils.MenuUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HP A4 on 18.6.2016.
 */
public class MenuServicesImpl implements MenuServices {
    @Override
    public Company getCompanyFromDB(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        Company company = dbHelper.getCompany();
        return company;
    }

    @Override
    public Company getCompany(Context c) {
        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_GET_COMPANY);
        request.addProperty("MAC",MenuUtils.getMacAddress(c));
        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_COMPANY);
        Company company = new Company();
        Bitmap bitmap = null;

            Object property = response.getProperty(0);
            if(property instanceof  SoapObject) {
                SoapObject companyObj = (SoapObject) property;

                Integer companyID = Integer.parseInt(companyObj.getProperty("Company_ID").toString());
                String companyName = companyObj.getProperty("Name").toString();
                String logoUrl = companyObj.getProperty("LogoUrl").toString();
                String logoUrlName = companyObj.getProperty("Url").toString();
                String adress = companyObj.getProperty("Adress").toString();
                String mail = companyObj.getProperty("Mail").toString();
                String phone = companyObj.getProperty("Phone").toString();
                Date createdDate = new Date();
                try {
                    createdDate = new SimpleDateFormat().parse(companyObj.getProperty("CreatedDate").toString());
                } catch (ParseException e) {
                    createdDate = new Date();
                }

                // GET CATEGORYID
                company.setCompanyID(companyID);

                // GET CATEGORYNAME
                company.setCompanyName(companyName);

                // GET IMAGEURL AND SAVE IMAGE
                String[] imageArray = logoUrl.split("/");
                String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
                company.setCompanyLogoUrl("/sdcard/menu_images/"+imageFileName);
                try {
                    bitmap = null;
                    InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(logoUrl,"UTF-8").replace("+", "%20")).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                }
                createDirectoryAndSaveFile(bitmap, imageFileName);

                // GET CATEGORY ORDER
                company.setAdress(adress);

                // GET COMPANY MAIL
                company.setMail(mail);

                //GET COMPANY PHONE
                company.setPhone(phone);

                // GET CREATED DATE

                company.setCreateDate(createdDate);

            }

        return company;
    }

    private SoapObject connectToWebService(SoapObject request, String soapAction){

            SoapObject response = null;
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServiceConstants.URL);
            androidHttpTransport.debug = true;

        try {
            androidHttpTransport.call(soapAction, envelope);
            response = (SoapObject) envelope.getResponse();
        } catch (IOException e) {

        } catch (XmlPullParserException e) {

        }

        return response;

    }

    @Override
    public List<Category> getCategoryList(Context c) {
        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_GET_CATEGORY);
        request.addProperty("MAC",MenuUtils.getMacAddress(c));
        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_CATEGORY);
        Category category = new Category();
        List<Category> categories = new ArrayList<Category>();
        Bitmap bitmap = null;

        for(int i=0; i<response.getPropertyCount();i++) {
            category = new Category();
            Object property = response.getProperty(i);
            if(property instanceof  SoapObject) {
                SoapObject categoryObj = (SoapObject) property;

                Integer categoryID = Integer.parseInt(categoryObj.getProperty("Category_ID").toString());
                String categoryName = categoryObj.getProperty("Name").toString();
                String imageUrl = categoryObj.getProperty("ImageUrl").toString();
                String imageUrlName = categoryObj.getProperty("Url").toString();
                Integer categoryOrder = Integer.parseInt(categoryObj.getProperty("OrderNumber").toString());
                Date createdDate = new Date();
                try {
                    createdDate = new SimpleDateFormat().parse(categoryObj.getProperty("CreatedDate").toString());
                } catch (ParseException e) {
                    createdDate = new Date();
                }

                // GET CATEGORYID
                category.setCategoryID(categoryID);

                // GET CATEGORYNAME
                category.setCategoryName(categoryName);

                // GET IMAGEURL AND SAVE IMAGE
                String[] imageArray = imageUrl.split("/");
                String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
                category.setCategoryImageUrl("/sdcard/menu_images/"+imageFileName);
                try {
                    bitmap = null;
                    InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(imageUrl,"UTF-8").replace("+", "%20")).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                }
                createDirectoryAndSaveFile(bitmap, imageFileName);

                // GET CATEGORY ORDER
                category.setOrder(categoryOrder);

                // GET CREATED DATE

                category.setCreateDate(createdDate);

            }

            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<Product> getProductList(Context c) {
        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_GET_PRODUCT);
        request.addProperty("MAC",MenuUtils.getMacAddress(c));
        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_PRODUCT);
        Product product = new Product();
        List<Product> products = new ArrayList<Product>();
        Bitmap bitmap = null;
        for(int i=0; i<response.getPropertyCount();i++) {
            product = new Product();
            Object property = response.getProperty(i);
            if(property instanceof  SoapObject) {
                SoapObject productObj = (SoapObject) property;

                Integer productID = Integer.parseInt(productObj.getProperty("ProductSecondID").toString());
                String productName = productObj.getProperty("Name").toString();
                String imageUrl = productObj.getProperty("ImageUrl").toString();
                String imageUrlName = productObj.getProperty("Url").toString();
                Integer productOrder = Integer.parseInt(productObj.getProperty("OrderNumber").toString());
                String detail = productObj.getProperty("Detail").toString();
                String serviceTime = productObj.getProperty("ServiceTime").toString();
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(productObj.getProperty("Price").toString()));
                Integer categoryID = Integer.parseInt(productObj.getProperty("CategorySecondID").toString());
                Date createdDate = new Date();
                try {
                    createdDate = new SimpleDateFormat().parse(productObj.getProperty("CreatedDate").toString());
                } catch (ParseException e) {
                    createdDate = new Date();
                }

                // GET PRODUCTID
                product.setProductID(productID);

                // GET PRODUCT NAME
                product.setProductName(productName);

                String[] imageArray= imageUrl.split("/");
                String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
                // GET IMAGEURL AND SAVE IMAGE
                product.setProductImageUrl("/sdcard/menu_images/"+imageFileName);
                try {
                    bitmap = null;
                    InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(imageUrl,"UTF-8").replace("+", "%20")).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createDirectoryAndSaveFile(bitmap, imageFileName);

                // GET PRODUCT ORDER
                product.setOrder(productOrder);

                // GET CREATED DATE

                product.setCreateDate(createdDate);

                // GET DETAIL

                product.setProductDetail(detail);

                // GET SERVICE TIME

                product.setServiceTime(serviceTime);

                // GET PRICE

                product.setProductPrice(price);

                // GET CATEGORY ID

                product.setCategoryID(categoryID);

            }

            products.add(product);
        }
        return products;
    }

    @Override
    public List<Category> getCategoryListFromDB(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        List<Category> categoryList = dbHelper.getAllCategories();

        return categoryList;
    }

    @Override
    public List<Product> getProductListFromDB(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        List<Product> productList = dbHelper.getAllProducts();

        return  productList;
    }


    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/sdcard/menu_images");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/menu_images/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/menu_images/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
