package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mymusic.R;
import com.example.mymusic.adapters.MainViewPagerAdapter;
import com.example.mymusic.fragments.HomePageFragment;
import com.example.mymusic.fragments.SearchFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static FrameLayout frameLayoutPlayerMini;
    TabLayout tabLayout;
    ViewPager viewPager;

    public static FrameLayout getFrameLayoutPlayerMini() {
        return frameLayoutPlayerMini;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        backToPlaySong();
    }

    private void backToPlaySong() {
        frameLayoutPlayerMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaySongActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new HomePageFragment(), "Home");
        mainViewPagerAdapter.addFragment(new SearchFragment(), "Search");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
    }

    private void initView() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        frameLayoutPlayerMini = findViewById(R.id.frameMini);
        frameLayoutPlayerMini.setVisibility(View.GONE);
    }

}