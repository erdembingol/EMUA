package com.evrekaguys.services;

import android.content.Context;

import com.evrekaguys.services.Webservices;

/**
 * Created by HP A4 on 30.7.2016.
 */
public class MenuThread extends Thread{

    private Context c;

    public MenuThread(){}

    public MenuThread(Context c){
        this.c = c;
    }

    @Override
    public void run() {
        Webservices webservices = new Webservices();
        webservices.doInBackgroundForGetMenu(c);

    }

}
