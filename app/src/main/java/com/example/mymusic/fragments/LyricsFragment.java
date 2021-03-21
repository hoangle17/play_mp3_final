package com.example.mymusic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.Callback;

import org.json.JSONException;
import org.json.JSONObject;

public class LyricsFragment extends Fragment {
    View view;
    TextView textViewLyrics;
    private static final String apiLyric = "https://api.lyrics.ovh/v1/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        textViewLyrics = view.findViewById(R.id.txtLyric);

        return view;
    }


    public void setLyricSong(Song song) {
        String url = getUrlLyric(song);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    textViewLyrics.setText("Lyric: \n\n" + jsonObject.getString("lyrics"));
                } catch (JSONException err) {
                    Log.d("Error", err.toString());
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewLyrics.setText("Sorry! Lyrics unavailable for song");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private String getUrlLyric(Song song) {
        return apiLyric + "/" + song.getSinger() + "/" + song.getNameSong();
    }
}