package ntk.ambrose.aroundtheworld;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import ntk.ambrose.aroundtheworld.Models.WorldMap;

/**
 * Main menu
 */
public class MainActivity extends AppCompatActivity {

    ImageButton btStart;
    ImageButton btSetting;

    RelativeLayout relativeLayout;

    float[] hsv;
    int runColor;
    int hue = 0;


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

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.setDuration(20000);

        hsv = new float[3]; // Transition color
        hsv[1] = 1;
        hsv[2] = 1;

        anim.addUpdateListener(animation -> {

            hsv[0] = 360 * animation.getAnimatedFraction();

            runColor = Color.HSVToColor(hsv);
            relativeLayout.setBackgroundColor(runColor);
        });

        anim.setRepeatCount(Animation.INFINITE);

        anim.start();



    }
}
