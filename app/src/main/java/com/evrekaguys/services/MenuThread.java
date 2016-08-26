package com.evrekaguys.services;

import android.content.Context;

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
