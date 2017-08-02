package com.example.alex.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

public class Harta extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    //To determinate your location
    private GpsTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;

    private EditText locationEditText;
    private Button searchButton;
    private Button typeMapButton;
    private Button logOut;
    private TextView emailText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harta);

        logOut = (Button)findViewById(R.id.logOutButton);
        emailText = (TextView)findViewById(R.id.emailText);

        searchButton = (Button)findViewById(R.id.searchButton);
        typeMapButton = (Button)findViewById(R.id.typeMapButton);

        locationEditText = (EditText)findViewById(R.id.address);



        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, login.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        emailText.setText("Welcome " + user.getEmail());
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

        // Your courent coordonates
        LatLng loc = new LatLng(latitude,longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(loc).title("You are here!"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
    }

    public void logOut(View view) {
        if(view == logOut){
            firebaseAuth.signOut();
            startActivity(new Intent(this, login.class));
        }
    }

    public void onSearch(View view) throws IOException {
        String location = locationEditText.getText().toString();
        List<Address> addressList = null;

        if(location != null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e){
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            //Location coordonates
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

        }
    }

    public void changeType(View view) {
        if(mGoogleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else{
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void onZoom(View view) {
        if(view.getId() == R.id.zoomInButton) {
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.zoomOutButton){
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
}
