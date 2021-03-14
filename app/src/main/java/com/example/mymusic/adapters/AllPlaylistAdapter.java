package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.models.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllPlaylistAdapter extends RecyclerView.Adapter<AllPlaylistAdapter.ViewHolder> {
    Context context;
    ArrayList<Playlist> playlistArrayList;

    public AllPlaylistAdapter(Context context, ArrayList<Playlist> playlistArrayList) {
        this.context = context;
        this.playlistArrayList = playlistArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_all_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlistArrayList.get(position);
        holder.textViewNamePlaylistLine.setText(playlist.getName());
        Picasso.with(context).load(playlist.getIcon()).into(holder.imageViewPlaylistLine);
    }

    @Override
    public int getItemCount() {
        return playlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamePlaylistLine;
        ImageView imageViewPlaylistLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNamePlaylistLine = itemView.findViewById(R.id.txtPlaylistLine);
            imageViewPlaylistLine = itemView.findViewById(R.id.imgAllPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SongsActivity.class);
                    intent.putExtra("itemplaylist", playlistArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
