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

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder> {
    ArrayList<Song> songArrayList;
    Context context;
    public static boolean isClickedItemSearch = false;

    public SearchSongAdapter(ArrayList<Song> songArrayList, Context context) {
        this.songArrayList = songArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.line_song_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.textViewNameSong.setText(song.getNameSong());
        holder.textViewSinger.setText(song.getSinger());
        Picasso.with(context).load(song.getImageSong()).into(holder.imageViewSong);
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameSong, textViewSinger;
        ImageView imageViewSong, imageViewLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameSong = itemView.findViewById(R.id.txtNameSongResult);
            textViewSinger = itemView.findViewById(R.id.txtSingerSongResult);
            imageViewSong = itemView.findViewById(R.id.imgSongResult);
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
//                                imageViewLike.setImageResource(R.drawable.heart);
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
                    isClickedItemSearch = true;

                    Intent intent = new Intent(context, PlaySongActivity.class);
                    intent.putExtra("song", songArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
