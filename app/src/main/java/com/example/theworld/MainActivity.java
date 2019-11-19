package com.example.theworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<String> opLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.action_recents:
                        selectedFragment = new HomeFragment();
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        selectedFragment = new MapsFragment();
                        Toast.makeText(MainActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        selectedFragment = new ToolsFragment();
                        Toast.makeText(MainActivity.this, "Tools", Toast.LENGTH_SHORT).show();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(getApplicationContext()));
            JSONArray litterBinsArray = obj.getJSONArray("features");
            String result = "";
            for (int i = 0; i < litterBinsArray.length(); i++) {
                result = litterBinsArray.getJSONObject(i).getJSONObject("properties").getString("OPERATING_LOCATION");
                opLocations.add(result);
            }
            String locations = "";
            for (int i = 0; i < opLocations.size(); i++) {
                locations += opLocations.get(i) + "\n\n";
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

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("litter_containers.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void goToMap(View v) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
}
