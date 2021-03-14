package com.example.mymusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusic.R;
import com.example.mymusic.models.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    class ViewHolder {
        TextView textViewNamePlaylist;
        ImageView imageViewBg, imageViewIcon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.line_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewNamePlaylist = convertView.findViewById(R.id.txtNamePlaylist);
            viewHolder.imageViewBg = convertView.findViewById(R.id.imgBackgroundPlaylist);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.imgIconPlaylist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getBackground()).into(viewHolder.imageViewBg);
        Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.imageViewIcon);
        viewHolder.textViewNamePlaylist.setText(playlist.getName());
        return convertView;
    }

}
