package ntk.ambrose.aroundtheworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.Task;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;

import ntk.ambrose.aroundtheworld.Models.WorldMap;

/**
 * Main menu
 */
public class MainActivity extends AppCompatActivity {

    ImageButton btStart;
    ImageButton btSetting;
    ImageView background;

    Animation backgroundAnimation;

    RelativeLayout relativeLayout;

    SignInButton btSignIn;
    LoginButton loginButton;

    TextView tvUsername;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);



        backgroundAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate_background);
        backgroundAnimation.setRepeatCount(Animation.INFINITE);
        backgroundAnimation.setRepeatMode(Animation.RESTART);
        backgroundAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                background.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        background = findViewById(R.id.imgBackground);
        background.startAnimation(backgroundAnimation);


        relativeLayout = findViewById(R.id.relativeLayout);
        SoundManager.getInstance().init(this);
        SoundManager.getInstance().stopAll();
        SoundManager.getInstance().Play(SoundManager.Playlist.BG_MAIN,true);
        //Set permission in runtime
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        }

        WorldMap.getInstance().createWorldMap(this);

        btStart =findViewById(R.id.btStart);


        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
                startActivity(new Intent(MainActivity.this,ModeActivity.class));
                finish();
            }
        });

        btSetting =findViewById(R.id.btSetting);

        btSetting.setOnClickListener((view)->{
            SoundManager.getInstance().Play(SoundManager.Playlist.SOUND_BUTTON,false);
            startActivity(new Intent(MainActivity.this,OptionActivity.class));
        });

        btSignIn = findViewById(R.id.btSignIn);
        btSignIn.setSize(SignInButton.SIZE_WIDE);
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        tvUsername = findViewById(R.id.tvUserName);
        if(Setting.getInstance().getGoogleSignInAccount()==null) {
            signIn();
        }
        else{
            tvUsername.setText(Setting.getInstance().getGoogleSignInAccount().getDisplayName());
        }



    }

    private int RC_SIGN_IN;
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
            Setting.getInstance().setGoogleSignInAccount(completedTask.getResult(ApiException.class));
            Log.i("AroundTheWorld","Signed in successfully");
            Setting.getInstance().setLeaderboardsClient(Games.getLeaderboardsClient(getBaseContext(), Setting.getInstance().getGoogleSignInAccount()));
            TastyToast.makeText(getBaseContext(),"Welcome! "+Setting.getInstance().getGoogleSignInAccount().getEmail(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
            tvUsername.setText("Hi, "+Setting.getInstance().getGoogleSignInAccount().getDisplayName());

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("AroundTheWorld", "signInResult:failed code=" + e.getStatusCode());

        }
        loginButton = findViewById(R.id.btLoginFacebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFacebook();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    CallbackManager callbackManager;
    private void loginFacebook() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        TastyToast.makeText(getBaseContext(), "Logged in Facebook", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        TastyToast.makeText(getBaseContext(), "Sorry! I cannot login Facebook, please try again...", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    }
                });

    }

}
