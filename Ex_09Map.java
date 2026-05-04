// MapsActivity.java
package com.example.locationapp;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.Intent;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.location.*;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient locationClient;
    private GeofencingClient geofencingClient;

    double latitude = 13.0827;
    double longitude = 80.2707;
    float radius = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        geofencingClient = LocationServices.getGeofencingClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Enable location
        mMap.setMyLocationEnabled(true);

        LatLng location = new LatLng(latitude, longitude);

        // Marker
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Geofence Area"));

        // Circle
        mMap.addCircle(new CircleOptions()
                .center(location)
                .radius(radius)
                .strokeColor(0x550000FF)
                .fillColor(0x220000FF));

        // Move camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        createGeofence();
    }

    private void createGeofence() {

        Geofence geofence = new Geofence.Builder()
                .setRequestId("GEOFENCE_ID")
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();

        ArrayList<Geofence> geofenceList = new ArrayList<>();
        geofenceList.add(geofence);

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofenceList)
                .build();

        Intent intent = new Intent(this, GeofenceReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        geofencingClient.addGeofences(request, pendingIntent);
    }
}

// GeofenceReceiver.java
package com.example.locationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event.hasError()) return;

        int transitionType = event.getGeofenceTransition();

        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(context, "Entered Geofence Area", Toast.LENGTH_LONG).show();
        }
        else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Toast.makeText(context, "Exited Geofence Area", Toast.LENGTH_LONG).show();
        }
    }
}

// activity_maps.xml
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/map"
    android:name="com.google.android.gms.maps.SupportMapFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>


// AndroidManifest.xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

<application ...>

    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_GOOGLE_MAPS_API_KEY"/>

    <receiver android:name=".GeofenceReceiver"/>

</application>