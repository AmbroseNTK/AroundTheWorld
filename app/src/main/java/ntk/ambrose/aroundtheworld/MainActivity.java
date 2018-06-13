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
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import ntk.ambrose.aroundtheworld.Models.WorldMap;

/**
 * Main menu
 */
public class MainActivity extends AppCompatActivity {

    ImageButton btStart;
    ImageButton btSetting;

    RelativeLayout relativeLayout;

    GoogleApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);
        SoundManager.getInstance().init(this);

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

        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, connectionResult -> {
                    Log.e("AroundTheWorld", "Could not connect to Play games services");

                }).build();
        apiClient.connect();
        Setting.getInstance().setApiClient(apiClient);

    }
}
