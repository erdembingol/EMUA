package com.evrekaguys.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MenuUtils {

    public static Bitmap loadImageSpecificLocation(String filePath){
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;

        File imgFile = new  File(filePath);
        if (imgFile.exists()) {
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
        } else
            return false;
    }

    public static String getMacAddress(Context c) {
        StringBuilder res1 = new StringBuilder();

        try{
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface nif : all){
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                for(byte b: macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length()-1);
                }
            }
        } catch(Exception ex) {
            return "00:00:00:00:00:00";
        }

        if (res1 != null) {
            String macAddress = res1.toString();
            macAddress = macAddress.replaceAll(":","").toUpperCase();

            return macAddress;
        } else {
            return "02:00:00:00:00:00";
        }
    }

    /*
        public static String getMacAddress(Context c){
            WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            String macAddress = wInfo.getMacAddress();
            macAddress = macAddress.replaceAll(":","").toUpperCase();
            return macAddress;
        }
    */
}
