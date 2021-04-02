package com.example.mymusic.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.models.Song;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mymusic.fragments.NowPlayingFragmentBottom.imageButtonPlayMini;

public class PlayFromDeviceActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    ImageView imageViewDefault;
    TextView textViewTotalTime, textViewCurrentTime, textViewSongName;
    SeekBar seekBar;
    Thread updateSeekBar;
    ImageButton imageButtonNext, imageButtonPrevious, imageButtonPlay;
    boolean next = false;
    String name;
    ObjectAnimator objectAnimator;
    ArrayList<File> mySongs;
    static MediaPlayer mp;//assigning memory loc once or else multiple songs will play at once
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_from_device);
        setViews();
        play();
        onClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void play() {
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
                while (currentPosition < totalDuration) {
                    try {
                        sleep(1000);
                        currentPosition = mp.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };

        if (mp != null) {
            mp.stop();
            mp.release();
        }
        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songs");
        name = mySongs.get(position).getName().toString();
        String SongName = i.getStringExtra("songname");
        textViewSongName.setText(SongName);
        textViewSongName.setSelected(true);

        position = b.getInt("pos", 0);
        Uri u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        seekBar.setMax(mp.getDuration());
        updateSeekBar.start();
//        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }

    private void onClick() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(mp.getDuration());
                if (mp.isPlaying()) {
                    imageButtonPlay.setImageResource(R.drawable.ic_play_arrow);
                    mp.pause();
                    circleImageView = findViewById(R.id.album_art);
                    circleImageView.setVisibility(View.INVISIBLE);
                    imageViewDefault.setVisibility(View.VISIBLE);

                } else {
                    imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    circleImageView = findViewById(R.id.album_art);
                    circleImageView.setVisibility(View.VISIBLE);
                    imageViewDefault.setVisibility(View.INVISIBLE);
                    mp.start();
                }
            }
        });

        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                position = ((position + 1) % mySongs.size());
                if (position > (mySongs.size() - 1)) {
                    position = 0;
                }
                Uri u = Uri.parse(mySongs.get(position).toString());
                // songNameText.setText(getSongName);
                mp = MediaPlayer.create(getApplicationContext(), u);
                name = mySongs.get(position).getName().toString();
                textViewSongName.setText(name);
                try {
                    mp.start();
                } catch (Exception e) {
                }
                timeSong();
                updateTime();
            }
        });
        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //songNameText.setText(getSongName);
                mp.stop();
                mp.release();
                position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                name = mySongs.get(position).getName().toString();
                textViewSongName.setText(name);
                mp.start();
                timeSong();
                updateTime();
            }
        });
        timeSong();
        updateTime();
    }

    private void timeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textViewTotalTime.setText(simpleDateFormat.format(mp.getDuration()));
        seekBar.setMax(mp.getDuration());
    }

    private void updateTime() {
        final Handler handlerSetTimePlaying = new Handler();
        handlerSetTimePlaying.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mp != null) {
                    seekBar.setProgress(mp.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    textViewCurrentTime.setText(simpleDateFormat.format(mp.getCurrentPosition()));
                    handlerSetTimePlaying.postDelayed(this, 300);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                if (next == true) {
                    if (position < (mySongs.size())) {
                        imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        position++;
                        if (position > (mySongs.size() - 1)) {
                            position = 0;
                        }
                    }
                    name = mySongs.get(position).getName().toString();
                    textViewSongName.setText(name);
                    imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(), u);
                    mp.start();
                    timeSong();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
        mySongs.clear();
        finish();
    }

    private void setViews() {
        circleImageView = findViewById(R.id.album_art);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        imageViewDefault = findViewById(R.id.albumArt);

        textViewSongName = findViewById(R.id.txtSongLabel);
        textViewTotalTime = findViewById(R.id.txtTotalTimeDv);
        textViewCurrentTime = findViewById(R.id.txtCurrentTimeDv);
        seekBar = findViewById(R.id.playerSeekBarDv);
        imageButtonNext = findViewById(R.id.btnNextDv);
        imageButtonPlay = findViewById(R.id.btnPlayDv);
        imageButtonPrevious = findViewById(R.id.btnPreviousDv);
    }
}