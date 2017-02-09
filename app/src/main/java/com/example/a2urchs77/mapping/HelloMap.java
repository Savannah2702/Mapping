package com.example.a2urchs77.mapping;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.config.Configuration;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.content.Intent;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class HelloMap extends Activity implements View.OnClickListener
{
     MapView mv;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView)findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(51.05,-0.72));
    }

    @Override
    public void onClick(View view) {
        // retrieve latitude
        EditText latitude1 = (EditText) findViewById(R.id.latitude);
        String latitude2 = latitude1.getText().toString();
        double newLatitude =Double.parseDouble(latitude2);

        // retrieve longitude
        EditText longitude1 = (EditText) findViewById(R.id.longitude);
        String longitude2 = longitude1.getText().toString();
        double newLongitude = Double.parseDouble(longitude2);

        // set map to new position
        mv.getController().setCenter(new GeoPoint(newLatitude,newLongitude));

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            // react to the menu item being selected...
            Intent intent = new Intent(this,MapChooseActivity.class);
            startActivityForResult(intent,0);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean cyclemap = extras.getBoolean("com.example.cyclemap");
                if(cyclemap==true)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.getTileProvider().setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
    }

}
