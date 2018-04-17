package ntk.ambrose.aroundtheworld;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);

        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    addressList.get(0).getCountryCode();
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
        ArrayList<String> countries = new ArrayList<>();
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

        btDetectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                }catch(SecurityException ex){

                }


            }
        });

    }

}
