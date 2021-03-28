package com.example.mymusic.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static int RC_SIGN_IN = 100;
    FloatingActionButton buttonLogin;
    EditText editTextUsername, editTextPass;
    TextView textViewSignIn;
    String user_lastname, user_firstname, user_email, user_id;
    private static User user;

    public static User getUser() {
        return user;
    }

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    SignInButton signInButton;
    public static GoogleSignInClient mGoogleSignInClient;
    String personName;
    String personEmail;

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

        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                try {
                                    user_lastname = me.optString("last_name");
                                    user_firstname = me.optString("first_name");
                                    user_email = response.getJSONObject().optString("email");
                                    user_id = me.getString("id");
                                    PersonalFragment.textViewNameUser.setText(user_firstname + "\t" + user_lastname);
                                    PersonalFragment.textViewUsername.setText(user_email);
                                    Picasso.with(LoginActivity.this).load("http://graph.facebook.com/" + user_id + "/picture?type=large").into(PersonalFragment.imageViewUser);
                                    PersonalFragment.isLogged = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
// Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                PersonalFragment.textViewNameUser.setText(personName);
                PersonalFragment.textViewUsername.setText(personEmail);
                Picasso.with(LoginActivity.this).load(personPhoto).into(PersonalFragment.imageViewUser);
                PersonalFragment.imageButtonLogin.setImageResource(R.drawable.ic_baseline_logout_24);
                PersonalFragment.textViewLogin.setText("Logout");
                PersonalFragment.isLogged = true;

                DataService dataService = APIService.getService();
                Call<User> call = dataService.returnUserGG(personEmail, "", personName);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            PersonalFragment.isLogged = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("BBB", t.toString());
                    }
                });

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("SSS", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setClickEvent() {
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUsername.getText().toString().matches("") || editTextPass.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Username or password cannot be blank!", Toast.LENGTH_SHORT).show();
                } else {
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
                                setInforUserLogin(user);
                                PersonalFragment.isLogged = true;
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Username or password incorrect!", Toast.LENGTH_SHORT).show();
                            editTextPass.setText("");
                        }
                    });
                }
            }
        });
    }


    private void setInforUserLogin(User user) {
        PersonalFragment.textViewNameUser.setText(user.getName());
        PersonalFragment.textViewUsername.setText(user.getUsername());
        PersonalFragment.imageButtonLogin.setImageResource(R.drawable.ic_baseline_logout_24);
        PersonalFragment.textViewLogin.setText("Logout");
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