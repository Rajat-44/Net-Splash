package com.apps.rajat.netsplashversion2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    Button next, skip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check the Android Version to see if PageView Animation is possible
        if(Build.VERSION.SDK_INT>=21){

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //Set the main content view
        setContentView(R.layout.activity_main);

        //Declaring the variables with their appropriate id.
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        next = (Button) findViewById(R.id.btnNext);
        skip = (Button)findViewById(R.id.btnSkip);


        //Declaring the sequence of array
        layouts = new int[]{R.layout.screen1,R.layout.screen2,R.layout.screen3,R.layout.screen4,R.layout.screen5};


        addBottomDots(0);
        changeStatusBarColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainRoot.class);
                startActivity(i);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int current = getItem(+1);
                if(current<layouts.length){
                    viewPager.setCurrentItem(current);
                }else{
                    Intent i = new Intent(MainActivity.this, MainRoot.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    //adding bottom dots
    private void addBottomDots(int position){

        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dotActive);
        int[] colorInactive = getResources().getIntArray(R.array.dotInactive);
        dotsLayout.removeAllViews();

        for (int i = 0 ; i <dots.length ; i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            //dots[i].setText(Html.fromHtml("h/8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0){
            dots[position].setTextColor(colorActive[position]);
        }

    }

    private int getItem(int i){
        return  viewPager.getCurrentItem() + 1;
    }
  ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        addBottomDots(position);
        if (position == dots.length -1){

            next.setText("Proceed");
            skip.setVisibility(View.GONE);
        }else{

            next.setText("Next");
            skip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
};

//Change the Status Bar Color
    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();

            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //Page Adaptar
    public class ViewPagerAdapter extends PagerAdapter{


        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
