package fi.metropolia.valemajoneesi.moodprint;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.function.Consumer;

public class MapsActivity<locationManager> extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LocationManager locManager;
    PlacesClient placesClient;
    boolean canGetLocation = false;
    private ActivityResultLauncher<String> locationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    canGetLocation=isGranted;
                }
            );

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Places.initialize(getApplicationContext(), getString(R.string.MAPS_API_KEY));
        placesClient = Places.createClient(this);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        EmotionTracker.setInstance(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public void okOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        if (
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION
            );
            locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
            );
        } else {
            canGetLocation = true;
        }
        while(!canGetLocation) {}
        locManager.getCurrentLocation(
                LocationManager.GPS_PROVIDER,
                null,
                getApplication().getMainExecutor(),
                new Consumer<Location>() {
                    @Override
                    public void accept(Location loc) {
                        map.clear();
                        EmotionTracker emoTrack = EmotionTracker.getInstance();
                        double energy = emoTrack.averageEnergy(emoTrack.lastHistoryEntry());
                        double mood = emoTrack.averageMood(emoTrack.lastHistoryEntry());
                        String type = getTypeOfActivity(energy, mood);
                        LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
                        map.addMarker(new MarkerOptions().position(pos).title("You are here"));
                        map.moveCamera(CameraUpdateFactory.newLatLng(pos));
                        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                        sb.append("location=" + pos.latitude + "," + pos.longitude);
                        sb.append("&radius=5000");
                        sb.append("&types=" + type);
                        sb.append("&sensor=true");
                        sb.append("&key="+R.string.MAPS_API_KEY);

                    }
                }
        );
    }

    private String getTypeOfActivity(double energy, double mood) {
        if(energy > 3 && mood >= 0) {
            return "gym";
        }
        if(energy > 2 && mood > -1.5) {
            return "restaurant";
        }
        return "";
    }
}