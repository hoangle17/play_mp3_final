package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {
    FloatingActionButton btnBackToLogin;
    EditText editTextUsername, editTextPass, editTextName;
    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setViews();
        clickEventUser();
    }

    private void clickEventUser() {
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUsername.getText().toString().matches("") || editTextPass.getText().toString().matches("")) {
                    Toast.makeText(SignUpActivity.this, "Username or password cannot be blank!", Toast.LENGTH_SHORT).show();
                } else {
                    DataService dataService = APIService.getService();
                    Call<String> call = dataService.createUser(String.valueOf(editTextUsername.getText().toString()),
                            String.valueOf(editTextPass.getText().toString()),
                            String.valueOf(editTextName.getText().toString()));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("available")) {
                                Log.d("BBB", "fail");
                                Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_LONG).show();
                                Log.d("BBB", "Success");
                                editTextName.setText("");
                                editTextPass.setText("");
                                editTextName.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void setViews() {
        btnBackToLogin = findViewById(R.id.btnBackLogin);
        editTextUsername = findViewById(R.id.username_signup);
        editTextPass = findViewById(R.id.password_signup);
        editTextName = findViewById(R.id.name_signup);
        textViewSignUp = findViewById(R.id.signup);
    }

}