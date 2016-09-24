package com.evrekaguys.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.evrekaguys.myapplication.model.Category;
import com.evrekaguys.myapplication.model.Company;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Product;
import com.evrekaguys.utils.MenuUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
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

public class MenuServicesImpl implements MenuServices {

    @Override
    public Company getCompanyFromDB(Context c) {

        DBHelper dbHelper = new DBHelper(c);
        Company company = dbHelper.getCompany();

        return company;

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

    @Override
    public boolean checkLicenceCode(Context c, String licenceCode) {

        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_CHECK_LICENCE_CODE);
        request.addProperty("MAC", MenuUtils.getMacAddress(c));
        request.addProperty("LicenceCode",licenceCode);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServiceConstants.URL);
        try {
            androidHttpTransport.call(WebServiceConstants.SOAP_ACTION_CHECK_LICENCE_CODE, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            return Boolean.valueOf(response.toString());
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Company getCompany(Context c) {

        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_GET_COMPANY);
        request.addProperty("MAC",MenuUtils.getMacAddress(c));

        Company company = new Company();
        Bitmap bitmap = null;

        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_COMPANY);
        Object property = response.getProperty(0);
        if(property instanceof SoapObject) {
            SoapObject companyObj = (SoapObject) property;

            Integer companyID = Integer.parseInt(companyObj.getProperty("Company_ID").toString());
            String companyName = companyObj.getProperty("Name").toString();
            String logoUrl = companyObj.getProperty("LogoUrl").toString();
            String adress = companyObj.getProperty("Adress").toString();
            String mail = companyObj.getProperty("Mail").toString();
            String phone = companyObj.getProperty("Phone").toString();
            Date createdDate = new Date();
            try {
                createdDate = new SimpleDateFormat().parse(companyObj.getProperty("CreatedDate").toString());
            } catch (ParseException e) {
                createdDate = new Date();
            }
            company.setCompanyID(companyID);
            company.setCompanyName(companyName);
            String[] imageArray = logoUrl.split("/");
            String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
            company.setCompanyLogoUrl(c.getExternalFilesDir(null) + "/EmuaTablet/"+imageFileName);
            try {
                bitmap = null;
                InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(logoUrl,"UTF-8").replace("+", "%20")).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                inputStream.close();
            } catch (IOException e) {}

            createDirectoryAndSaveFile(c, bitmap, imageFileName);
            company.setAdress(adress);
            company.setMail(mail);
            company.setPhone(phone);
            company.setCreateDate(createdDate);
        }

        return company;

    }

    @Override
    public List<Category> getCategoryList(Context c) {

        SoapObject request = new SoapObject(WebServiceConstants.NAMESPACE, WebServiceConstants.METHOD_NAME_GET_CATEGORY);
        request.addProperty("MAC",MenuUtils.getMacAddress(c));

        Category category = new Category();
        List<Category> categories = new ArrayList<Category>();
        Bitmap bitmap = null;

        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_CATEGORY);
        for (int i=0; i<response.getPropertyCount();i++) {
            category = new Category();
            Object property = response.getProperty(i);

            if (property instanceof  SoapObject) {
                SoapObject categoryObj = (SoapObject) property;

                Integer categoryID = Integer.parseInt(categoryObj.getProperty("Category_ID").toString());
                String categoryName = categoryObj.getProperty("Name").toString();
                String imageUrl = categoryObj.getProperty("ImageUrl").toString();
                Integer categoryOrder = Integer.parseInt(categoryObj.getProperty("OrderNumber").toString());
                Date createdDate = new Date();
                try {
                    createdDate = new SimpleDateFormat().parse(categoryObj.getProperty("CreatedDate").toString());
                } catch (ParseException e) {
                    createdDate = new Date();
                }
                category.setCategoryID(categoryID);
                category.setCategoryName(categoryName);
                String[] imageArray = imageUrl.split("/");
                String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
                category.setCategoryImageUrl(c.getExternalFilesDir(null) + "/EmuaTablet/"+imageFileName);
                try {
                    bitmap = null;
                    InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(imageUrl,"UTF-8").replace("+", "%20")).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    inputStream.close();
                } catch (IOException e) {}

                createDirectoryAndSaveFile(c,bitmap, imageFileName);
                category.setOrder(categoryOrder);
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

        Product product = new Product();
        List<Product> products = new ArrayList<Product>();
        Bitmap bitmap = null;

        SoapObject response = connectToWebService(request,WebServiceConstants.SOAP_ACTION_GET_PRODUCT);
        for(int i=0; i<response.getPropertyCount();i++) {
            product = new Product();
            Object property = response.getProperty(i);

            if(property instanceof  SoapObject) {
                SoapObject productObj = (SoapObject) property;

                Integer productID = Integer.parseInt(productObj.getProperty("ProductSecondID").toString());
                String productName = productObj.getProperty("Name").toString();
                String imageUrl = productObj.getProperty("ImageUrl").toString();
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
                product.setProductID(productID);
                product.setProductName(productName);
                String[] imageArray= imageUrl.split("/");
                String imageFileName = imageArray[imageArray.length-1].contains(" ") ? imageArray[imageArray.length-1].replace(" ", "_") : imageArray[imageArray.length-1];
                product.setProductImageUrl(c.getExternalFilesDir(null) + "/EmuaTablet/"+imageFileName);
                try {
                    bitmap = null;
                    InputStream inputStream = new URL(WebServiceConstants.SERVICE_LINK + URLEncoder.encode(imageUrl,"UTF-8").replace("+", "%20")).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createDirectoryAndSaveFile(c,bitmap, imageFileName);
                product.setOrder(productOrder);
                product.setCreateDate(createdDate);
                product.setProductDetail(detail);
                product.setServiceTime(serviceTime);
                product.setProductPrice(price);
                product.setCategoryID(categoryID);
            }

            products.add(product);
        }

        return products;

    }

    private SoapObject connectToWebService(SoapObject request, String soapAction) {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServiceConstants.URL);
        androidHttpTransport.debug = true;

        SoapObject response = null;
        try {
            androidHttpTransport.call(soapAction, envelope);
            response = (SoapObject) envelope.getResponse();
        } catch (IOException e) {}
          catch (XmlPullParserException e) {}

        return response;

    }

    private void createDirectoryAndSaveFile(Context c, Bitmap imageToSave, String fileName) {

        File direct = new File(c.getExternalFilesDir(null) + "/EmuaTablet");

        if (!direct.exists()) {
            File wallpaperDirectory = new File(c.getExternalFilesDir(null) + "/EmuaTablet");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File(c.getExternalFilesDir(null) + "/EmuaTablet"), fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            //imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
