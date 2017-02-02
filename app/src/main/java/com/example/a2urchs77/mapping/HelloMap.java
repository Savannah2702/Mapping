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
        double newLatitude = Integer.parseInt(latitude2);

        // retrieve longitude
        EditText longitude1 = (EditText) findViewById(R.id.longitude);
        String longitude2 = longitude1.getText().toString();
        double newLongitude = Integer.parseInt(longitude2);

        // set map to new position
        mv.getController().setCenter(new GeoPoint(newLatitude,newLongitude));

    }
}
