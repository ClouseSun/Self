package com.abition.self;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
                implements View.OnClickListener{

    private ViewPager viewPager;
    NewPlanDialog newPlanDialog;
    PswResetDialog pswResetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newPlanDialog = new NewPlanDialog();
        pswResetDialog = new PswResetDialog();

        viewPager = (ViewPager) findViewById(R.id.vp_content);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        //
        viewPager.setOffscreenPageLimit(6);


        //

        final LinearLayout funcBar = (LinearLayout) findViewById(R.id.ll_function_bar);

        for(int i = 0; i < funcBar.getChildCount(); i++) {
            funcBar.getChildAt(i).setOnClickListener(this);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int bR, bG, bB;
                int eR, eG, eB;

                for (int i = 0; i < funcBar.getChildCount(); i ++) {
                    if (i == position + 1 && position < 2) {
                        bR = 0x78;
                        bG = 0x78;
                        bB = 0x78;

                        eR = (int) (bR + positionOffset * (0x00 - 0x78));
                        eG = (int) (bG + positionOffset * (0xC8 - 0x78));
                        eB = (int) (bB + positionOffset * (0x53 - 0x78));

                        LinearLayout linearLayout = (LinearLayout) funcBar.getChildAt(position + 1);
                        ((ImageView) linearLayout.getChildAt(0)).setColorFilter(Color.argb(0xff, eR, eG, eB));
                        ((TextView) linearLayout.getChildAt(1)).setTextColor(Color.argb(0xff, eR, eG, eB));
                    } else if (i == position) {

                        bR = 0x00;
                        bG = 0xC8;
                        bB = 0x53;

                        eR = (int) (bR + positionOffset * (0x78 - 0x00));
                        eG = (int) (bG + positionOffset * (0x78 - 0xC8));
                        eB = (int) (bB + positionOffset * (0x78 - 0x53));

                        LinearLayout linearLayoutF = (LinearLayout) funcBar.getChildAt(position);
                        ((ImageView) linearLayoutF.getChildAt(0)).setColorFilter(Color.argb(0xff, eR, eG, eB));
                        ((TextView) linearLayoutF.getChildAt(1)).setTextColor(Color.argb(0xff, eR, eG, eB));
                    } else {
                        LinearLayout linearLayout = (LinearLayout) funcBar.getChildAt(i);

                        ((ImageView) linearLayout.getChildAt(0)).setColorFilter(0xFF787878);
                        ((TextView) linearLayout.getChildAt(1)).setTextColor(0xFF787878);

                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("MainActivity", "Position : " + position);
                for (int i = 0; i < funcBar.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) funcBar.getChildAt(i);
                    if (i == position) {
                        ((ImageView) linearLayout.getChildAt(0)).setColorFilter(0xFF00C853);
                        ((TextView) linearLayout.getChildAt(1)).setTextColor(0xFF00C853);
                    } else {
                        ((ImageView) linearLayout.getChildAt(0)).setColorFilter(0xFF787878);
                        ((TextView) linearLayout.getChildAt(1)).setTextColor(0xFF787878);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_plan :
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_share :
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_me :
                viewPager.setCurrentItem(2);
                break;
        }
    }


}
