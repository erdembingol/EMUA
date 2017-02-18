package com.evrekaguys.myapplication.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.activity.base.BaseActivity;
import com.evrekaguys.myapplication.db.DBHelper;
import com.evrekaguys.myapplication.model.Colour;
import com.evrekaguys.services.MenuServices;
import com.evrekaguys.services.MenuServicesImpl;
import com.evrekaguys.utils.MenuUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StartActivity extends BaseActivity {

    private String update="";
    ProgressDialog dialog;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        ImageView img = (ImageView) findViewById(R.id.start);

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                view = v;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if( PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                       requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},2909);
                    }else{
                        goTabletMenu(v);
                    }
                }else{
                    goTabletMenu(v);
                }
                return true;
            }
        });

    }

    @Override
    protected void onPause() {

        super.onPause();

        if ((dialog != null) && dialog.isShowing())
            dialog.dismiss();
        dialog = null;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       switch (requestCode){
           case 2909:{
               if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                   goTabletMenu(view);
               }
               return;
           }
       }
    }

    public boolean isStoragePermissionGranted(){
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                return false;
            }
        }else{
            return true;
        }
    }

    public void goTabletMenu(View v) {

        if (isLicenced()) {
            Intent intent = new Intent(StartActivity.this, CategoryListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        } else {
            Intent intent = new Intent(StartActivity.this, LicenceScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
        }

    }

    private boolean isLicenced() {

//        SharedPreferences settings = getSharedPreferences("LICENCE", 0);
//        boolean licenced = settings.getBoolean("licenced", false);

//        return licenced;
        return true;
    }

    public class UpdateServices extends AsyncTask<Context, String, List<?>> {

        private MenuServices menuServices = new MenuServicesImpl();


        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(StartActivity.this);
            dialog.setMessage("Güncel ürün bilgileri indiriliyor. Lütfen bekleyiniz...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(true);
            dialog.show();

        }

        @Override
        protected List<?> doInBackground(Context... params) {

            String updateCategory = "";
            String updateProduct = "";

            updateCategory = menuServices.getUpdateCategoryList(params[0]);
            updateProduct = menuServices.getUpdateProductList(params[0]);

            if((updateCategory.equals("1") && updateProduct.equals("1"))||(updateCategory.equals("1") && updateProduct.equals("0"))||(updateCategory.equals("0") && updateProduct.equals("1")))
                menuServices.setUpdateSuccess(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(List<?> objects) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    public void update1(View v) {
        update = update+"1";
    }

    public void update2(View v) {
        update = update+"2";
    }

    public void update3(View v) {
        update = update+"3";
    }

    public void update4(View v) {
        update = update+"4";

        if("2314".equals(update)){
            UpdateServices updateServices = new UpdateServices();
            updateServices.execute(getApplicationContext());
            update = "";
        }
        //Toast.makeText(getApplicationContext(), "There is no update...", Toast.LENGTH_SHORT).show();
    }
}
