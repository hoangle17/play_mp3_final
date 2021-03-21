package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.PlaySongActivity;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotSongAdapter extends RecyclerView.Adapter<HotSongAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> songArrayList;
    public static boolean isClickedHotSong = false;

    public HotSongAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_hot_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.textViewNameHotSong.setText(song.getNameSong());
        holder.textViewNameSinger.setText(song.getSinger());
        Picasso.with(context).load(song.getImageSong()).into(holder.imageViewSong);
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameSinger, textViewNameHotSong;
        ImageView imageViewSong, imageViewLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameHotSong = itemView.findViewById(R.id.txtNameHotSong);
            textViewNameSinger = itemView.findViewById(R.id.txtSingerHotSong);
            imageViewSong = itemView.findViewById(R.id.imgViewHotSong);
            imageViewLike = itemView.findViewById((R.id.imgLike));
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
                                imageViewLike.setEnabled(false);
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
                    isClickedHotSong = true;

                    Intent intent = new Intent(context, PlaySongActivity.class);
                    intent.putExtra("song", songArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
