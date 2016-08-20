package com.evrekaguys.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LicenceScreen extends AppCompatActivity {

    private final String NAMESPACE = "http://tempuri.org/";
    private final String METHOD_NAME = "SelectLicenceCode";
    private final String SOAP_ACTION = "http://tempuri.org/SelectLicenceCode";
    private final String URL = "http://demo.avnibabaoglu.com/EmuaService.asmx";

    private static String licenceCode = "";
    private static String mac = "";
    private static boolean isLicenced;
    private MenuServices menuServices = new MenuServicesImpl();

    Button b;
    EditText et;

    TextView tw;//////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence_screen);

        et = (EditText) findViewById(R.id.licence_code);
        b = (Button) findViewById(R.id.send);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et.getText().length() != 0 && et.getText().toString() != "") {
                    licenceCode = et.getText().toString();

                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreen.this);
                    dialog.setMessage("Please enter Licence Code...")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = dialog.create();
                    alert.setTitle("Warning !!!");
                    alert.show();
                }
            }
        });
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            isLicenced = menuServices.checkLicenceCode(getApplicationContext(),licenceCode);
            //isLicenced = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (isLicenced) {
                saveLicenceCode(new View(LicenceScreen.this));

                AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreen.this);
                dialog.setMessage("Licence is activated ...")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LicenceScreen.this, MainActivity.class);
                                tw = (TextView) findViewById(R.id.textView3);
                                tw.setText(mac + " # " + licenceCode);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.setTitle("Info !!!");
                alert.show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreen.this);
                dialog.setMessage("Licence Code is invalid ...")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.setTitle("Warning !!!");
                alert.show();
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

    private void saveLicenceCode(View v) {
        try {
            FileOutputStream outputStream = openFileOutput("licence.txt", Context.MODE_PRIVATE);
            outputStream.write(licenceCode.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
