package com.example.mymusic.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.LoginActivity;
import com.example.mymusic.activities.PlaySongActivity;
import com.example.mymusic.adapters.ListCommentAdapter;
import com.example.mymusic.models.Comment;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDiaLog extends AppCompatDialogFragment {
    EditText editTextCmt;
    RecyclerView recyclerViewCmt;
    ListCommentAdapter listCommentAdapter;
    ImageButton imageButtonClose, imageButtonSendCmt;
    Song song;
    Dialog alertDialog;
    public static boolean isClickSendComment = false;

    public CommentDiaLog(Song song) {
        this.song = song;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.layout_comment);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        window.setGravity(Gravity.TOP);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.y = 185; //margin top
        window.setAttributes(layoutParams);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.97);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.645);
//        window.setGravity(Gravity.TOP);
        window.setLayout(width, height);
        alertDialog.show();

        editTextCmt = alertDialog.findViewById(R.id.txtInputCommentSend);
        recyclerViewCmt = alertDialog.findViewById(R.id.rcvShowComment);
        imageButtonClose = alertDialog.findViewById(R.id.btnClose);
        imageButtonSendCmt = alertDialog.findViewById(R.id.btnSendCmt);
        getData();
        clickEvent();
        return alertDialog;
    }

    private void clickEvent() {
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        imageButtonSendCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String time = formatter.format(date);
                isClickSendComment = true;
                if (LoginActivity.getUser() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    if (editTextCmt.getText().toString().trim().equals("")) {

                    } else {
                        DataService dataService = APIService.getService();
                        Call<String> call = dataService.sendComment(
                                LoginActivity.getUser().getIdUser(),
                                song.getIdSong(),
                                LoginActivity.getUser().getName(),
                                String.valueOf(editTextCmt.getText().toString()),
                                time);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result = response.body();
                                if (result.equals("fail")) {
                                    Log.d("BBB", result);
                                    Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("BBB", result);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        listCommentAdapter.addComment(new Comment(
                                song.getIdSong(),
                                LoginActivity.getUser().getIdUser(),
                                LoginActivity.getUser().getName(),
                                String.valueOf(editTextCmt.getText().toString()),
                                time));
                        editTextCmt.setText(null);
                        isClickSendComment = false;
                    }
                }
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Comment>> call = dataService.getAllComment(song.getIdSong());
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                ArrayList<Comment> arrayList = (ArrayList<Comment>) response.body();
                listCommentAdapter = new ListCommentAdapter(getActivity(), arrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerViewCmt.setLayoutManager(linearLayoutManager);
                recyclerViewCmt.setAdapter(listCommentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

}
