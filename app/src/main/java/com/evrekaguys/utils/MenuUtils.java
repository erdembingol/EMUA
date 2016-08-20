package com.evrekaguys.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import java.io.File;

/**
 * Created by HP A4 on 18.6.2016.
 */
public class MenuUtils {

    public static Bitmap loadImageSpecificLocation(String filePath){
        File imgFile = new  File(filePath);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), o);
            return myBitmap;
        }

        return null;
    }

    public static boolean InternetKontrol(ConnectivityManager manager) {
        if (manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isAvailable()
                && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    public static String getMacAddress(Context c){
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        macAddress = macAddress.replaceAll(":","").toUpperCase();
        return macAddress;
    }

}
