package com.evrekaguys.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.evrekaguys.myapplication.activity.base.BaseActivity;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Colour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuUtils {

    public static Bitmap loadImageSpecificLocation(String filePath) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;

        File imgFile = new  File(String.valueOf(Environment.getExternalStoragePublicDirectory(filePath)));
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), o);
            return myBitmap;
        }

        return null;

    }

    public static boolean checkInternet(ConnectivityManager manager) {

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

    public static void setActionBarColor(BaseActivity activity, String color) {

        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

    }

    public static void setStatusBarColor(Window window, String color) {

        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor((Color.parseColor(color)));
        }

    }

    public static void writeToFile(BaseActivity activity, List<Colour> colours) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput("colours.txt", Context.MODE_PRIVATE));

            for (int i = 0; i < colours.size(); i++) {
                outputStreamWriter.write(colours.get(i).name + "&" + colours.get(i).value + "\n");
            }

            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Colour> readFromFile(BaseActivity activity) {

        List<Colour> colours = new ArrayList<>();

        try {
            InputStream inputStream = activity.openFileInput("colours.txt");

            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String receiveString = "";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    colours.add(parseToColour(receiveString));
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return colours;

    }

    private static Colour parseToColour(String text) {

        String[] parts = text.split("&");

        Colour colour = new Colour(parts[0], parts[1]);

        return colour;

    }

}
