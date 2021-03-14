package com.example.mymusic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.adapters.ViewPagerPlayListSong;
import com.example.mymusic.fragments.PlayListSongsFragment;
import com.example.mymusic.fragments.ShowInformationSongFragment;
import com.example.mymusic.models.Song;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class PlaySongActivity extends AppCompatActivity {
    CircleIndicator circleIndicatorPlay;
    Toolbar toolbarPlaySong;
    TextView textViewCurrentTime, textViewTotalTime;
    ImageButton imageButtonPlay, imageButtonRepeat, imageButtonNext, imageButtonPrevious, imageButtonShuffle, imageButtonMore;
    ViewPager viewPagerPlay;
    SeekBar seekBar;
    MenuBuilder menuBuilder;
    public static ArrayList<Song> songArrayList = new ArrayList<>();
    public static ViewPagerPlayListSong viewPagerPlayListSong;
    ShowInformationSongFragment showInformationSongFragment;
    PlayListSongsFragment playListSongsFragment;
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        setViews();
        getSongsFromIntent();

    }


    private void getSongsFromIntent() {
        Intent intent = getIntent();
        songArrayList.clear();
        if (intent != null) {
            if (intent.hasExtra("song")) {
                Song song = intent.getParcelableExtra("song");
                songArrayList.add(song);
            }
            if (intent.hasExtra("songs")) {
                ArrayList<Song> songListMoved = intent.getParcelableArrayListExtra("songs");
                songArrayList = songListMoved;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void setViews() {
        imageButtonMore = findViewById(R.id.btnMorePlaySong);
        menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.popup_menu, menuBuilder);//this will show our menu list we add
        imageButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPopupHelper optionMenu = new MenuPopupHelper(PlaySongActivity.this,
                        menuBuilder, v);
                optionMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_download:
                                Toast.makeText(PlaySongActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.popup_share:
                                Toast.makeText(PlaySongActivity.this, "Share with Facebook", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionMenu.show();
            }
        });
        circleIndicatorPlay = findViewById(R.id.indicatorDefaultViewPager);
        toolbarPlaySong = findViewById(R.id.tlbPlaySong);
        textViewCurrentTime = findViewById(R.id.txtCurrentTime);
        textViewTotalTime = findViewById(R.id.txtTotalTime);
        imageButtonPlay = findViewById(R.id.btnPlay);
        imageButtonNext = findViewById(R.id.btnNext);
        imageButtonRepeat = findViewById(R.id.btnRepeat);
        imageButtonPrevious = findViewById(R.id.btnPrevious);
        imageButtonShuffle = findViewById(R.id.btnShuffle);
        seekBar = findViewById(R.id.playerSeekBar);
        viewPagerPlay = findViewById(R.id.vpPlaySong);
        setSupportActionBar(toolbarPlaySong);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlaySong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        playListSongsFragment = new PlayListSongsFragment();
        showInformationSongFragment = new ShowInformationSongFragment();
        viewPagerPlayListSong = new ViewPagerPlayListSong(getSupportFragmentManager());
        viewPagerPlayListSong.addFragment(showInformationSongFragment);
        viewPagerPlayListSong.addFragment(playListSongsFragment);
        viewPagerPlay.setAdapter(viewPagerPlayListSong);
        circleIndicatorPlay.setViewPager(viewPagerPlay);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPagerPlay.getCurrentItem();
                currentItem++;
                if (currentItem >= viewPagerPlay.getAdapter().getCount()) {
                    currentItem = 0;
                }
                viewPagerPlay.setCurrentItem(currentItem, true);
            }
        };
    }

}