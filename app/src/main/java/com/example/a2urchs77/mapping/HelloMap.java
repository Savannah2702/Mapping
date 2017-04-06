package com.example.a2urchs77.mapping;

import android.app.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.config.Configuration;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.content.Intent;
import android.widget.Toast;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HelloMap extends Activity implements View.OnClickListener{
    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers


        mv = (MapView) findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.73577, -1.778586));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()

        {

            public boolean onItemLongPress ( int i, OverlayItem item){
                Toast.makeText(HelloMap.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }


            public boolean onItemSingleTapUp(int i, OverlayItem item) {
                Toast.makeText(HelloMap.this, item.getTitle()+":"+item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem fernhurst = new OverlayItem("Fernhurst", "Village in West Sussex", new GeoPoint(51.05, -0.72));
        OverlayItem blackdown = new OverlayItem("Blackdown", "highest point in West Sussex", new GeoPoint(51.0581, -0.6897));
        OverlayItem christchurch = new OverlayItem("Christchurch", "Home", new GeoPoint(50.73577, -1.778586));
        items.addItem(fernhurst);
        items.addItem(blackdown);
        items.addItem(christchurch);
        mv.getOverlays().add(items);

        try {

            BufferedReader reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath()+"/poi.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] components = line.split(",");
                if (components.length == 5) {
                    OverlayItem currentItem = new OverlayItem (components[0], components[2], new GeoPoint(Double.parseDouble(components[4]), Double.parseDouble(components[3])));
                    items.addItem(currentItem);
                }
            }

        } catch (IOException e) {
            new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();

        }

    }

    @Override
    public void onClick(View view) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.choosemap) {
            // react to the menu item being selected...
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }


        if (item.getItemId() == R.id.setlocation) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                boolean cyclemap = extras.getBoolean("com.example.cyclemap");
                if (cyclemap == true) {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                } else {
                    mv.getTileProvider().setTileSource(TileSourceFactory.MAPNIK);
                }

            }
        }

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                double newLatitude = extras.getDouble("com.example.latitude");
                double newLongitude = extras.getDouble("com.example.longitude");
                mv.getController().setCenter(new GeoPoint(newLatitude, newLongitude));
            }
        }
    }

}

