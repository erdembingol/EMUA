package com.evrekaguys.myapplication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by HP A4 on 30.5.2016.
 */
public class Product implements Serializable {
    private Integer productID;
    private String productName;
    private String productImageUrl;
    private Integer order;
    private String productDetail;
    private String serviceTime;
    private BigDecimal productPrice;
    private Date createDate;
    private Integer categoryID;

    public Product(){

    }

    public Product(Integer productID, String productName, String productImageUrl, Integer order, String productDetail, BigDecimal productPrice, Date createDate, Integer categoryID){
        this.setProductID(productID);
        this.setProductName(productName);
        this.setProductImageUrl(productImageUrl);
        this.setOrder(order);
        this.setProductDetail(productDetail);
        this.setProductPrice(productPrice);
        this.setCreateDate(createDate);
        this.setCategoryID(categoryID);
    }


    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}
