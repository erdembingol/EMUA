package com.evrekaguys.myapplication.model;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {
    private Integer categoryID;
    private String categoryName;
    private String categoryImageUrl;
    private Integer order;
    private Date createDate;

    public Category(){}

    public Category(Integer categoryID, String categoryName, String categoryImageUrl, Integer order, Date createDate){
        this.setCategoryID(categoryID);
        this.setCategoryName(categoryName);
        this.setCategoryImageUrl(categoryImageUrl);
        this.setOrder(order);
        this.setCreateDate(createDate);
    }


    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
