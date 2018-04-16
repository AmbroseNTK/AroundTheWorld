package ntk.ambrose.aroundtheworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class MainActivity extends AppCompatActivity {

    Button btFlagMode;
    Button btCountryMode;
    Button btDiscovery;
    Button btMixMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WorldMap.getInstance().createWorldMap(this);
        btFlagMode=findViewById(R.id.btFlagMode);
        btCountryMode=findViewById(R.id.btNameMode);
        btMixMode = findViewById(R.id.btMixMode);
        btDiscovery=findViewById(R.id.btDiscoveryMode);

    }
}
