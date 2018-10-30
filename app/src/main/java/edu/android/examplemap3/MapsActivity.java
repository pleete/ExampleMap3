package edu.android.examplemap3;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    LocationCallback callback;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = new Intent(this, GpsService.class);
        startService(intent);
        callback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                LatLng latLng = new LatLng(lat,lon);

                updateGoogleMap(latLng);
            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void updateGoogleMap(LatLng latLng) {
        if(mMap ==null){
            // GoogleMap 객체가 생성되어 있지 않을 때는 화면 업데이트 불가능
            return;
        }
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
//        mMap.setMinZoomPreference(15);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    
}
