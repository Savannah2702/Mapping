package com.example.a2urchs77.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetLocationActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        EditText latitudeEditText = (EditText)findViewById(R.id.latitude);
        double newLatitude = Double.parseDouble(latitudeEditText.getText().toString());

        //Retrieve Longitude
        EditText longitudeEditText = (EditText)findViewById(R.id.longitude);
        double newLongitude = Double.parseDouble(longitudeEditText.getText().toString());

        //add to bundle
        Bundle latlongBundle = new Bundle();
        latlongBundle.putDouble("com.example.latitude", newLatitude);
        latlongBundle.putDouble("com.example.longitude", newLongitude);

        //send bundle to main activity
        Intent intent = new Intent();
        intent.putExtras(latlongBundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}

