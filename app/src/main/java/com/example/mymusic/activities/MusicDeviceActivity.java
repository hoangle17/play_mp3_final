package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mymusic.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import static com.example.mymusic.fragments.NowPlayingFragmentBottom.imageButtonPlayMini;

public class MusicDeviceActivity extends AppCompatActivity {
    ListView listView;
    Button back;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_device);
        setViews();
    }

    private void setViews() {
        listView = findViewById(R.id.lvDevice);
        back = findViewById(R.id.backDevice);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                displayItems();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                // check for permanent denial of permission
                if (response.isPermanentlyDenied()) {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void displayItems() {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            //toast(mySongs.get(i).getName().toString());
            items[i] = i + 1 + " . " + mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adp);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (PlaySongActivity.getMediaPlayer() != null) {
                    PlaySongActivity.getMediaPlayer().pause();
                    imageButtonPlayMini.setImageResource(R.drawable.ic_play_arrow);
                }
                String songName = listView.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(), PlayFromDeviceActivity.class)
                        .putExtra("pos", position).putExtra("songs", mySongs).putExtra("songname", songName));
            }
        });
    }

    private ArrayList<File> findSong(File externalStorageDirectory) {
        ArrayList<File> at = new ArrayList<File>();
        File[] files = externalStorageDirectory.listFiles();
        if (files != null) {
            for (File singleFile : files) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    at.addAll(findSong(singleFile));
                } else {
                    if (singleFile.getName().endsWith(".mp3") ||
                            singleFile.getName().endsWith(".wav")) {
                        at.add(singleFile);
                    }
                }
            }
        }
        return at;
    }
}