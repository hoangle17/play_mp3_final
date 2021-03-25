package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.adapters.HotSongAdapter;
import com.example.mymusic.adapters.ListSongsAdapter;
import com.example.mymusic.adapters.MainViewPagerAdapter;
import com.example.mymusic.adapters.SearchSongAdapter;
import com.example.mymusic.fragments.HomePageFragment;
import com.example.mymusic.fragments.PersonalFragment;
import com.example.mymusic.fragments.SearchFragment;
import com.example.mymusic.fragments.YoutubeFragment;
import com.example.mymusic.models.User;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static FrameLayout frameLayoutPlayerMini;
    TabLayout tabLayout;
    ViewPager viewPager;
    private static User user;

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
        Fragment personalFragment = new PersonalFragment();


        mainViewPagerAdapter.addFragment(homePageFragment, "Home");
        mainViewPagerAdapter.addFragment(searchFragment, "Search");
        mainViewPagerAdapter.addFragment(youtubeFragment, "MV");
        mainViewPagerAdapter.addFragment(personalFragment, "Personal");
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setOffscreenPageLimit(4); //keep state fragment not reload
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_ondemand_video_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_person_24);
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

    //retrieve user after login
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        user = (User) intent.getSerializableExtra("user");
        PersonalFragment.textViewNameUser.setText(user.getName());
        PersonalFragment.textViewUsername.setText(user.getUsername());
        PersonalFragment.imageButtonLogin.setImageResource(R.drawable.ic_baseline_logout_24);
        PersonalFragment.textViewLogin.setText("Logout");
        //Lout out
        PersonalFragment.imageButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static User getUser() {
        return user;
    }
}