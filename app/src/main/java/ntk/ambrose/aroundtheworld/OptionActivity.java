package ntk.ambrose.aroundtheworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ntk.ambrose.aroundtheworld.Models.WorldMap;

public class OptionActivity extends AppCompatActivity{
    CheckBox checkMute;
    Button btDetectCountry;
    Spinner spinCountry;
    LocationManager locationManager;
    LocationListener locationListener;
    ArrayList<String> countries;
    String code;
    String countryName;

    RadioButton radioEasy;
    RadioButton radioMedium;
    RadioButton radioHard;

    Button btSaveChanges;

    Animation backgroundRotateAnim;

    ImageView imgSettingIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);

        radioEasy =findViewById(R.id.radioEasy);
        radioMedium = findViewById(R.id.radioMedium);
        radioHard = findViewById(R.id.radioHard);

        btSaveChanges = findViewById(R.id.btSaveChanges);



        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    code = addressList.get(0).getCountryCode().toLowerCase();
                    countryName=WorldMap.getInstance().codeToName(code);

                    Setting.getInstance().setCountry(code);
                    spinCountry.setSelection(countries.indexOf(countryName));
                    Point point = WorldMap.getInstance().codeToPos(countryName);
                    Setting.getInstance().setX(point.x);
                    Setting.getInstance().setY(point.y);
                    TastyToast.makeText(getBaseContext(),"You are in "+countryName,TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                    locationManager.removeUpdates(this);
                } catch (IOException exception) {

                }
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
            @Override
            public void onProviderEnabled(String s) {

            }
            @Override
            public void onProviderDisabled(String s) {

            }
        };
        checkMute=findViewById(R.id.checkMute);
        btDetectCountry=findViewById(R.id.btAutoDetectCountry);
        spinCountry = findViewById(R.id.spinCountry);
        countries = new ArrayList<>();
        for(int i = 0; i< WorldMap.getInstance().HEIGHT; i++){
            for(int j=0;j<WorldMap.getInstance().WIDTH;j++) {
                for (int k = 0; k < WorldMap.getInstance().getCell(i, j).getUnit().size(); k++) {
                    if (!countries.contains(WorldMap.getInstance().getCell(i, j).getUnit().get(k).getName())) {
                        countries.add(WorldMap.getInstance().getCell(i, j).getUnit().get(k).getName());
                    }
                }
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,countries.toArray());
        spinCountry.setAdapter(arrayAdapter);

        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Point point = WorldMap.getInstance().codeToPos(countries.get(i));
                Setting.getInstance().setX(point.x);
                Setting.getInstance().setY(point.y);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btDetectCountry.setOnClickListener(view -> {
                    try {
                        TastyToast.makeText(getBaseContext(), "Please wait!!!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                    } catch (SecurityException ex) {
                        TastyToast.makeText(getBaseContext(), "Sorry! We cannot find you", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                });

        checkMute.setOnClickListener((view)->{
            if(checkMute.isChecked()){
                SoundManager.getInstance().setMuteAll();
            }
            else{
                SoundManager.getInstance().setMute(false);
            }
        });

        radioEasy.setOnClickListener(view -> {
            if(radioEasy.isChecked()){
                Setting.getInstance().setLevel(4);
            }
        });
        radioMedium.setOnClickListener(view -> {
            if(radioEasy.isChecked()){
                Setting.getInstance().setLevel(8);
            }
        });
        radioHard.setOnClickListener(view -> {
            if(radioEasy.isChecked()){
                Setting.getInstance().setLevel(12);
            }
        });
        loadSetting();
        btSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting();
                TastyToast.makeText(getBaseContext(),"Saved changes !",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                finish();
            }
        });


        backgroundRotateAnim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate_background);
        backgroundRotateAnim.setRepeatCount(Animation.INFINITE);
        backgroundRotateAnim.setRepeatMode(Animation.RESTART);
        imgSettingIcon = findViewById(R.id.imgSettingIcon);
        backgroundRotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgSettingIcon.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgSettingIcon.startAnimation(backgroundRotateAnim);


    }
    public void loadSetting(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            Setting.getInstance().setLevel(sharedPreferences.getInt("setting_level",4));
            switch (Setting.getInstance().getLevel()){
                case 4:
                    radioEasy.setChecked(true);
                    break;
                case 8:
                    radioMedium.setChecked(true);
                    break;
                case 12:
                    radioHard.setChecked(true);
            }
            checkMute.setChecked(sharedPreferences.getBoolean("setting_isMute",false));
            int id = sharedPreferences.getInt("setting_pos",0);
            Point point = WorldMap.getInstance().codeToPos(countries.get(id));
            Setting.getInstance().setX(point.x);
            Setting.getInstance().setY(point.y);
            spinCountry.setSelection(id);
        }
    }
    public void saveSetting(){
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("setting_level",Setting.getInstance().getLevel());
        editor.putBoolean("setting_isMute",checkMute.isChecked());
        editor.putInt("setting_pos", (int)(spinCountry.getSelectedItemId()));
        editor.apply();
    }

}
