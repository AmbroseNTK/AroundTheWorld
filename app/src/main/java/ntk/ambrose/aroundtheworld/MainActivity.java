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
import android.widget.Button;

import ntk.ambrose.aroundtheworld.Models.QuestionBundle;
import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class MainActivity extends AppCompatActivity {

    Button btFlagMode;
    Button btCountryMode;
    Button btDiscovery;
    Button btMixMode;
    Button btSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btFlagMode=findViewById(R.id.btFlagMode);
        btCountryMode=findViewById(R.id.btNameMode);
        btMixMode = findViewById(R.id.btMixMode);
        btDiscovery=findViewById(R.id.btDiscoveryMode);
        btSetting = findViewById(R.id.btSetting);

        btFlagMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionBundle.getInstance().setLevel(QuestionBundle.LEVEL_EASY);
                QuestionBundle.getInstance().generateQuestionList(3,10);
                Log.d("APP","Created question bundle");
                Intent intent = new Intent(MainActivity.this,FlagModeActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
            }
        });

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OptionActivity.class));
            }
        });

    }
}
