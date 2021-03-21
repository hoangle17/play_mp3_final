package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mymusic.R;
import com.example.mymusic.adapters.HotSongAdapter;
import com.example.mymusic.adapters.ListSongsAdapter;
import com.example.mymusic.adapters.MainViewPagerAdapter;
import com.example.mymusic.adapters.SearchSongAdapter;
import com.example.mymusic.fragments.HomePageFragment;
import com.example.mymusic.fragments.SearchFragment;
import com.example.mymusic.fragments.YoutubeFragment;
import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

                HotSongAdapter.isClickedHotSong = true;
                SearchSongAdapter.isClickedItemSearch = true;
                SongsActivity.isClickFB = false;
                ListSongsAdapter.isClickItem = false;
            }
        });
    }


    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        Fragment homePageFragment = new HomePageFragment();
        Fragment searchFragment = new SearchFragment();
        Fragment youtubeFragment = new YoutubeFragment();

        mainViewPagerAdapter.addFragment(homePageFragment, "Home");
        mainViewPagerAdapter.addFragment(searchFragment, "Search");
        mainViewPagerAdapter.addFragment(youtubeFragment, "Youtube");
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setOffscreenPageLimit(3); //keep state fragment not reload
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_ondemand_video_24);
    }

    private void initView() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        frameLayoutPlayerMini = findViewById(R.id.frameMini);
        frameLayoutPlayerMini.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you sure you want to exit?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}