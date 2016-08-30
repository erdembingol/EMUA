package com.evrekaguys.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;

public class LicenceScreenActivity extends AppCompatActivity {

    private static String licenceCode = "";
    private static boolean isLicenced;
    private MenuServices menuServices = new MenuServicesImpl();

    Button b;
    EditText et;

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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreenActivity.this);
                    dialog.setMessage("Lütfen Geçerli Bir Lisans Kodu Giriniz!")
                            .setCancelable(false)
                            .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = dialog.create();
                    alert.setTitle("Uyarı");
                    alert.show();
                }
            }
        });
    }

    public void clearLicenceCodeArea(View v) {

        EditText area = (EditText)findViewById(R.id.licence_code);
        area.setText("");

    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            isLicenced = menuServices.checkLicenceCode(getApplicationContext(),licenceCode);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SharedPreferences settings = getSharedPreferences("LICENCE", 0);
            boolean licenced = settings.getBoolean("licenced", false);

            if (isLicenced) {
                if (!licenced) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("licenced", true);
                    editor.commit();
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreenActivity.this);
                dialog.setMessage("Ürününüz başarıyla aktive edildi.")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LicenceScreenActivity.this, CategoryListActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.setTitle("Bilgi");
                alert.show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LicenceScreenActivity.this);
                dialog.setMessage("Geçersiz bir lisans kodu girişi yapıldı!")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.setTitle("Uyarı");
                alert.show();
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

}
