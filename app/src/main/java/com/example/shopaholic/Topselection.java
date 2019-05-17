package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class Topselection extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView backpress,shareButton;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topselection);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.tabs);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ourpicfragment(), "");
        adapter.addFragment(new womensfashion(), "");
        adapter.addFragment(new Technology(), "");
        adapter.addFragment(new Sports(), "");
        navigationTabStrip.setCornersRadius(3);
        navigationTabStrip.setAnimationDuration(300);
        navigationTabStrip.setInactiveColor(Color.GRAY);
        navigationTabStrip.setActiveColor(Color.WHITE);
        navigationTabStrip.setTabIndex(0, true);
        navigationTabStrip.setTitles(" Picked For You ", " Women's Fashion "," Technology "," Sports ");
        navigationTabStrip.setTitleSize(25);
        navigationTabStrip.setStripColor(Color.RED);
        navigationTabStrip.setStripWeight(6);
        navigationTabStrip.setStripFactor(2);
        navigationTabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        navigationTabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        backpress=findViewById(R.id.button_search);
        shareButton=findViewById(R.id.category_share);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareIntent();
            }
        });

        viewPager.setAdapter(adapter);
        navigationTabStrip.setViewPager(viewPager,0);

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int myColor = Color.parseColor("#000000");
            window.setStatusBarColor(myColor);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back=new Intent(Topselection.this,MainActivity.class);
        startActivity(back);
        finish();
    }

    private void setShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://www.shopaholic-app.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
}
