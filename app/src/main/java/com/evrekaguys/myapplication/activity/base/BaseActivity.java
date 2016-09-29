package com.evrekaguys.myapplication.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.evrekaguys.myapplication.R;
import com.evrekaguys.myapplication.model.Colour;
import com.evrekaguys.utils.MenuUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        List<Colour> colours = MenuUtils.readFromFile(this);

        if (colours != null) {
            String actionBarColor = colours.get(0).value;
            String statusBarColor = colours.get(1).value;

            MenuUtils.setActionBarColor(this, actionBarColor);
            MenuUtils.setStatusBarColor(getWindow(), statusBarColor);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_color_chooser, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        List<Colour> colours = new ArrayList<>();

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_green:
                if(item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                MenuUtils.setActionBarColor(this, "#759020");
                MenuUtils.setStatusBarColor(getWindow(), "#4c6109");

                colours.add(new Colour("ActionBarColour", "#759020"));
                colours.add(new Colour("StatusBarColour", "#4c6109"));
                MenuUtils.writeToFile(this, colours);

                return true;
            case R.id.menu_orange:
                if(item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                MenuUtils.setActionBarColor(this, "#d18b3b");
                MenuUtils.setStatusBarColor(getWindow(), "#b77932");

                colours.add(new Colour("ActionBarColour", "#d18b3b"));
                colours.add(new Colour("StatusBarColour", "#b77932"));
                MenuUtils.writeToFile(this, colours);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStart() {

        super.onStart();

        List<Colour> colours = MenuUtils.readFromFile(this);

        if (colours != null) {
            String actionBarColor = colours.get(0).value;
            String statusBarColor = colours.get(1).value;

            MenuUtils.setActionBarColor(this, actionBarColor);
            MenuUtils.setStatusBarColor(getWindow(), statusBarColor);
        }

    }

    @Override
    protected void onResume() {

        super.onResume();

        List<Colour> colours = MenuUtils.readFromFile(this);

        if (colours != null) {
            String actionBarColor = colours.get(0).value;
            String statusBarColor = colours.get(1).value;

            MenuUtils.setActionBarColor(this, actionBarColor);
            MenuUtils.setStatusBarColor(getWindow(), statusBarColor);
        }

    }

}
