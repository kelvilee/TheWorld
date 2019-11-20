package com.example.theworld;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RecyclingMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = MainActivity.class.getSimpleName();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ArrayList<PlaceOfInterest> placeOfInterests = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int buttonStyle = R.style.Widget_AppCompat_Button_Colored;
        Button button = new Button(new ContextThemeWrapper(this, buttonStyle), null, buttonStyle);
        button.setText(R.string.backLabel);
        addContentView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // places_of_interest.json
        try {
            JSONArray recycleArray = new JSONArray(loadJSONFromPlacesAsset(getApplicationContext()));
            for(int i = 0; i < recycleArray.length(); i++) {
                String facType = recycleArray.getJSONObject(i).getString("FACILITY_TYPE").trim();
                if(facType.equals("Garbage and Recycling")) {
                    String location = recycleArray.getJSONObject(i).getString("LOCATION").trim();
                    String name = recycleArray.getJSONObject(i).getString("NAME").trim();
                    String webLink = recycleArray.getJSONObject(i).getString("WEBLINK").trim();
                    String facSubType = recycleArray.getJSONObject(i).getString("FACILITY_SUBTYPE").trim();
                    long id = recycleArray.getJSONObject(i).getLong("FACILITYID");
                    double longitude = recycleArray.getJSONObject(i).getDouble("LONGITUDE");
                    double latitude = recycleArray.getJSONObject(i).getDouble("LATITUDE");
                    LatLng latLng = new LatLng(latitude, longitude);
                    PlaceOfInterest poi = new PlaceOfInterest(location, name, facType, webLink, facSubType, id, latLng);
                    placeOfInterests.add(poi);
                }
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    }

    /**
     * Helper method to load JSON data from assets folder
     * @param context context
     * @return JSON as a String
     */
    private String loadJSONFromPlacesAsset(Context context) {

        String json;

        try {
            InputStream is = context.getAssets().open("places_of_interest.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return marker.getTitle().equals("Current Location");
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                PlaceOfInterest poi = placeOfInterests.get(Integer.parseInt(marker.getTitle()));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(poi.getWeblink()));
                startActivity(browserIntent);
            }
        });

        mMap.setInfoWindowAdapter(new RecyclingCenterInfoWindowAdapter());

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                addMarker2Map(location);
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

        Location lastKnownLocation = getLastKnownLocation();
        addMarker2Map(lastKnownLocation);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            addMarker2Map(lastKnownLocation);
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
        // add recycling markers
        for(int i = 0; i < placeOfInterests.size(); i++) {
            if(placeOfInterests.get(i).getFacilitySubType().equals("Used Oil")) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(placeOfInterests.get(i).getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.oil)).title(Integer.toString(i)).snippet(placeOfInterests.get(i).getLocation()));
                markerList.add(marker);
            } else if (placeOfInterests.get(i).getFacilitySubType().equals("Bottle Depot")) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(placeOfInterests.get(i).getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bottle)).title(Integer.toString(i)).snippet(placeOfInterests.get(i).getLocation()));
                markerList.add(marker);
            }
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = null;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                l = locationManager.getLastKnownLocation(provider);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    /**
     * Adds marker to map and centers on the location
     *
     * @param location location to center on
     */
    private void addMarker2Map(Location location) {
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latlng).title("Current Location"));

        float zoomLevel = 12.0f; // sets zoom level to be 6, higher zoom levels are zoomed in more
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
    }

    private class RecyclingCenterInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".

        private final View mContents;

        RecyclingCenterInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.recycling_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int id = Integer.parseInt(marker.getTitle());

            PlaceOfInterest poi = placeOfInterests.get(id);
            String title = poi.getName();
            TextView titleUi = view.findViewById(R.id.title);
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                // SpannableString titleText = new SpannableString(title);
                // titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }

            String type = poi.getFacilitySubType();
            TextView typeUi = view.findViewById(R.id.type);
            if (type != null) {
                // Spannable string allows us to edit the formatting of the text.
                // SpannableString titleText = new SpannableString(title);
                // titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                typeUi.setText(type);
            } else {
                typeUi.setText("N/A");
            }

            String snippet = poi.getLocation();
            TextView snippetUi = view.findViewById(R.id.snippet);
            if (snippet != null) {
                //SpannableString snippetText = new SpannableString(snippet);
                //snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                //snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippet);
            } else {
                snippetUi.setText("");
            }

            String link = poi.getWeblink();
            TextView weblinkUi = view.findViewById(R.id.weblink);
            if (link != null) {
                //SpannableString snippetText = new SpannableString(snippet);
                //snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                //snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                weblinkUi.setText(link);
            } else {
                weblinkUi.setText("");
            }
        }
    }
}
