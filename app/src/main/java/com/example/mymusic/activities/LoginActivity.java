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
import com.example.mymusic.fragments.PersonalFragment;
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
    private static User user;

    public static User getUser() {
        return user;
    }

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
                        user = response.body();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);

                            PersonalFragment.textViewNameUser.setText(user.getName());
                            PersonalFragment.textViewUsername.setText(user.getUsername());
                            PersonalFragment.imageButtonLogin.setImageResource(R.drawable.ic_baseline_logout_24);
                            PersonalFragment.textViewLogin.setText("Logout");
                            //Lout out
                            PersonalFragment.imageButtonLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(LoginActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                                }
                            });
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