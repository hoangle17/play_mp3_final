package com.example.mymusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.models.Comment;

import java.util.ArrayList;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ViewHolder> {
    Context context;
    ArrayList<Comment> commentArrayList;

    public ListCommentAdapter(Context context, ArrayList<Comment> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_comment, parent, false);
        return new ListCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentArrayList.get(position);
        holder.textViewUser.setText(comment.getNameuser());
        holder.textViewContent.setText(comment.getContent());
        holder.textViewTime.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUser, textViewTime, textViewContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.txtContentCmt);
            textViewTime = itemView.findViewById(R.id.txtTimeCmt);
            textViewUser = itemView.findViewById(R.id.txtUserCmt);
        }
    }

    public void addComment(Comment comment) {
        commentArrayList.add(comment);
        //notifyDataSetChanged();
        notifyItemInserted(commentArrayList.size() - 1);
    }
}
