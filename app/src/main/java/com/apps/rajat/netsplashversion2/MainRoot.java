package com.apps.rajat.netsplashversion2;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainRoot extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;

    Boolean doubleBackToExitOnce = false;

    Typeface netSplashFont;

    String Message;
    SharedPref sharedPref;
    SharedPrefLoadGoogleAuto sharedPrefLoadGoogleAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        sharedPrefLoadGoogleAuto = new SharedPrefLoadGoogleAuto(this);
        //Load Night Mode from Settings!
        if(sharedPref.loadNightModeState()==true){
            setTheme(R.style.night);
        }else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);



        NavigationView nv = (NavigationView) findViewById(R.id.navview);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  setupDrawerContent(nv);


       Fragment defaulthome = null;
        Class homeclass;
        homeclass = Home.class;
        try{
            defaulthome =  (Fragment) homeclass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentcontent, defaulthome).commit();




    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem){
        Fragment myfragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.browsermain:
                fragmentClass = Browser.class;
                break;
            case R.id.home:
                fragmentClass = Home.class;
                break;
            case R.id.setting:
                fragmentClass = Settings.class;
                break;
            case R.id.help:
                fragmentClass = Help.class;
                break;
            case R.id.about:
                fragmentClass = About.class;
                break;

                default:
                    fragmentClass = Home.class;
        }
        try{
            myfragment =  (Fragment) fragmentClass.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentcontent, myfragment).commit();
        menuItem.setChecked(true);
        if (menuItem.getItemId() == R.id.home){
           setTitle("Net Splash Version-2 Beta");
        }else{
        setTitle(menuItem.getTitle());}
        mdrawerlayout.closeDrawers();
    }

    public void browsermenu(){

    }


    private void setupDrawerContent (NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.FragmentManager fragmentManager = getFragmentManager();

        switch (item.getItemId()){
            case R.id.item_back:
                if (webView.canGoBack()){
                    webView.goBack();
                }


                return true;

            case R.id.item_forward:
                if (webView.canGoForward()){
                    webView.goForward();
                }
                return true;

            case R.id.item_home:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                webView.loadUrl("https://www.google.co.in/");
                editText.setText("");

                return true;
            case R.id.item_about:
                //bottomSheetDialog.show();
                ShowPopup();


                return true;
            case R.id.item_settings:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Important")
                        .setCancelable(true)
                        .setIcon(R.mipmap.browsernew)
                        .setMessage("If you go onto settings now, NET SPLASH will restart. Please be sure you have no unsaved activity on the Internet.")
                        .setPositiveButton("Yes, Let me in.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GoOther();
                            }
                        })
                        .setNegativeButton("No", null);
                dialog.show();

                return true;
            case R.id.item_help:
                GoHelp();
                return true;
            case R.id.item_reload:
                webView.reload();
                Toast.makeText(this, "Reloading this page.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_stopLoading:
                webView.stopLoading();
                Toast.makeText(this, "Stopped.", Toast.LENGTH_SHORT).show();
                return true;


            default:
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void onBackPressed() {
        if (doubleBackToExitOnce){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

            //super.onBackPressed();
            return;
        }
        this.doubleBackToExitOnce = true;

        Snackbar.make(findViewById(R.id.snack), "Press back again to exit.", Snackbar.LENGTH_INDEFINITE)
                .setDuration(2000)
                .show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitOnce=false;
            }
        },2000);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
