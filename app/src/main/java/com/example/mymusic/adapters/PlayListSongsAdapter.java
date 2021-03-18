package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.PlaySongActivity;
import com.example.mymusic.fragments.PlayListSongsFragment;
import com.example.mymusic.fragments.ShowInformationSongFragment;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.CommunicationInterface;
import com.example.mymusic.services.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListSongsAdapter extends RecyclerView.Adapter<PlayListSongsAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> songArrayList;

    public PlayListSongsAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_play_list_songs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.textViewSingerLine.setText(song.getSinger());
        holder.textViewNameSongPlayLine.setText(song.getNameSong());
        holder.textViewIndexLine.setText(position + 1 + ".");
        Picasso.with(context).load(song.getImageSong()).into(holder.imageViewPlaySongsLine);
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLike, imageViewPlaySongsLine;
        TextView textViewNameSongPlayLine, textViewIndexLine, textViewSingerLine;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSingerLine = itemView.findViewById(R.id.txtNameSingerPlaySongs);
            textViewIndexLine = itemView.findViewById(R.id.txtIndexPlaySongs);
            textViewNameSongPlayLine = itemView.findViewById(R.id.txtNameSongPlayLine);
            imageViewLike = itemView.findViewById(R.id.imgLikePlayLine);
            imageViewPlaySongsLine = itemView.findViewById(R.id.imgPlaySongsLine);
            imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewLike.setImageResource(R.drawable.heartfull);
                    DataService dataService = APIService.getService();
                    Call<String> call = dataService.updateLiked("1", songArrayList.get(getPosition()).getIdSong());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("success")) {
                                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("INTENT_NAME").putExtra("moveSong", songArrayList.get(getPosition()));
                    Log.d("BBB", songArrayList.get(getPosition()).getNameSong());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }
    }

}
