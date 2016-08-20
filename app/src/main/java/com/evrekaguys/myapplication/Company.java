package com.evrekaguys.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HP A4 on 30.5.2016.
 */
public class Company implements Serializable {
    private Integer companyID;
    private String companyName;
    private String companyLogoUrl;
    private String adress;
    private String mail;
    private String phone;
    private Date createDate;

    public Company(){

    }

    public Company(Integer companyID, String companyName, String companyLogoUrl, String adress, String mail, String phone, Date createDate){
        this.setCompanyID(companyID);
        this.setCompanyName(companyName);
        this.setCompanyLogoUrl(companyLogoUrl);
        this.setAdress(adress);
        this.setMail(mail);
        this.setPhone(phone);
        this.setCreateDate(createDate);
    }


    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
