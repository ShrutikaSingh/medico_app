package com.saloni.doctoroncall;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    public static Double lat = 18.518149;
    public static Double lng = 73.815230;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    public void needADoctor(View view) {
        Intent i = new Intent(this, NeedADoctor.class);
        startActivity(i);
    }

    public void iamADoctor(View view) {
        Intent i = new Intent(this, IamADoctor.class);
        startActivity(i);
    }


    public void viewPrescription(View view) {
        Intent i = new Intent(this, FindPrescription.class);
        startActivity(i);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        Log.e("Lat/Lng", lat + "/" + lng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
