package com.example.mymusic.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.models.User;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    FloatingActionButton buttonLogin;
    EditText editTextUsername, editTextPass;
    TextView textViewSignIn;
    boolean logged = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_login);
        setViews();
        setClickEvent();
    }

    private void setClickEvent() {
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataService dataService = APIService.getService();
                Call<User> call = dataService.login(
                        String.valueOf(editTextUsername.getText().toString()),
                        String.valueOf(editTextPass.getText().toString()));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if (user != null) {
                            Log.d("BBB", "Successfully");
                            Toast.makeText(LoginActivity.this, "Logged, Successfully!", Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Username or password incorrect!", Toast.LENGTH_SHORT).show();
                        editTextPass.setText("");
                    }
                });
            }
        });
    }

    private void setViews() {
        editTextUsername = findViewById(R.id.txtUsername);
        editTextPass = findViewById(R.id.txtPassword);
        textViewSignIn = findViewById(R.id.txtSignIn);
        buttonLogin = findViewById(R.id.btnSignIn);
    }

    public void OpenSignUpPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
}