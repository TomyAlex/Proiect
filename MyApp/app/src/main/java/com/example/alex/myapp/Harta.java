package com.example.alex.myapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Harta extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    //To determinate your location
    private GpsTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harta);

        //GPS
        gpsTracker = new GpsTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();
        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();

        initMap();
    }
    private void initMap(){
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng loc = new LatLng(latitude,longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(loc).title("You are here!"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
