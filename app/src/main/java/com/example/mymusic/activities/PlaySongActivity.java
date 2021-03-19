package com.example.mymusic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.adapters.HotSongAdapter;
import com.example.mymusic.adapters.ListSongsAdapter;
import com.example.mymusic.adapters.ViewPagerPlayListSong;
import com.example.mymusic.fragments.NowPlayingFragmentBottom;
import com.example.mymusic.fragments.PlayListSongsFragment;
import com.example.mymusic.fragments.ShowInformationSongFragment;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymusic.fragments.NowPlayingFragmentBottom.imageButtonPlayMini;
import static com.example.mymusic.fragments.NowPlayingFragmentBottom.imageViewLikeMini;
import static com.example.mymusic.fragments.NowPlayingFragmentBottom.imageViewSongMini;
import static com.example.mymusic.fragments.NowPlayingFragmentBottom.textViewNameSongMini;
import static com.example.mymusic.fragments.NowPlayingFragmentBottom.textViewSingerMini;

public class PlaySongActivity extends AppCompatActivity {
    CircleIndicator circleIndicatorPlay;
    Toolbar toolbarPlaySong;
    TextView textViewCurrentTime, textViewTotalTime;
    public static ImageButton imageButtonPlay;
    ImageButton imageButtonRepeat, imageButtonNext, imageButtonPrevious, imageButtonShuffle, imageButtonMore;
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
    public static MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;
    Intent intent;

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSongsFromIntent();
        setViews();
        eventClickPlay();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("INTENT_NAME"));
    }


    @Override
    public void onBackPressed() {
        if (songArrayList.size() > 0) {
            MainActivity.getFrameLayoutPlayerMini().setVisibility(View.VISIBLE);

            Song song = songArrayList.get(position);
            setViewMinimize(song);
        }
        backToPreviousActivity();
    }

    private void setViewMinimize(Song song) {
        textViewSingerMini.setText(song.getSinger());
        textViewNameSongMini.setText(song.getNameSong());
        imageViewLikeMini.setImageResource(R.drawable.heart);
        Picasso.with(NowPlayingFragmentBottom.getContextMinimize().getContext()).load(song.getImageSong()).into(imageViewSongMini);
        imageViewLikeMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewLikeMini.setImageResource(R.drawable.heartfull);
                DataService dataService = APIService.getService();
                Call<String> call = dataService.updateLiked("1", song.getIdSong());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if (result.equals("success")) {
                            Toast.makeText(NowPlayingFragmentBottom.getContextMinimize().getContext(), "Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NowPlayingFragmentBottom.getContextMinimize().getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void backToPreviousActivity() {
        if (ListSongsAdapter.isClickItem || SongsActivity.isClickFB) {
            Intent intentToListSong = new Intent(this, SongsActivity.class);
            intentToListSong.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intentToListSong);
        } else {
            Intent intentToMain = new Intent(this, MainActivity.class);
            intentToMain.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intentToMain);
        }
    }

    private void moveSongToMinimized(Song song) {
        Intent intent = new Intent("MOVE_MINI").putExtra("moveToMini", song);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    //song from list play
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Song receiver = (Song) intent.getParcelableExtra("moveSong");
            playFromPlayList(receiver);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
    }

    private void getSongsFromIntent() {
        intent = getIntent();
        songArrayList.clear();
        if (intent != null) {
            if (mediaPlayer != null) {
                PlaySongActivity.getMediaPlayer().stop();
            }
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

    private void playSong(Song song) {
        new PlayMp3().execute(song.getLinkSong());
        showInformationSongFragment.setViewsPlaySong(song.getImageSong(), song.getNameSong(), song.getSinger());
        getSupportActionBar().setTitle(song.getNameSong());
    }

    private void playFromPlayList(Song receivedSong) {
        imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
        position = songArrayList.indexOf(receivedSong);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        playSong(songArrayList.get(position));
    }

    private void eventClickPlay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlayListSong.getItem(0) != null) {
                    if (songArrayList.size() > 0) {
                        showInformationSongFragment.setViewsPlaySong(
                                songArrayList.get(0).getImageSong(),
                                songArrayList.get(0).getNameSong(),
                                songArrayList.get(0).getSinger());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imageButtonPlay.setImageResource(R.drawable.ic_play_arrow);
                    imageButtonPlayMini.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    mediaPlayer.start();
                    imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });
        imageButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkRandom == true) {
                        checkRandom = false;
                        imageButtonRepeat.setImageResource(R.drawable.ic_repeat_one);
                        imageButtonShuffle.setImageResource(R.drawable.ic_shuffle);
                    }
                    imageButtonRepeat.setImageResource(R.drawable.ic_repeat_one);
                    repeat = true;
                } else {
                    imageButtonRepeat.setImageResource(R.drawable.ic_repeat);
                    repeat = false;
                }
            }
        });
        imageButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        imageButtonShuffle.setImageResource(R.drawable.ic_iconshuffled);
                        imageButtonRepeat.setImageResource(R.drawable.ic_repeat);
                    }
                    imageButtonShuffle.setImageResource(R.drawable.ic_iconshuffled);
                    checkRandom = true;
                } else {
                    imageButtonShuffle.setImageResource(R.drawable.ic_shuffle);
                    checkRandom = false;
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (songArrayList.size())) {
                        imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        playSong(songArrayList.get(position));
                        updateTime();
                    }
                }
                imageButtonPrevious.setClickable(false);
                imageButtonNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonPrevious.setClickable(true);
                        imageButtonNext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (songArrayList.size())) {
                        imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
                        position--;
                        if (position < 0) {
                            position = songArrayList.size() - 1;
                        }
                        if (repeat == true) {
                            position += 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        playSong(songArrayList.get(position));
                        updateTime();
                    }
                }
                imageButtonPrevious.setClickable(false);
                imageButtonPlay.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonPrevious.setClickable(true);
                        imageButtonPlay.setClickable(true);
                    }
                }, 5000);
            }
        });
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
                                Log.d("BBB", "clicked");
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playListSongsFragment = new PlayListSongsFragment();
        showInformationSongFragment = new ShowInformationSongFragment();
        viewPagerPlayListSong = new ViewPagerPlayListSong(getSupportFragmentManager());
        viewPagerPlayListSong.addFragment(showInformationSongFragment);
        viewPagerPlayListSong.addFragment(playListSongsFragment);
        viewPagerPlay.setAdapter(viewPagerPlayListSong);
        toolbarPlaySong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    MainActivity.getFrameLayoutPlayerMini().setVisibility(View.VISIBLE);

                    Song song = songArrayList.get(position);
                    setViewMinimize(song);
                }
                backToPreviousActivity();
            }
        });
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
        showInformationSongFragment = (ShowInformationSongFragment) viewPagerPlayListSong.getItem(0);
        if (songArrayList.size() > 0) {
            getSupportActionBar().setTitle(songArrayList.get(0).getNameSong());
            new PlayMp3().execute(songArrayList.get(0).getLinkSong());
            imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
            imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        }
    }


    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            timeSong();
            updateTime();
        }
    }

    private void updateTime() {
        final Handler handlerSetTimePlaying = new Handler();
        handlerSetTimePlaying.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    textViewCurrentTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handlerSetTimePlaying.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);
        final Handler handlerNextSong = new Handler();
        handlerNextSong.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < (songArrayList.size())) {
                        imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        playSong(songArrayList.get(position));
                        updateTime();
                    }
                    imageButtonPrevious.setClickable(false);
                    imageButtonNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageButtonPrevious.setClickable(true);
                            imageButtonNext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                    handler1.removeCallbacks(this, 1000);
                } else {
                    handlerNextSong.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void timeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textViewTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
}